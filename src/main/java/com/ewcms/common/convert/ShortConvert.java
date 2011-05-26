/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 * 转换成Short数据类型
 *  
 * @author WangWei
 */
class ShortConvert implements Convertable<Short> {
    
    @Override
    public Short parse(String value)throws ConvertException {
        try{
            return Short.valueOf(value);
        }catch(NumberFormatException e){
            throw new ConvertException(e);
        }
    }
    
    @Override
    public String parseString(Short value) {
        return String.valueOf(value.shortValue());
    }
}
