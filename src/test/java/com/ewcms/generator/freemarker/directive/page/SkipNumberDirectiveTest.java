/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * SkipNumberDirective单元测试
 * 
 * @author wangwei
 */
public class SkipNumberDirectiveTest extends FreemarkerTest {

    @Test
    public void testMaxIsOneGetPageOuts(){
        SkipNumberDirecitve directive = new SkipNumberDirecitve();
        List<PageOut> pages = directive.getPageOuts(5, 0, 1, "", "..");
        Assert.assertEquals(2, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(1).getLabel(),"..");
    }
    
    @Test
    public void testMaxGeCountGetPageOuts(){
        SkipNumberDirecitve directive = new SkipNumberDirecitve();
        List<PageOut> pages = directive.getPageOuts(5, 0, 6, "", "..");
        Assert.assertEquals(5, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(4).getLabel(),"5");
    }
    
    @Test
    public void testMiddleGetPgeOuts(){
        SkipNumberDirecitve directive = new SkipNumberDirecitve();
        List<PageOut> pages = directive.getPageOuts(20, 10, 6, "", "..");
        Assert.assertEquals(8, pages.size());
        Assert.assertEquals(pages.get(0).getLabel(), "..");
        Assert.assertEquals(pages.get(1).getLabel(),"9");
        Assert.assertEquals(pages.get(3).getLabel(), "11");
        Assert.assertEquals(pages.get(7).getLabel(), "..");
    }
    
    @Override
    protected void currentConfiguration(Configuration cfg) {
         cfg.setSharedVariable("page_number", new SkipNumberDirecitve());
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
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        String expected="..891011121314..";
        Assert.assertEquals(expected, value);
    }
    
    @Test
    public void testNumberLoopTemplate()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("numberloop.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(10));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(20));
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        String expected="..891011121314..";
        Assert.assertEquals(expected, value);
    }
}
