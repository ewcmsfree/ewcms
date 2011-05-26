/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
