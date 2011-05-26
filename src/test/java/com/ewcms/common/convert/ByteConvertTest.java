/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ewcms.common.convert.ByteConvert;
import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;

/**
 * 测试byte转换
 * 
 * @author 王伟
 */
public class ByteConvertTest {

    private Convertable<Byte> handler;
    
    @Before
    public void setUp(){
        handler = new ByteConvert();
    }
    
    @Test
    public void testParseSuccess() throws Exception{
        String test = "22";
        Byte testByte = handler.parse(test);
        assertEquals(testByte.intValue(), 22);
    }

    @Test(expected=ConvertException.class)
    public void testParseFail()throws Exception{
        String test = "wangwei";
        handler.parse(test);
    }

    @Test
    public void testParseString()throws Exception{
        assertEquals(handler.parseString(Byte.valueOf((byte)1)),"1");
    }
}
