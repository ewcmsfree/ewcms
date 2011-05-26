/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.freemarker.directive.page.Page;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.generator.freemarker.directive.page.SkipLast;

/**
 *
 * @author wangwei
 */
public class SkipLastTest {

    @Test
    public void testSkip() {
        PageParam pageParam = new PageParam();
        pageParam.setCount(10);
        pageParam.setPage(1);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");

        SkipLast skip = new SkipLast();
        Page page = skip.skip(pageParam);
        Assert.assertEquals("末页", page.getLabel());
        Assert.assertEquals(page.getUrl(), "http://test.com/dddd_9.html");
        Assert.assertTrue(page.isEnabled());

        pageParam.setPage(9);
        page = skip.skip(pageParam);
        Assert.assertFalse(page.isEnabled());
    }
}
