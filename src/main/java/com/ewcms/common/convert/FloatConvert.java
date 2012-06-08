/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 * 转换成Float数据类型
 *  
 * @author WangWei
 */
class FloatConvert implements Convertable<Float> {
    
    @Override
    public Float parse(String value) throws ConvertException{
        try{
        	if (value == null || value.equals("")){
        		return 0f;
        	}
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
