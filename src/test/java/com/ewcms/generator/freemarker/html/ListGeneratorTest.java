/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.freemarker.directive.page.PageOutDirective;
import com.ewcms.generator.freemarker.directive.page.SkipDirective;
import com.ewcms.generator.freemarker.directive.page.SkipNumberDirecitve;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.service.ArticleLoaderServiceable;

import freemarker.template.Configuration;

/**
 * ListGeneratorHtml单元测试
 * 
 * @author wangwei
 */
public class ListGeneratorTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("page_number", new SkipNumberDirecitve());
        cfg.setSharedVariable("page_skip", new SkipDirective());
        cfg.setSharedVariable("page", new PageOutDirective());
    }
    
    @Test
    public void testListTemplate()throws Exception{
        ArticleLoaderServiceable service = mock(ArticleLoaderServiceable.class);
        when(service.getArticleCount(any(Integer.class))).thenReturn(100);
        
        ListGenerator generator = new ListGenerator(cfg,initSite(),initChannel(),service);
        List<OutputResource> resources = generator.process(initTemplate(getTemplatePath("list.html")));
        Assert.assertEquals(10, resources.size());
        Assert.assertEquals("news/cn/0.html", resources.get(0).getReleasePath());
        Assert.assertEquals("news/cn/9.html", resources.get(9).getReleasePath());
    }
    
    private Site initSite(){
        Site  site = new Site();
        
        return site;
    }
    
    private Channel initChannel(){
        Channel channel = new Channel();
        
        channel.setId(1);
        channel.setListSize(10);
        channel.setAbsUrl("/news/cn");
        
        return channel;
    }
    
    private String getTemplatePath(String name) {
        return String.format("html/%s", name);
    }
    
    private Template initTemplate(String path){
        Template template = new Template();
        
        template.setUniquePath(path);
        template.setType(TemplateType.LIST);
        
        return template;
    }

}
