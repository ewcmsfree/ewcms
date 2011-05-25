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
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

/**
 *
 * @author wangwei
 */
public class IPPersistentRememberMeToken extends PersistentRememberMeToken {

    private String ipAddress;

    public IPPersistentRememberMeToken(String username, String series, String tokenValue, Date date, String ipAddress) {
        super(username,series,tokenValue,date);
        this.ipAddress = ipAddress;
    }

    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress(){
        return ipAddress;
    }
}
