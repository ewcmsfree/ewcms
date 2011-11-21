/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.util;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析ListView对象中的value值,并进行排序。
 * <ul>
 * value值可以有几种写法：
 * <li>key=value,key=value,......
 * <li>key=value,1900-2008,key=value,......
 * <li>1900-2008,2009-2100,......
 * </ul>
 * 
 * @author 吴智俊
 */
public class AnalysisUtil {

    public static Map<String, String> analysis(String value) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        try {
            String[] array = value.split(",");
            for (String arrayValue : array) {
                if (arrayValue.indexOf("-") > 0) {
                    String[] range = arrayValue.split("-");
                    String begin = range[0];
                    String end = range[1];
                    if (isNumber(begin) && isNumber(end)) {
                        Integer beginInt = Integer.parseInt(begin);
                        Integer endInt = Integer.parseInt(end);
                        if (beginInt <= endInt) {
                            for (int i = beginInt; i <= endInt; i++) {
                                map.put(String.valueOf(i), String.valueOf(i));
                            }
                        }
                    }
                } else if (arrayValue.indexOf("=") > 0) {
                    String[] range = arrayValue.split("=");
                    String k = range[0];
                    String v = range[1];
                    map.put(k, v);
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    //字符串是否数值
    private static Boolean isNumber(String s) {
        Pattern pa = Pattern.compile("[0-9]*");
        Matcher ma = pa.matcher(s);
        if (ma.matches()) {
            return true;
        }
        return false;
    }
//	字符串中含有数值
//	private static void ContainNumber(String s) {
//		Pattern pa = Pattern.compile("\\d+");
//		Matcher ma = pa.matcher(s);
//		while (ma.find()) {
//			System.out.println(ma.group());
//		}
//	}
}
