/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.page;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * SkipNumberDirective单元测试
 * 
 * @author wangwei
 */
public class SkipNumberDirectiveTest extends FreemarkerTest {

    @Test
    public void testMaxIsOneGetPageOuts()throws Exception{
        SkipNumberDirective directive = new SkipNumberDirective();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        List<PageOut> pages = directive.getPageOuts(rule,5, 0, 1, "..");
        Assert.assertEquals(2, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(1).getLabel(),"..");
    }
    
    @Test
    public void testMaxGeCountGetPageOuts()throws Exception{
        SkipNumberDirective directive = new SkipNumberDirective();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        List<PageOut> pages = directive.getPageOuts(rule,5, 0, 6, "");
        Assert.assertEquals(5, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(4).getLabel(),"5");
    }
    
    @Test
    public void testMaxGePageNumberGetPageOuts()throws Exception{
        SkipNumberDirective directive = new SkipNumberDirective();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        List<PageOut> pages = directive.getPageOuts(rule,10, 5, 7, "..");
        Assert.assertEquals(8, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(7).getLabel(),"..");
    }
    
    @Test
    public void testMiddleGetPgeOuts()throws Exception{
        SkipNumberDirective directive = new SkipNumberDirective();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        List<PageOut> pages = directive.getPageOuts(rule,20, 10, 7, "..");
        Assert.assertEquals(8, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "..");
        Assert.assertEquals(pages.get(1).getLabel(),"8");
        Assert.assertEquals(pages.get(7).getLabel(), "..");
    }
    
    
    @Test
    public void testEndGetPgeOuts()throws Exception{
        SkipNumberDirective directive = new SkipNumberDirective();
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        List<PageOut> pages = directive.getPageOuts(rule,20, 17, 7, "..");
        Assert.assertEquals(8, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "..");
        Assert.assertEquals(pages.get(1).getLabel(),"14");
        Assert.assertEquals(pages.get(7).getLabel(), "20");
    }
    
    @Override
    protected void currentConfiguration(Configuration cfg) {
         cfg.setSharedVariable("page_number", new SkipNumberDirective());
         cfg.setSharedVariable("page", new PageOutDirective());
    }
    
    /**
     * 得到模板路径
     * 
     * @param name 模板名
     * @return
     */
    private String getTemplatePath(String name){
        return String.format("directive/page/%s", name);
    }
    
    @Test
    public void testNumberTemplate()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("number.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(10));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(20));
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        params.put(GlobalVariable.URI_RULE.toString(), rule);
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        String expected="..8910111213..";
        Assert.assertEquals(expected, value);
    }
    
    @Test
    public void testNumberLoopTemplate()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("numberloop.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(10));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(20));
        UriRuleable rule = mock(UriRuleable.class);
        when(rule.getUri()).thenReturn("");
        params.put(GlobalVariable.URI_RULE.toString(), rule);
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        String expected="##8910111213##";
        Assert.assertEquals(expected, value);
    }
    
    @Test
    public void testNumberActiveTemplate()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("numberactive.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(10));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(20));
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        String expected="11/20";
        Assert.assertEquals(expected, value);
    }
}
