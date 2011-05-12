/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试String转换
 * 
 * @author 王伟
 */
public class StringConvertTest {

    private Convertable<String> handler;
    
    @Before
    public void setUp(){
        handler = new StringConvert();
    }
    
    @Test
    public void testParse() throws Exception{

        String test = "wangwei";

        assertEquals("wangwei", handler.parse(test));
    }
    
    @Test
    public void testParseString(){
        assertEquals(handler.parseString("wangwei"),"wangwei");
    }
}
