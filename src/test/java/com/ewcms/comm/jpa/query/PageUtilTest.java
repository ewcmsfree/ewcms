/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */

package com.ewcms.comm.jpa.query;

import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author wangwei
 */
public class PageUtilTest {

    @Test
    public void testPageCount(){
        int count = 0;
        int rows = 15;
        int pageCount = PageUtil.pageCount(count, rows);
        Assert.assertTrue(pageCount == 0);

        count = 1;
        pageCount = PageUtil.pageCount(count, rows);
        Assert.assertTrue(pageCount == 1);

        count = 15;
        pageCount = PageUtil.pageCount(count, rows);
        Assert.assertTrue(pageCount == 1);

        count = 16;
        pageCount = PageUtil.pageCount(count, rows);
        Assert.assertTrue(pageCount == 2);
    }

    @Test
    public void testDefaultZero(){
        Object value = PageUtil.defaultZero(null);
        Assert.assertTrue((Integer)value == 0);
        value = PageUtil.defaultZero(1.1f);
        Assert.assertEquals(value, 1.1f);
    }

    @Test
    public void testArrayDefaultZero(){
        Object[] o = new Object[]{1,null};

        Object[] values =(Object[])PageUtil.defaultZero(o);

        Assert.assertTrue(values.length == 2);
        Assert.assertTrue((Integer)values[0] == 1);
        Assert.assertTrue((Integer)values[1] == 0);
    }
}
