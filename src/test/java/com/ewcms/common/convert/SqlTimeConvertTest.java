/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试Time转换成
 * 
 * @author 王伟
 */
public class SqlTimeConvertTest {

    private ConvertDateable<Time> handler;

    @Before
    public void setUp() {
        handler = new SqlTimeConvert();
    }

    @Test
    public void testParseSuccess() throws Exception {

        String test = "20:15:40";
        Time time = handler.parse(test);
        assertEqualsTime(time, 20, 15, 40);

        test = "20-15-40";
        handler.setFormat("HH-mm-ss");
        time = handler.parse(test);
        assertEqualsTime(time, 20, 15, 40);
    }

    private void assertEqualsTime(Time time, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
        assertEquals(second, calendar.get(Calendar.SECOND));
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception {

        String test = "20:154a0";
        handler.parse(test);
    }

    @Test
    public void testParseString() throws Exception {
        String test = "20:15:40";
        Time testTime = (Time) handler.parse(test);
        assertEquals(handler.parseString(testTime), test);
    }
}
