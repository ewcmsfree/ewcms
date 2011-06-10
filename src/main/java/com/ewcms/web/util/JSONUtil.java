/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wangwei
 */
public class JSONUtil {

    private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    private static DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object object, DateFormat dateFormat) {
        try {
            objectMapper.getSerializationConfig().setDateFormat(dateFormat);
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error(e.toString());
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
