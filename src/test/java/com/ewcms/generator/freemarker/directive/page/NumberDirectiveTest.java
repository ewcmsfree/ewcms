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

import com.ewcms.generator.freemarker.directive.AbstractDirectiveTest;
import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.page.NumberDirecitve;
import com.ewcms.generator.freemarker.directive.page.Page;
import com.ewcms.generator.freemarker.directive.page.PageLabelDirective;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.generator.freemarker.directive.page.PageUrlDirective;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author wangwei
 */
public class NumberDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(NumberDirectiveTest.class);
    
    @Test
    public void testPageListNumberGtCount(){
        NumberDirecitve directive = new NumberDirecitve();
        int count =4 ;
        int number =5;
        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(2);

        List<Page> pages = directive.pageList(pageParam, number, true);
        for(Page page : pages){
            log.info(page.getLabel());
        }
        Assert.assertEquals(pages.size(), 4);
        Assert.assertFalse(pages.get(2).isEnabled());
        Assert.assertEquals(pages.get(0).getLabel(), "1");
    }

    @Test
    public void testPageListNotAllFirst(){

        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(0);

        List<Page> pages = directive.pageList(pageParam, number, false);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 6);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(5).getLabel(), "..");
    }

    @Test
    public void testPageListNotAllMiddle(){
        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(4);

        List<Page> pages = directive.pageList(pageParam, number, false);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 7);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(2).getLabel(), "..");
        Assert.assertEquals(pages.get(6).getLabel(), "..");
    }

    @Test
    public void testPageListNotAllLast(){
        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

         PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(10);

        List<Page> pages = directive.pageList(pageParam, number, false);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 6);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(2).getLabel(), "..");
        Assert.assertEquals(pages.get(5).getLabel(), "10");
    }

     @Test
    public void testPageListAllFirst(){

        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(0);

        List<Page> pages = directive.pageList(pageParam, number, true);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 6);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(5).getLabel(), "10");
    }

    @Test
    public void testPageListAllMiddle(){
        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(4);

        List<Page> pages = directive.pageList(pageParam, number, true);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 7);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(1).getLabel(), "..");
        Assert.assertEquals(pages.get(5).getLabel(), "..");
        Assert.assertEquals(pages.get(6).getLabel(), "10");
    }

    @Test
    public void testPageListAllLast(){
        NumberDirecitve directive = new NumberDirecitve();
        int count =10 ;
        int number =5;

         PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(count);
        pageParam.setPage(9);

        List<Page> pages = directive.pageList(pageParam, number, true);
        for(Page page : pages){
            log.info(page.getLabel());
        }

        Assert.assertEquals(pages.size(), 6);
        Assert.assertEquals(pages.get(0).getLabel(), "1");
        Assert.assertEquals(pages.get(1).getLabel(), "..");
        Assert.assertEquals(pages.get(5).getLabel(), "10");
    }

    @Test
    public void testPageInfo(){
        NumberDirecitve directive = new NumberDirecitve();

        PageParam pageParam = new PageParam();
        pageParam.setUrlPattern("http://test.com/dddd_%d.html");
        pageParam.setCount(10);
        pageParam.setPage(4);

        List<Page> pages = directive.pageInfo(pageParam);

        Assert.assertEquals(pages.size(), 1);
        Assert.assertEquals(pages.get(0).getLabel(), "5/10");
        Assert.assertFalse(pages.get(0).isEnabled());
        Assert.assertEquals(pages.get(0).getUrl(), "");
    }

    @Override
    protected void setDirective(Configuration cfg) {
         cfg.setSharedVariable("page_number", new NumberDirecitve());
         cfg.setSharedVariable("page_url", new PageUrlDirective());
         cfg.setSharedVariable("page_label", new PageLabelDirective());
    }

    @Test
    public void testExecute()throws Exception{
        Template template = cfg.getTemplate("www/page/number.html");
        Map params = new HashMap();
        PageParam pageParam = new PageParam();
        pageParam.setCount(10);
        pageParam.setPage(1);
        pageParam.setUrlPattern("http://test.org/dddd_%d.html");
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.indexOf("1") != -1);
        Assert.assertTrue(value.indexOf("2/10") != -1);
    }
}
