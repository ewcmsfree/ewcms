/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.web.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author wangwei
 */
public class JSONUtil {

    private static Log log = LogFactory.getLog(JSONUtil.class);
    private static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object object, DateFormat dateFormat) {
        try {
            objectMapper.getSerializationConfig().setDateFormat(dateFormat);
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error(e.toString());
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJSON(Object object) {
        return toJSON(object, DEFAULT_DATE_FORMAT);
    }

    public static String toJSON(List<String> aliases, List<List<Object>> values, DateFormat dateFormat) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (List<Object> value : values) {
            list.add(createMap(aliases, value));
        }
        return toJSON(list, dateFormat);
    }

    public static String toJSON(List<String> aliases, List<List<Object>> values) {
        return toJSON(aliases, values, DEFAULT_DATE_FORMAT);
    }

    public static String toJSON(String[] aliases, List<Object[]> values, DateFormat dateFormat) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Object[] value : values) {
            list.add(createMap(aliases, value));
        }
        return toJSON(list, dateFormat);
    }

    public static String toJSON(String[] aliases, List<Object[]> values) {
        return toJSON(aliases, values, DEFAULT_DATE_FORMAT);
    }

    static Map<String, Object> createMap(String[] keys, Object[] values) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (int i = 0; i < keys.length; ++i) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    static Map<String, Object> createMap(List<String> keys, List<Object> values) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        for (int i = 0; i < keys.size(); ++i) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }
}
