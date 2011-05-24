/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.comm.convert;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ewcms.common.convert.BigDecimalConvert;
import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;

/**
 *
 * @author wangwei
 */
public class BigDecimalConvertTest {

    private Convertable<BigDecimal> handler;

    @Before
    public void setUp(){
        handler = new BigDecimalConvert();
    }

    @Test
    public void testParseSuccess() throws Exception{

        BigDecimal bigDecimal = handler.parse("20.9");
        assertEquals(20.9, bigDecimal.floatValue(), 0.0001);
    }

    @Test(expected=ConvertException.class)
    public void testParseFail()throws Exception{
        String test = "wangwei";
        handler.parse(test);
    }
     
    @Test
    public void testParseString(){
        BigDecimal bigDecimal = new BigDecimal(20);
        assertEquals(handler.parseString(bigDecimal),"20");
    }
}
