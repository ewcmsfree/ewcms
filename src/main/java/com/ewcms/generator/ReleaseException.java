/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator;

/**
 * 发布异常
 * 
 * @author wangwei
 */
public class ReleaseException extends Exception {

    public ReleaseException(){}

     public ReleaseException(String message) {
        super(message);
    }

    public ReleaseException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public ReleaseException(Throwable thrwbl){
        super(thrwbl);
    }
}
