/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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
