/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.common.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author wangwei
 */
public class EmptyUtil {

    public static boolean isNull(Object value) {
        return value == null ? true : false;
    }

    public static boolean isNotNull(Object value){
        return !isNull(value);
    }

    public static boolean isArrayEmpty(Object value) {
        if (isNull(value)) {
            return true;
        }
        return Array.getLength(value) == 0 ? true : false;
    }

    public static boolean isArrayNotEmpty(Object value){
        return !isArrayEmpty(value);
    }

    public static boolean isCollectionEmpty(Collection value) {
        if (isNull(value)) {
            return true;
        }
        return value.isEmpty();
    }

    public static boolean isCollectionNotEmpty(Collection value){
        return !isCollectionEmpty(value);
    }

    public static boolean isMapEmpty(Map map){
        if(isNull(map)){
            return true;
        }
        return map.isEmpty();
    }

    public static boolean isMapNotEmpty(Map map){
        return !isMapEmpty(map);
    }

    /**
     * 判断是否为空
     *
     * <p>String类型</p>
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

    public static boolean isStringNotEmpty(final Object value){
        return !isStringEmpty(value);
    }
}
