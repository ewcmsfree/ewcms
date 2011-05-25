/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * TypeHandler对象的工厂封装 
 * 
 * @author 王伟
 */
public enum ConvertFactory {

    instance;
    
    private Map<Class<?>, Convertable<?>> parseHandlerMap;

    private ConvertFactory() {
        parseHandlerMap = handlerMap();
    }

    /**
     * 根据Class类型 返回  相同类型的ParseTypeHandler
     * 
     * @param clazz 数据类型类
     * @return ParseTypeHandler
     */
    public  Convertable<?> convertHandler(Class<?> clazz) {
        Convertable<?> hander = parseHandlerMap.get(clazz);
        if(hander == null){
            throw new IllegalStateException(clazz.getName()+" type has not convert");
        }
        return hander;
    }

    private Map<Class<?>, Convertable<?>> handlerMap() {
        Map<Class<?>, Convertable<?>> map = new HashMap<Class<?>, Convertable<?>>();

        map.put(BigDecimal.class, new BigDecimalConvert());
        map.put(BigInteger.class, new BigIntegerConvert());
        map.put(Boolean.class, new BooleanConvert());
        map.put(Byte.class, new ByteConvert());
        map.put(java.util.Date.class, new DateConvert());
        map.put(Double.class, new DoubleConvert());
        map.put(Float.class, new FloatConvert());
        map.put(Integer.class, new IntegerConvert());
        map.put(Long.class, new LongConvert());
        map.put(Short.class, new ShortConvert());
        map.put(java.sql.Date.class, new SqlDateConvert());
        map.put(java.sql.Timestamp.class, new SqlTimestampConvert());
        map.put(java.sql.Time.class, new SqlTimeConvert());
        map.put(String.class, new StringConvert());

        return map;
    }
}
