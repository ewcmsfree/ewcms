/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.junit.Assert;
import org.junit.Test;

/**
 * SkipPagePrevious单元测试
 * 
 * @author wangwei
 */
public class SkipPagePreviousTest {

    @Test
    public void testNumberIsFirst() {
        String url = "http://test.com/dddd_0.html";
        SkipPagePrevious skip = new SkipPagePrevious();
        PageOut page = skip.skip(10,0,"prev",url);
        Assert.assertEquals("prev",page.getLabel());
        Assert.assertEquals(page.getUrl(),null);
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
    }
    
    @Test
    public void testSkipPrev(){
        String url = "http://test.com/dddd_0.html";
        SkipPagePrevious skip = new SkipPagePrevious();
        PageOut page = skip.skip(10,1,"prev",url);
        Assert.assertEquals("prev",page.getLabel());
        Assert.assertEquals(page.getUrl(),url);
        Assert.assertEquals(Integer.valueOf(1), page.getNumber());
        
        page = skip.skip(10,2,"prev",url);
        Assert.assertEquals(Integer.valueOf(2), page.getNumber());
    }
}
