/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.common.convert;

/**
 * 转换异常
 *
 * @author WangWei
 */
public class ConvertException extends Exception {

	private static final long serialVersionUID = -4065582154820315966L;

	public ConvertException(String string) {
        super(string);
    }

    public ConvertException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ConvertException(Throwable thrwbl) {
        super(thrwbl);
    }
}
