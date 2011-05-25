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
 *
 * @author wangwei
 */
public class ConvertException extends Exception {

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
