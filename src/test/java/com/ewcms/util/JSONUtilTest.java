/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public class JSONUtilTest {

    private static Log log = LogFactory.getLog(JSONUtilTest.class);

    @Test
    public void testToJSONByObject() {
        JSONModel model = new JSONModel();
        model.setId(1);
        model.setMoney(1.1f);
        model.setName("中文");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2009, 0, 1);
        model.setBrithDate(new Date(calendar.getTimeInMillis()));

        String jsonString = JSONUtil.toJSON(model);
        log.info(jsonString);
        Assert.assertEquals(jsonString, "{\"name\":\"中文\",\"id\":1,\"brithDate\":\"2009-01-01\",\"money\":1.1}");
    }

    @Test
    public void testToJSONByObjectDateFormat(){
        JSONModel model = new JSONModel();
        model.setId(1);
        model.setMoney(1.1f);
        model.setName("中文");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2009, 0, 1);
        model.setBrithDate(new Date(calendar.getTimeInMillis()));

        String jsonString = JSONUtil.toJSON(model,new SimpleDateFormat("yyyy年MM月dd日"));
        log.info(jsonString);
        Assert.assertEquals(jsonString, "{\"name\":\"中文\",\"id\":1,\"brithDate\":\"2009年01月01日\",\"money\":1.1}");
    }

    @Test
    public void testToJSONByArrayObject() {
        JSONModel model = new JSONModel();
        model.setId(1);
        model.setMoney(1.1f);
        model.setName("中文");

        JSONModel[] array = new JSONModel[]{model, model};
        String jsonString = JSONUtil.toJSON(array);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1}]");

    }

    @Test
    public void testToJSONByListObject() {
        JSONModel model = new JSONModel();
        model.setId(1);
        model.setMoney(1.1f);
        model.setName("中文");
        List<JSONModel> list = new ArrayList<JSONModel>();
        list.add(model);
        list.add(model);

        String jsonString = JSONUtil.toJSON(list);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1}]");
    }

    @Test
    public void testToJSONByMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", "中文");
        map.put("id", 1);
        map.put("brithDate", null);
        map.put("money", 1.1f);

        String jsonString = JSONUtil.toJSON(map);
        log.info(jsonString);
        Assert.assertEquals(jsonString, "{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1}");
    }

    @Test
    public void testToJSONByArrayMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", "中文");
        map.put("id", 1);
        map.put("brithDate", null);
        map.put("money", 1.1f);

        Map<String, Object>[] array = new Map[2];
        array[0] = map;
        array[1] = map;

        String jsonString = JSONUtil.toJSON(array);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1}]");
    }

    @Test
    public void testToJSONByListMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", "中文");
        map.put("id", 1);
        map.put("brithDate", null);
        map.put("money", 1.1f);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map);
        list.add(map);

        String jsonString = JSONUtil.toJSON(list);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1}]");
    }

    @Test
    public void testCreateMapByArray() {
        String[] aliases = new String[]{"name", "id", "brithDate", "money"};
        Object[] values = new Object[]{"中文", 1, null, 1.1f};

        Map<String, Object> map = JSONUtil.createMap(aliases, values);

        Assert.assertTrue(map.size() == 4);
        Assert.assertEquals(map.get("name"), "中文");
        Assert.assertEquals(map.get("money"), 1.1f);
    }

    @Test
    public void testCreateMapByList() {
        List<String> aliases = new ArrayList<String>();
        aliases.add("name");
        aliases.add("id");
        aliases.add("brithDate");
        aliases.add("money");
        List<Object> values = new ArrayList<Object>();
        values.add("中文");
        values.add(1);
        values.add(null);
        values.add(1.1f);

        Map<String, Object> map = JSONUtil.createMap(aliases, values);

        Assert.assertTrue(map.size() == 4);
        Assert.assertEquals(map.get("name"), "中文");
        Assert.assertEquals(map.get("money"), 1.1f);
    }

    /**
     * unit test JSONString.toJSON(String[] aliases,List<Object[]> values)
     */
    @Test
    public void testToJSONArrayCompound() {
        String[] aliases = new String[]{"name", "id", "brithDate", "money"};
        List<Object[]> values = new ArrayList<Object[]>();
        values.add(new Object[]{"中文", 1, null, 1.1f});
        values.add(new Object[]{"中文1", 2, null, 2.1f});

        String jsonString = JSONUtil.toJSON(aliases, values);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文1\",\"id\":2,\"brithDate\":null,\"money\":2.1}]");

    }

    /**
     * unit test JSONString.toJSON(List<String> aliases,List<List<Object>> values)
     */
    @Test
    public void testToJSONListCompound() {
        List<String> aliases =Arrays.asList(new String[]{"name", "id", "brithDate", "money"});
        List<List<Object>> values = new ArrayList<List<Object>>();
        values.add(Arrays.asList(new Object[]{"中文", 1, null, 1.1f}));
        values.add(Arrays.asList(new Object[]{"中文1", 2, null, 2.1f}));

        String jsonString = JSONUtil.toJSON(aliases, values);
        log.info(jsonString);
        Assert.assertEquals(jsonString,
                "[{\"name\":\"中文\",\"id\":1,\"brithDate\":null,\"money\":1.1},{\"name\":\"中文1\",\"id\":2,\"brithDate\":null,\"money\":2.1}]");

    }

    
}
