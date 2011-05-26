/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试Integer转换
 * 
 * @author 王伟
 */
public class IntegerConvertTest {

    private Convertable<Integer> handler;
    
    @Before
    public void setUp(){
        handler = new IntegerConvert();
    }
    @Test
    public void testParseSuccess() throws Exception{

        String test = "10";
        Integer testInteger = handler.parse(test);
        assertEquals(10, testInteger.intValue());
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception{

        String test = "10.9";
        handler.parse(test);
    }
    
    @Test
    public void testParseString(){
        assertEquals(handler.parseString(Integer.valueOf(1)),"1");
    }
}
