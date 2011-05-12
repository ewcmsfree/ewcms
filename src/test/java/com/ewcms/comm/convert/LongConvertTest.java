/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;

/**
 * 测试Long转换
 * 
 * @author 王伟
 */
public class LongConvertTest {

    private Convertable<Long> handler;
   
    @Before
    public void setUp(){
        this.handler = new LongConvert();
    }
    
    @Test
    public void testParseSuccess()throws Exception{

        String test = "1000000";
        Long testLong = handler.parse(test);
        assertTrue(1000000 == testLong.longValue());
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception{

        String test = "1000.0";
        handler.parse(test);
    }
    
    @Test
    public void testParseString(){
        assertEquals(handler.parseString(Long.valueOf(100L)),"100");
    }
}
