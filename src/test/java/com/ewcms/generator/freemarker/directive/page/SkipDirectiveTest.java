/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.freemarker.GlobalVariable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * SkipDirective单元测试
 * 
 * @author wangwei
 */
public class SkipDirectiveTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("page_skip", new SkipDirective());
        cfg.setSharedVariable("page", new PageOutDirective());
    }
    
    @Test
    public void testInitAliasesMapProperties()throws Exception{
        
        String[] aliases = initAliases();
        SkipDirective directive = new SkipDirective();
        for(String alias : aliases){
            SkipPageable<PageOut> skipPage = directive.getSkipPage(alias);
            Assert.assertNotNull(skipPage);
        }
    }
    
    private String[] initAliases(){
        return new String[]{
             "f","first","首","首页",
             "n","next","下","下一页",
             "p","prev","上","上一页",
             "l","last","末","末页"
        };
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
    public void testPageCountIsOnlyOne() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("skip.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(0));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(1));
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("，，，",value);
    }
//
    @Test
    public void testSkipTemplate() throws Exception {
        Template template = cfg.getTemplate(getTemplatePath("skip.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(0));
        params.put(GlobalVariable.PAGE_COUNT.toString(), Integer.valueOf(5));
        String value = this.process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("第一页，下一页，上一页，未页",value);
    }
//
//    @Test
//    public void testExecuteLoop() throws Exception {
//        Template template = cfg.getTemplate("www/page/skip_first_loop.html");
//        Map params = new HashMap();
//        PageParam pageParam = new PageParam();
//        pageParam.setCount(5);
//        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
//        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageParam);
//        String value = this.process(template, params);
//        log.info(value);
//
//        Assert.assertTrue(value.indexOf("第一页loop") != -1);
//        Assert.assertTrue(value.indexOf("http://test.com/dddd_0.html") != -1);
//    }
}
