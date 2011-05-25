/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
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
