/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
     * <p>
     * 考虑导入对数据要求不高，转换错误返回空对象，不做异常处理
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
