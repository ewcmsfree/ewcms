/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ewcms.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;

/**
 */
@Controller("ewcmsContext")
public class EwcmsContextFilter implements Filter, InitializingBean {

    private static final Log log = LogFactory.getLog(EwcmsContextFilter.class);
    static final String FILTER_APPLIED = "__ewcms_session_filter_applied";
    public static final String EWCMS_CONTEXT_KEY = "EWCMS_CONTEXT";
    //~ Instance fields ================================================================================================
    private Class contextClass = EwcmsContext.class;
    private Object contextObject;
    /**
     * Indicates if this filter can create a <code>HttpSession</code> if
     * needed (sessions are always created sparingly, but setting this value to
     * <code>false</code> will prohibit sessions from ever being created).
     * Defaults to <code>true</code>. Do not set to <code>false</code> if
     * you are have set {@link #forceEagerSessionCreation} to <code>true</code>,
     * as the properties would be in conflict.
     */
    private boolean allowSessionCreation = true;
    /**
     * Indicates if this filter is required to create a <code>HttpSession</code>
     * for every request before proceeding through the filter chain, even if the
     * <code>HttpSession</code> would not ordinarily have been created. By
     * default this is <code>false</code>, which is entirely appropriate for
     * most circumstances as you do not want a <code>HttpSession</code>
     * created unless the filter actually needs one. It is envisaged the main
     * situation in which this property would be set to <code>true</code> is
     * if using other filters that depend on a <code>HttpSession</code>
     * already existing, such as those which need to obtain a session ID. This
     * is only required in specialised cases, so leave it set to
     * <code>false</code> unless you have an actual requirement and are
     * conscious of the session creation overhead.
     */
    private boolean forceEagerSessionCreation = false;

