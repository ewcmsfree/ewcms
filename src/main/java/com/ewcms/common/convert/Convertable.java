/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

/**
 *  将String类型的值转换成指定数据类型的值
 *    
 * @author 王伟
 */
public interface Convertable<T> {

    /**
     * 转换参数数据类型
     * 
     * @param value 转换的值
     * @return 转换后的值      
     */
     public T parse(String value)throws ConvertException;
    
    /**
     * 将指定的数据类型转换成字符串
     * 
     * @param value 数据值
     * @return 字符串
     */
    public String parseString(T value);
}
