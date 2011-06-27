/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.junit.Assert;
import org.junit.Test;

/**
 * SkipPageFirst单元测试
 *
 * @author wangwei
 */
public class SkipPageFirstTest {

    @Test
    public void testNumberIsFirst(){
        SkipPageFirst skipFirst = new SkipPageFirst();
        PageOut page = skipFirst.skip(10,0,"first","http://test.com/dddd_0.html");
        Assert.assertEquals("first", page.getLabel());
        Assert.assertEquals(page.getUrl(), null);
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
    }
    
    @Test
    public void testSkipFirst(){
        SkipPageFirst skipFirst = new SkipPageFirst();
        PageOut page = skipFirst.skip(10,1,null,"http://test.com/dddd_0.html");
        Assert.assertEquals("首页", page.getLabel());
        Assert.assertEquals("http://test.com/dddd_0.html",page.getUrl());
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
    }
}
