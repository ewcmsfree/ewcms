/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.directive.page;

import org.junit.Assert;
import org.junit.Test;

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
