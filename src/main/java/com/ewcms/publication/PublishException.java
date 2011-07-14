/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

/**
 * 发布异常
 * 
 * @author wangwei
 */
public class PublishException extends Exception {

    public PublishException(){}

     public PublishException(String message) {
        super(message);
    }

    public PublishException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public PublishException(Throwable thrwbl){
        super(thrwbl);
    }
}
