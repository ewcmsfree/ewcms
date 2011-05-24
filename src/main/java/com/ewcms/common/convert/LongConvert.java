/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

/**
 *   转换成Long数据类型的值
 *  
 * @author 王伟
 */
class LongConvert implements Convertable<Long> {

    @Override
    public Long parse(String value)throws ConvertException {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Long value) {
        return String.valueOf(value.longValue());
    }
}
