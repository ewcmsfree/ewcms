/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * 判断值为空
 * 
 * @author WangWei
 */
public class EmptyUtil {

    /**
     * 判断值为null
     * 
     * @param value 
     * @return true is null
     */
    public static boolean isNull(Object value) {
        return value == null ? true : false;
    }

    /**
     * 判断值为不为null
     * 
     * @param value
     * @return true is not null
     */
    public static boolean isNotNull(Object value){
        return !isNull(value);
    }

    /**
     * 判断数组为空
     * 
     * @param value
     * @return 
     */
    public static boolean isArrayEmpty(Object[] value) {
        if (isNull(value)) {
            return true;
        }
        return Array.getLength(value) == 0 ? true : false;
    }

    /**
     * 判断数组不为空
     * 
     * @param value
     * @return 
     */
    public static boolean isArrayNotEmpty(Object[] value){
        return !isArrayEmpty(value);
    }

    /**
     * 判断集合为空
     * 
     * @param value
     * @return
     */
    public static boolean isCollectionEmpty(Collection<?> value) {
        if (isNull(value)) {
            return true;
        }
        return value.isEmpty();
    }

    /**
     * 判断集合不为空
     * 
     * @param value
     * @return
     */
    public static boolean isCollectionNotEmpty(Collection<?> value){
        return !isCollectionEmpty(value);
    }

    /**
     * 判断Map为空
     * 
     * @param value
     * @return
     */
    public static boolean isMapEmpty(Map<?,?> value){
        if(isNull(value)){
            return true;
        }
        return value.isEmpty();
    }

    /**
     * 判断Map不为空
     * 
     * @param value
     * @return
     */
    public static boolean isMapNotEmpty(Map<?,?> value){
        return !isMapEmpty(value);
    }

    /**
     * 判断字符串为空
     *
     * <pre>
     * isEmpty(null)=true
     * isEmpty("") =true
     * isEmpty("  ")=true
     * isEmpty("test")=false
     * isEmpty(" test  ")=false
     *
     * <p>非String类型</p>
     *
     * <pre>
     * isEmpty(null)=true
     *
     * @param value
     * @return
     */
    public static boolean isStringEmpty(final Object value) {
        if (isNull(value)) {
            return true;
        }
        return StringUtils.isBlank((String) value);
    }

    /**
     *判断字符串不为空
     * 
     * @param value
     * @return
     */
    public static boolean isStringNotEmpty(final Object value){
        return !isStringEmpty(value);
    }
}
