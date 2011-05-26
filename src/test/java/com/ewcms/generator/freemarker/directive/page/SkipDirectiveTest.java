/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.generator.freemarker.directive.page.SkipDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class SkipDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(SkipDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("page_skip", new SkipDirective());
    }

    @Test
    public void testGetSkipType()throws Exception{
        SkipDirective directive = new SkipDirective();

        SkipDirective.SkipType type = directive.getSkipType("f");
        Assert.assertEquals(type, SkipDirective.SkipType.First);
        type = directive.getSkipType("first");
        Assert.assertEquals(type, SkipDirective.SkipType.First);

        type = directive.getSkipType("l");
        Assert.assertEquals(type, SkipDirective.SkipType.Last);
        type = directive.getSkipType("last");
        Assert.assertEquals(type, SkipDirective.SkipType.Last);

        type = directive.getSkipType("p");
        Assert.assertEquals(type, SkipDirective.SkipType.Previous);
        type = directive.getSkipType("prev");
        Assert.assertEquals(type, SkipDirective.SkipType.Previous);

        type = directive.getSkipType("n");
        Assert.assertEquals(type, SkipDirective.SkipType.Next);
        type = directive.getSkipType("next");
        Assert.assertEquals(type, SkipDirective.SkipType.Next);
    }

    /**
     * 页面数小于或等于一不显示翻页
     * 
     * @throws Exception
     */
    @Test
    public void testExecuteNotShow() throws Exception {
        Template template = cfg.getTemplate("www/page/skip_first.html");
        Map params = new HashMap();
        PageParam pageParam = new PageParam();
        pageParam.setCount(1);
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.trim().length() == 0);
    }

    @Test
    public void testExecute() throws Exception {
        Template template = cfg.getTemplate("www/page/skip_first.html");
        Map params = new HashMap();
        PageParam pageParam = new PageParam();
        pageParam.setCount(5);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("第一页") != -1);
        Assert.assertTrue(value.indexOf("第一页全") != -1);
        Assert.assertTrue(value.indexOf("http://test.com/dddd_0.html") != -1);
    }

    @Test
    public void testExecuteLoop() throws Exception {
        Template template = cfg.getTemplate("www/page/skip_first_loop.html");
        Map params = new HashMap();
        PageParam pageParam = new PageParam();
        pageParam.setCount(5);
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("第一页loop") != -1);
        Assert.assertTrue(value.indexOf("http://test.com/dddd_0.html") != -1);
    }
}
