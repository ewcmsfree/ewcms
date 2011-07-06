/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.uri.UriRuleable;

/**
 * SkipPagePrevious单元测试
 * 
 * @author wangwei
 */
public class SkipPagePreviousTest {

    @Test
    public void testNumberIsFirst() throws Exception{
        SkipPagePrevious skip = new SkipPagePrevious();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        PageOut page = skip.skip(10,0,"prev",rule);
        Assert.assertEquals("prev",page.getLabel());
        Assert.assertEquals(page.getUrl(),null);
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
    }
    
    @Test
    public void testSkipPrev()throws Exception{
        String url = "http://test.com/dddd_0.html";
        SkipPagePrevious skip = new SkipPagePrevious();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn(url);
        PageOut page = skip.skip(10,0,"prev",rule);
        Assert.assertEquals("prev",page.getLabel());
        Assert.assertEquals(page.getUrl(),null);
        page = skip.skip(10,1,"prev",rule);
        Assert.assertEquals(page.getUrl(),url);
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
        
        page = skip.skip(10,2,"prev",rule);
        Assert.assertEquals(Integer.valueOf(2), page.getNumber());
    }
}
