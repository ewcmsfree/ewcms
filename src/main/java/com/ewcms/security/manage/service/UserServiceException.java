/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.service;

import org.springframework.security.core.AuthenticationException;

public class UserServiceException extends AuthenticationException {

    public UserServiceException(String msg) {
        super(msg);
    }

    public UserServiceException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }

    public UserServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}
