/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;

import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;
import com.ewcms.common.convert.ShortConvert;

/**
 * 测试Short转换
 * 
 * @author 王伟
 */
public class ShortConvertTest {

    private Convertable<Short> handler;
    
    @Before
    public void setUp(){
        handler = new ShortConvert();
    }
    
    @Test
    public void testParseSuccess() throws Exception{

        String test = "200";
        Short testShort = handler.parse(test);
        assertTrue(200 == testShort.shortValue());
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception{

        String test = "20.0";
        assertNull(handler.parse(test));
    }
    
    @Test
    public void testParseString(){
        assertEquals(handler.parseString(Short.valueOf((short)10)),"10");
    }
}
