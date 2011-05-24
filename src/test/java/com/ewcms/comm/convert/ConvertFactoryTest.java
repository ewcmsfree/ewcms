/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ewcms.common.convert.ConvertFactory;

/**
 *  测试ParseTypeHandlerFactory对象
 * 
 * @author 王伟
 */
public class ConvertFactoryTest {

    @Test
    public void testConvertHandler() throws Exception {
        assertNotNull(ConvertFactory.instance.convertHandler(Byte.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Integer.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Short.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Long.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Boolean.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Float.class));
        assertNotNull(ConvertFactory.instance.convertHandler(Double.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.math.BigDecimal.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.math.BigInteger.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.util.Date.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.sql.Date.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.sql.Time.class));
        assertNotNull(ConvertFactory.instance.convertHandler(java.sql.Timestamp.class));
        assertNotNull(ConvertFactory.instance.convertHandler(String.class));
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void testNullConvertHandler() throws Exception {
        ConvertFactory.instance.convertHandler(java.util.Timer.class);
    }
}
