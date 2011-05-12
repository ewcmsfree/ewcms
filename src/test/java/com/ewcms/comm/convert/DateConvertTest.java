/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试java.util.Date转换
 * 
 * @author 王伟
 */
public class DateConvertTest {

    private Convertable<Date> handler;
    
    @Before
    public void setUp(){
        handler = new DateConvert();
    }
    
    @Test
    public void testParseSuccess()throws Exception{

        String dateString = "2005-06-01 20:12:24";
        Date date = handler.parse(dateString);
        assertEqualsDate(date,2005,5,1,20,12,24);

        dateString = " 2005-06-01 ";
        date = handler.parse(dateString);
        assertEqualsDate(date,2005,5,1,0,0,0);

        dateString = "2005/06/01";
        ((ConvertDate)handler).setFormat("yyyy/MM/dd");
        date = handler.parse(dateString);
        assertEqualsDate(date,2005,5,1,0,0,0);
    }

    private void assertEqualsDate(Date date,
            int year,int month,int day,int hour,int minute,int second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(month, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
        assertEquals(second, calendar.get(Calendar.SECOND));
    }

    @Test(expected=ConvertException.class)
    public void testParseFail()throws Exception{
        String dateString = "2005/06/01";
        handler.parse(dateString);
    }
    
    @Test
    public void testParseString()throws Exception{
        String dateString = "2005-06-01 20:12:24";
        Date date = (Date) handler.parse(dateString);
        assertEquals(handler.parseString(date),dateString);
    }
}
