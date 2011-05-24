/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

/**
 *   转换成Float数据类型的值
 *  
 * @author 王伟
 */
class FloatConvert implements Convertable<Float> {
    
    @Override
    public Float parse(String value) throws ConvertException{
        try{
            return Float.valueOf(value);
        }catch(NumberFormatException e){
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Float value) {
        return String.valueOf(value.floatValue());
    }
}
