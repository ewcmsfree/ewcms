/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.Before;

import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;
import com.ewcms.common.convert.IntegerConvert;

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
