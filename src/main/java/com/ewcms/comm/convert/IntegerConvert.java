/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

/**
 *   转换成Integer数据类型的值
 *  
 * @author 王伟
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
