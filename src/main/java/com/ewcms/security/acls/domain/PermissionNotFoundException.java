/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.domain;

import org.springframework.security.core.AuthenticationException;

public class PermissionNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 9043199421424927555L;

	/**
     * Constructs a <code>PermissionNotFoundException</code> with the specified
     * message.
     *
     * @param msg the detail message.
     */
    public PermissionNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>PermissionNotFoundException</code>, making use of the <tt>extraInformation</tt>
     * property of the superclass.
     *
     * @param msg the detail message
     * @param extraInformation additional information such as the username.
     */
    public PermissionNotFoundException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }

    /**
     * Constructs a <code>PermissionNotFoundException</code> with the specified
     * message and root cause.
     *
     * @param msg the detail message.
     * @param t root cause
     */
    public PermissionNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

}
