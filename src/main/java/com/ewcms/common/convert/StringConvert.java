/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 * 转换成String数据类型
 * 
 * @author WangWei
 */
class StringConvert implements Convertable<String> {

    @Override
    public String parse(String value) {
    	return (value == null) ? "" : value;
    }

    @Override
    public String parseString(String value) {
        return (String)value;
    }
}
