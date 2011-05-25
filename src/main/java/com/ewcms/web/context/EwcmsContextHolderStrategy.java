/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.context;

/**
 * A strategy for storing security context information against a thread.
 *
 * <p>
 * The preferred strategy is loaded by {@link
 * org.springframework.security.context.SecurityContextHolder}.
 * </p>
 *
 * @author Ben Alex
 * @version $Id: SecurityContextHolderStrategy.java 2142 2007-09-21 18:18:21Z luke_t $
 */
public interface EwcmsContextHolderStrategy {
    //~ Methods ========================================================================================================

    /**
     * Clears the current context.
     */
    void clearContext();

    /**
     * Obtains the current context.
     *
     * @return a context (never <code>null</code> - create a default implementation if necessary)
     */
    EwcmsContextable getContext();

    /**
     * Sets the current context.
     *
     * @param context to the new argument (should never be <code>null</code>, although implementations must check if
     *        <code>null</code> has been passed and throw an <code>IllegalArgumentException</code> in such cases)
     */
    void setContext(EwcmsContextable context);
}
