/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
