/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ewcms.common.convert.BooleanConvert;
import com.ewcms.common.convert.Convertable;

/**
 * 测试BooleanTypeHandler
 * 
 * @author 王伟
 */
public class BooleanConvertTest {

    private Convertable<Boolean> handler;
    
    @Before
    public void setUp(){
        handler = new BooleanConvert();
    }
    
    @Test
    public void testParseSuccess() throws Exception{

        assertTrue(handler.parse("TRUE"));
        assertTrue(handler.parse("True"));
        assertTrue(handler.parse("tRuE"));

        assertFalse(handler.parse("FALSE"));
        assertFalse(handler.parse("False"));
        assertFalse(handler.parse("F"));
        assertFalse(handler.parse("sdfw"));
    }
    
    @Test
    public void testParseString(){
        
        assertEquals(handler.parseString(Boolean.valueOf(true)),"true");
        assertEquals(handler.parseString(Boolean.valueOf(false)),"false");
    }
}
