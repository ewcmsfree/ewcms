/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 * 转换成String数据类型的值
 * 
 * @author 王伟
 */
class StringConvert implements Convertable<String> {

    @Override
    public String parse(String value) {
        return value;
    }

    @Override
    public String parseString(String value) {
        return (String)value;
    }
}
