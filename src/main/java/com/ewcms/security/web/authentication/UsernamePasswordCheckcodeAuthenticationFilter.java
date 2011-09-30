/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.web.authentication;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

/**
 * 登录用户认证
 * 
 * 通过验证码、用户名和密码认证用户登判断录是否有效。
 * 验证码缺省参数名{@link #FORM_CODECHECK_KEY}，页可以通过getCheckcodeParameter设置。
 * 
 * @author wangwei
 */
public class UsernamePasswordCheckcodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(UsernamePasswordCheckcodeAuthenticationFilter.class);
    
    public static final String FORM_CODECHECK_KEY = "j_checkcode";
    
    private String checkcodeParameter = FORM_CODECHECK_KEY;
    
    @Autowired
    private CaptchaService captchaService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        setUsernameInSession(request);

        String checkcode = obtainCheckcode(request);
        if (checkcode == null) {
            checkcode = "";
        }
        checkcode = checkcode.toUpperCase();

        String captchaId = request.getSession().getId();
        try {
            if (!captchaService.validateResponseForID(captchaId, checkcode)) {
                throw new AuthenticationServiceException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCheckcode", "bad checkecode"));
            }
        } catch (CaptchaServiceException e) {
            if(logger.isDebugEnabled()){
                logger.debug("CaptchaServiceException:{}" , messages);
            }
            throw new AuthenticationServiceException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCheckcode", "bad checkecode"));
        }

        Authentication auth = super.attemptAuthentication(request, response);

        removeUsernameInSession(request);

        return auth;
    }

    /**
     * 保持用户名到Session attribute中。
     * 跳转到登录错误页面，可以显示登录错误的用户名
     *
     * @param request
     */
    protected void setUsernameInSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null || getAllowSessionCreation()) {
            request.getSession().setAttribute(getUsernameParameter(), this.obtainUsername(request));
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("Add username in session attribute fail,because session is null");
            }
        }
    }

    /**
     * 删除Session attribute用户名
     * 登录成功后删除登录的用户名
     * 
     * @param request
     */
    protected void removeUsernameInSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(getUsernameParameter());
        }
    }

    public String obtainCheckcode(HttpServletRequest request) {
        return request.getParameter(checkcodeParameter);
    }

    public void setCheckcodeParameter(String parameter) {
        Assert.hasText(parameter, "checkcode parameter must not be empty or null");
        this.checkcodeParameter = parameter;
    }

    public String getCheckcodeParameter() {
        return this.checkcodeParameter;
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}
