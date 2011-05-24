/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.comm.convert;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ewcms.common.convert.BigIntegerConvert;
import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.Convertable;

/**
 *
 * @author wangwei
 */
public class BigIntegerConvertTest {

    private Convertable<BigInteger> handler;

    @Before
    public void setUp() {
        handler = new BigIntegerConvert();
    }

    @Test
    public void testParseSuccess() throws Exception {

        BigInteger bigInteger = handler.parse("200");
        assertEquals(200, bigInteger.intValue());
    }

   @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception {
        String test = "wangwei";
        handler.parse(test);
    }

    @Test
    public void testParseString() {
        BigInteger bigInteger = new BigInteger("200");
        assertEquals(handler.parseString(bigInteger), "200");
    }
}
