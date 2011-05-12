/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

/**
 *   转换成Short数据类型的值
 *  
 * @author 王伟
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
