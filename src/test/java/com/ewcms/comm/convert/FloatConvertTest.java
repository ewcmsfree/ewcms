/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * 测试Float 转换
 * 
 * @author 王伟
 */
public class FloatConvertTest {

   private Convertable<Float> handler;
    
    @Before
    public void setUp(){
        this.handler = new FloatConvert();
    }
    
    @Test
    public void testParseSuccess() throws Exception{

        String test = "20";
        Float testFloat = handler.parse(test);
        assertEquals(20, testFloat, 0.0001);

        test = "0.99";
        testFloat = handler.parse(test);
        assertEquals(0.99, testFloat, 0.0001);
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception{
        String test = "20.0.0";
        handler.parse(test);
    }
    
    @Test
    public void testParseString(){
        assertEquals(handler.parseString(Float.valueOf((float)200)),"200.0");
    }
}
