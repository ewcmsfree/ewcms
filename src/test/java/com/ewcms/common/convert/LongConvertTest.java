/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
