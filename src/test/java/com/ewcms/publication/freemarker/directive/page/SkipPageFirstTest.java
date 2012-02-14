/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.uri.UriRuleable;

/**
 * SkipPageFirst单元测试
 *
 * @author wangwei
 */
public class SkipPageFirstTest {

    @Test
    public void testNumberIsFirst()throws Exception{
        SkipPageFirst skipFirst = new SkipPageFirst();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("/test");
        PageOut page = skipFirst.skip(10,0,"first",rule);
        Assert.assertEquals("first", page.getLabel());
        Assert.assertEquals(page.getUrl(), "/test");
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
        Assert.assertFalse(page.isActive());
    }
    
    @Test
    public void testSkipFirst()throws Exception{
        SkipPageFirst skipFirst = new SkipPageFirst();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("http://test.com/dddd_0.html");
        PageOut page = skipFirst.skip(10,1,null,rule);
        Assert.assertEquals("首页", page.getLabel());
        Assert.assertEquals("http://test.com/dddd_0.html",page.getUrl());
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
        Assert.assertTrue(page.isActive());
    }
}
