/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 *   转换成Double数据类型的值
 *  
 * @author 王伟
 */
class DoubleConvert implements Convertable<Double> {
    
    @Override
    public Double parse(String value)throws ConvertException {
        try{
            return Double.valueOf(value);
        }catch(NumberFormatException e){
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Double value) {
        return String.valueOf(value.doubleValue());
    }
}
