/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 * 转换成Integer数据类型
 *  
 * @author WangWei
 */
class IntegerConvert implements Convertable<Integer> {
    
    @Override
    public Integer parse(String value)throws ConvertException {
        try{
            return Integer.valueOf(value);
        }catch(NumberFormatException e){
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Integer value) {
        return String.valueOf(value.intValue());
    }
    
    
}
