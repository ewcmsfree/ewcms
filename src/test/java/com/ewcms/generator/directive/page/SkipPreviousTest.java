/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.page;

import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author wangwei
 */
public class SkipPreviousTest {

    @Test
    public void testSkip() {
        PageParam pageParam = new PageParam();
        pageParam.setCount(10);
        pageParam.setPage(2);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");

        SkipPrevious skip = new SkipPrevious();
        Page page = skip.skip(pageParam);
        Assert.assertEquals(page.getLabel(), "上一页");
        Assert.assertEquals(page.getUrl(),"http://test.com/dddd_1.html");
        Assert.assertTrue(page.isEnabled());

        pageParam.setPage(0);
        page = skip.skip(pageParam);
        Assert.assertFalse(page.isEnabled());
    }
}
