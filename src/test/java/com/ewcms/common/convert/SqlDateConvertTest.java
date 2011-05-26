/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试转换java.sql.Date
 * 
 * @author 王伟
 */
public class SqlDateConvertTest {

    private ConvertDateable<Date> handler;

    @Before
    public void setUp() {
        handler = new SqlDateConvert();
    }

    @Test
    public void testParseSuccess() throws Exception {

        String test = "2006-01-12";
        Date date = handler.parse(test);
        assertEqualsDate(date, 2006, 0, 12);

        test = "2006-5-9";
        date = handler.parse(test);
        assertEqualsDate(date, 2006, 4, 9);

        test = "2005/06/01";
        handler.setFormat("yyyy/MM/dd");
        date = handler.parse(test);
        assertEqualsDate(date, 2005, 5, 1);
    }

    private void assertEqualsDate(Date date,
            int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(month, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception {

        String test = "2006-01d-12";
        handler.parse(test);
    }

    @Test
    public void testParseString() throws Exception {
        String test = "2006-01-12 00:00:00";
        Date testDate = (Date) handler.parse(test);
        assertEquals(handler.parseString(testDate), test);
    }
}
