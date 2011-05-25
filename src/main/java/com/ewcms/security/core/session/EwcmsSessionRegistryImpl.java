/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.core.session;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class EwcmsSessionRegistryImpl extends SessionRegistryImpl implements EwcmsSessionRegistry{

    private static final Logger logger = LoggerFactory.getLogger(EwcmsSessionRegistryImpl.class);
    
    /**
     * 通过用户名得到principal
     * 
     * @param username 用户名
     * @return principal
     */
    Object getPrincipal(final String username){
        for(Object p : getAllPrincipals()){
            if(p instanceof UserDetails){
                if(username.equals(((UserDetails)p).getUsername())){
                    return p;
                }
            }else{
                if(username.equals(p.toString())){
                    return p;
                }
            }
        }
        return null;
    }
    
    @Override
    public void removeSessionInformationByUsername(String username) {
        Object principal = getPrincipal(username);
        if(principal == null){
            if(logger.isDebugEnabled()){
                logger.debug("{} has't session",username);
            }
            return ;
        }
        
        List<SessionInformation> sessionInformations = getAllSessions(principal, true);
        for(SessionInformation sessionInformation : sessionInformations){
            removeSessionInformation(sessionInformation.getSessionId());
        }
    }

    @Override
    public void expiredSessionInformationByUsername(String username) {
        Object principal = getPrincipal(username);
        if(principal == null){
            if(logger.isDebugEnabled()){
                logger.debug("{} has't session",username);
            }
            return ;
        }
        List<SessionInformation> sessionInformations = getAllSessions(principal, false);
        for(SessionInformation sessionInformation : sessionInformations){
            sessionInformation.expireNow();
        }
    }
    
}
