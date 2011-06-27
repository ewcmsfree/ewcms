/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import org.junit.Assert;
import org.junit.Test;

/**
 * SkipPageLast单元测试
 * 
 * @author wangwei
 */
public class SkipPageLastTest {

    @Test
    public void testNumberIsLast() {
        String url = "http://test.com/dddd_9.html";
        SkipPageLast skip = new SkipPageLast();
        PageOut page = skip.skip(10,9,"last",url);
        Assert.assertEquals("last", page.getLabel());
        Assert.assertEquals(null,page.getUrl());
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
    
    @Test
    public void testSkipLast(){
        String url = "http://test.com/dddd_8.html";
        SkipPageLast skip = new SkipPageLast();
        PageOut page = skip.skip(10,8,null,url);
        Assert.assertEquals("未页", page.getLabel());
        Assert.assertEquals(url,page.getUrl());
        Assert.assertEquals(Integer.valueOf(10), page.getNumber());
    }
}
