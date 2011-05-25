/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.security.web.authentication.rememberme;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

/**
 *
 * @author wangwei
 */
public class JPATokenBasedRememberMeService extends PersistentTokenBasedRememberMeServices {

    private static final Logger logger = LoggerFactory.getLogger(JPATokenBasedRememberMeService.class);
    
    private boolean bindingIP = false;
    
    private PersistentTokenRepository tokenRepository;
    
    public JPATokenBasedRememberMeService() throws Exception {
        super();
    }

    protected String getUserIPAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
    
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {

        if (bindingIP) {
            String ip = getUserIPAddress(request);
            final String presentedSeries = cookieTokens[0];
            IPPersistentRememberMeToken token = (IPPersistentRememberMeToken) tokenRepository.getTokenForSeries(presentedSeries);
            if (token == null) {
                // No series match, so we can't authenticate using this cookie
                throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
            }
            if (!ip.equals(token.getIpAddress())) {
                throw new InvalidCookieException("Cookie IP Address did not contain a matching IP (contained '" + ip + "')");
            }
        }

        return super.processAutoLoginCookie(cookieTokens, request, response);
    }
        
    @Override
    protected void onLoginSuccess(HttpServletRequest request,HttpServletResponse response, Authentication successfulAuthentication) {
        String username = successfulAuthentication.getName();

        logger.debug("Creating new persistent login for user {}" , username);

        String ip = getUserIPAddress(request);
        IPPersistentRememberMeToken persistentToken =
                new IPPersistentRememberMeToken(username, generateSeriesData(),
                generateTokenData(), new Date(), ip);
        try {
            tokenRepository.createNewToken(persistentToken);
            addCookie(persistentToken, request, response);
        } catch (DataAccessException e) {
            logger.error("Failed to save persistent token ", e);
        }
    }

    private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[]{token.getSeries(), token.getTokenValue()}, getTokenValiditySeconds(), request, response);
    }

    public void setbindingIP(boolean bindingIP) {
        this.bindingIP = bindingIP;
    }

    @Override
    @Autowired
    public void setTokenRepository(PersistentTokenRepository tokenRepository) {
        super.setTokenRepository(tokenRepository);
        this.tokenRepository = tokenRepository;
    }

}
