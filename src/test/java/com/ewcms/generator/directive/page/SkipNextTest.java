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
public class SkipNextTest {

    @Test
    public void testSkip() {
        PageParam pageParam = new PageParam();
        pageParam.setCount(10);
        pageParam.setPage(2);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");

        SkipNext skip = new SkipNext();
        Page page = skip.skip(pageParam);
        Assert.assertEquals(page.getLabel(), "下一页");
        Assert.assertEquals(page.getUrl(),"http://test.com/dddd_3.html");
        Assert.assertTrue(page.isEnabled());

        pageParam.setPage(9);
        page = skip.skip(pageParam);
        Assert.assertFalse(page.isEnabled());
    }
}
