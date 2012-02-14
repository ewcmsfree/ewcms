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
 * SkipPageLast单元测试
 * 
 * @author wangwei
 */
public class SkipPageLastTest {

    @Test
    public void testNumberIsLast()throws Exception{
        SkipPageLast skip = new SkipPageLast();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        PageOut page = skip.skip(10,9,"last",rule);
        Assert.assertEquals("last", page.getLabel());
        Assert.assertEquals("",page.getUrl());
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
    
    @Test
    public void testSkipLast()throws Exception{
        String url = "http://test.com/dddd_8.html";
        SkipPageLast skip = new SkipPageLast();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn(url);
        PageOut page = skip.skip(10,8,null,rule);
        Assert.assertEquals("未页", page.getLabel());
        Assert.assertEquals(url,page.getUrl());
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
}
