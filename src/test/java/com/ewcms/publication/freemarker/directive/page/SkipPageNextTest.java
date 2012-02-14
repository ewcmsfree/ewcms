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
 * SkipPageNext单元测试
 * 
 * @author wangwei
 */
public class SkipPageNextTest {

    @Test
    public void testNumberIsLast()throws Exception{
        SkipPageNext skip = new SkipPageNext();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("/last.html");
        PageOut page = skip.skip(10,9,"next",rule);
        Assert.assertEquals("next",page.getLabel());
        Assert.assertEquals(page.getUrl(),"/last.html");
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
    
    @Test
    public void testSkipNext()throws Exception{
        String url = "http://test.com/dddd_3.html";
        SkipPageNext skip = new SkipPageNext();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn(url);
        PageOut page = skip.skip(10,8,"next",rule);
        Assert.assertEquals("next",page.getLabel());
        Assert.assertEquals(page.getUrl(),url);
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
        
        page = skip.skip(10,7,"next",rule);
        Assert.assertEquals(Integer.valueOf(9), page.getNumber());
    }
    
   
}
