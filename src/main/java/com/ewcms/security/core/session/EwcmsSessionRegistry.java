package com.ewcms.security.core.session;

import org.springframework.security.core.session.SessionRegistry;

public interface EwcmsSessionRegistry extends SessionRegistry{

    /**
     * 通过用户名移除该用户所有注册的SessionInformation,包括过时信息。
     * 
     * @param username 用户名
     */
    void removeSessionInformationByUsername(String username);
    
    /**
     * 通过用户名使该用户所有注册的SessionInformation都过时。
     * 
     * @param username
     */
    void expiredSessionInformationByUsername(String username);
}