    public EwcmsContextFilter() throws ServletException {
        this.contextObject = generateNewContext();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }

        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }

        doFilterHttp((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    //~ Methods ========================================================================================================
    @Override
    public void afterPropertiesSet() throws Exception {
        if ((this.contextClass == null) || (!EwcmsContextable.class.isAssignableFrom(this.contextClass))) {
            throw new IllegalArgumentException("context must be defined and implement EwcmsContextable "
                    + "(typically use com.ewcms.context.EwcmsContextable; existing class is "
                    + this.contextClass + ")");
        }

        if (forceEagerSessionCreation && !allowSessionCreation) {
            throw new IllegalArgumentException(
                    "If using forceEagerSessionCreation, you must set allowSessionCreation to also be true");
        }

        contextObject = generateNewContext();
    }

    public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getAttribute(FILTER_APPLIED) != null) {
            // ensure that filter is only applied once per request
            chain.doFilter(request, response);

            return;
        }

        HttpSession httpSession = safeGetSession(request, forceEagerSessionCreation);
        boolean httpSessionExistedAtStartOfRequest = httpSession != null;
        EwcmsContextable contextBeforeChainExecution = readSecurityContextFromSession(httpSession);

        // Make the HttpSession null, as we don't want to keep a reference to it lying
        // around in case chain.doFilter() invalidates it.
        httpSession = null;

        if (contextBeforeChainExecution == null) {
            contextBeforeChainExecution = generateNewContext();

            if (log.isDebugEnabled()) {
                log.debug("New EwcmsContextable instance will be associated with EwmcsContextHolderable");
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Obtained a valid EwcmsContextable from Ewcms_CONTEXT to "
                        + "associate with EwmcsContextHolderable: '" + contextBeforeChainExecution + "'");
            }
        }

        int contextHashBeforeChainExecution = contextBeforeChainExecution.hashCode();
        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

        // Create a wrapper that will eagerly update the session with the security context
        // if anything in the chain does a sendError() or sendRedirect().
        // See SEC-398

        OnRedirectUpdateSessionResponseWrapper responseWrapper =
                new OnRedirectUpdateSessionResponseWrapper(response, request,
                httpSessionExistedAtStartOfRequest, contextHashBeforeChainExecution);

        // Proceed with chain

        try {
            // This is the only place in this class where SecurityContextHolder.setContext() is called
            EwcmsContextHolder.setContext(contextBeforeChainExecution);
            chain.doFilter(request, responseWrapper);
        } finally {
            // This is the only place in this class where SecurityContextHolder.getContext() is called
            EwcmsContextable contextAfterChainExecution = EwcmsContextHolder.getContext();

            // Crucial removal of SecurityContextHolder contents - do this before anything else.
            EwcmsContextHolder.clearContext();

            request.removeAttribute(FILTER_APPLIED);

            // storeSecurityContextInSession() might already be called by the response wrapper
            // if something in the chain called sendError() or sendRedirect(). This ensures we only call it
            // once per request.
            if (!responseWrapper.isSessionUpdateDone()) {
                storeSecurityContextInSession(contextAfterChainExecution, request,
                        httpSessionExistedAtStartOfRequest, contextHashBeforeChainExecution);
            }

            if (log.isDebugEnabled()) {
                log.debug("EwcmsContextHolder now cleared, as request processing completed");
            }
        }
    }

    /**
     * Gets the security context from the session (if available) and returns it.
     * <p/>
     * If the session is null, the context object is null or the context object stored in the session
     * is not an instance of SecurityContext it will return null.
     * <p/>
     * If <tt>cloneFromHttpSession</tt> is set to true, it will attempt to clone the context object
     * and return the cloned instance.
     *
     * @param httpSession the session obtained from the request.
     */
    private EwcmsContextable readSecurityContextFromSession(HttpSession httpSession) {
        if (httpSession == null) {
            if (log.isDebugEnabled()) {
                log.debug("No HttpSession currently exists");
            }
            return null;
        }

        // Session exists, so try to obtain a context from it.

        Object contextFromSessionObject = httpSession.getAttribute(EWCMS_CONTEXT_KEY);

        if (contextFromSessionObject == null) {
            if (log.isDebugEnabled()) {
                log.debug("HttpSession returned null object for Ewcms_CONTEXT");
            }

            return null;
        }

        // Everything OK. The only non-null return from this method.

        return (EwcmsContextable) contextFromSessionObject;
    }

    /**
     * Stores the supplied security context in the session (if available) and if it has changed since it was
     * set at the start of the request. If the AuthenticationTrustResolver identifies the current user as
     * anonymous, then the context will not be stored. 
     *
     * @param securityContext the context object obtained from the SecurityContextHolder after the request has
     *        been processed by the filter chain. SecurityContextHolder.getContext() cannot be used to obtain
     *        the context as it has already been cleared by the time this method is called.
     * @param request the request object (used to obtain the session, if one exists).
     * @param httpSessionExistedAtStartOfRequest indicates whether there was a session in place before the
     *        filter chain executed. If this is true, and the session is found to be null, this indicates that it was
     *        invalidated during the request and a new session will now be created.
     * @param contextHashBeforeChainExecution the hashcode of the context before the filter chain executed.
     *        The context will only be stored if it has a different hashcode, indicating that the context changed
     *        during the request.
     *
     */
    private void storeSecurityContextInSession(EwcmsContextable context,
            HttpServletRequest request,
            boolean httpSessionExistedAtStartOfRequest,
            int contextHashBeforeChainExecution) {
        HttpSession httpSession = safeGetSession(request, false);

        if (httpSession == null) {
            if (httpSessionExistedAtStartOfRequest) {
                if (log.isDebugEnabled()) {
                    log.debug("HttpSession is now null, but was not null at start of request; "
                            + "session was invalidated, so do not create a new session");
                }
            } else {
                // Generate a HttpSession only if we need to

                if (!allowSessionCreation) {
                    if (log.isDebugEnabled()) {
                        log.debug("The HttpSession is currently null, and the "
                                + "HttpSessionContextIntegrationFilter is prohibited from creating an HttpSession "
                                + "(because the allowSessionCreation property is false) - EwcmsContextable thus not "
                                + "stored for next request");
                    }
                } else if (!contextObject.equals(context)) {
                    if (log.isDebugEnabled()) {
                        log.debug("HttpSession being created as EwcmsContextHolderable contents are non-default");
                    }
                    httpSession = safeGetSession(request, true);

                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("HttpSession is null, but EwcmsContextHolderable has not changed from default: ' "
                                + context
                                + "'; not creating HttpSession or storing EwcmsContextHolderable contents");
                    }
                }
            }
        }

        // If HttpSession exists, store current SecurityContextHolder contents but only if
        // the SecurityContext has actually changed (see JIRA SEC-37)
        if (httpSession != null && context.hashCode() != contextHashBeforeChainExecution) {
            httpSession.setAttribute(EWCMS_CONTEXT_KEY, context);
            if (log.isDebugEnabled()) {
                log.debug("EwcmsContextable stored to HttpSession: '" + context + "'");
            }

        }
    }

    private HttpSession safeGetSession(HttpServletRequest request, boolean allowCreate) {
        try {
            return request.getSession(allowCreate);
        } catch (IllegalStateException ignored) {
            return null;
        }
    }

    public EwcmsContextable generateNewContext() throws ServletException {
        try {
            return (EwcmsContextable) this.contextClass.newInstance();
        } catch (InstantiationException ie) {
            throw new ServletException(ie);
        } catch (IllegalAccessException iae) {
            throw new ServletException(iae);
        }
    }

    public boolean isAllowSessionCreation() {
        return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    protected Class getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class secureContext) {
        this.contextClass = secureContext;
    }

    public boolean isForceEagerSessionCreation() {
        return forceEagerSessionCreation;
    }

    public void setForceEagerSessionCreation(boolean forceEagerSessionCreation) {
        this.forceEagerSessionCreation = forceEagerSessionCreation;
    }

    //~ Inner Classes ==================================================================================================
    /**
     * Wrapper that is applied to every request to update the <code>HttpSession<code> with
     * the <code>SecurityContext</code> when a <code>sendError()</code> or <code>sendRedirect</code>
     * happens. See SEC-398. The class contains the fields needed to call
     * <code>storeSecurityContextInSession()</code>
     */
    private class OnRedirectUpdateSessionResponseWrapper extends HttpServletResponseWrapper {

        HttpServletRequest request;
        boolean httpSessionExistedAtStartOfRequest;
        int contextHashBeforeChainExecution;
        // Used to ensure storeSecurityContextInSession() is only
        // called once.
        boolean sessionUpdateDone = false;

        /**
         * Takes the parameters required to call <code>storeSecurityContextInSession()</code> in
         * addition to the response object we are wrapping.
         * @see HttpSessionContextIntegrationFilter#storeSecurityContextInSession(SecurityContext, HttpServletRequest, boolean, int)
         */
        public OnRedirectUpdateSessionResponseWrapper(HttpServletResponse response,
                HttpServletRequest request,
                boolean httpSessionExistedAtStartOfRequest,
                int contextHashBeforeChainExecution) {
            super(response);
            this.request = request;
            this.httpSessionExistedAtStartOfRequest = httpSessionExistedAtStartOfRequest;
            this.contextHashBeforeChainExecution = contextHashBeforeChainExecution;
        }

        /**
         * Makes sure the session is updated before calling the
         * superclass <code>sendError()</code>
         */
        @Override
        public void sendError(int sc) throws IOException {
            doSessionUpdate();
            super.sendError(sc);
        }

        /**
         * Makes sure the session is updated before calling the
         * superclass <code>sendError()</code>
         */
        @Override
        public void sendError(int sc, String msg) throws IOException {
            doSessionUpdate();
            super.sendError(sc, msg);
        }

        /**
         * Makes sure the session is updated before calling the
         * superclass <code>sendRedirect()</code>
         */
        @Override
        public void sendRedirect(String location) throws IOException {
            doSessionUpdate();
            super.sendRedirect(location);
        }

        /**
         * Calls <code>storeSecurityContextInSession()</code>
         */
        private void doSessionUpdate() {
            if (sessionUpdateDone) {
                return;
            }
            EwcmsContextable context = EwcmsContextHolder.getContext();
            storeSecurityContextInSession(context, request,
                    httpSessionExistedAtStartOfRequest, contextHashBeforeChainExecution);
            sessionUpdateDone = true;
        }

        /**
         * Tells if the response wrapper has called
         * <code>storeSecurityContextInSession()</code>.
         */
        public boolean isSessionUpdateDone() {
            return sessionUpdateDone;
        }
    }
}
