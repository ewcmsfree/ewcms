/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.freemarker.directive.page.PageOutDirective;
import com.ewcms.publication.freemarker.directive.page.SkipDirective;
import com.ewcms.publication.freemarker.directive.page.SkipNumberDirective;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.service.ArticlePublishServiceable;

import freemarker.template.Configuration;

/**
 * ListGeneratorHtml单元测试
 * 
 * @author wangwei
 */
public class ListGeneratorTest  extends GeneratorTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("page_number", new SkipNumberDirective());
        cfg.setSharedVariable("page_skip", new SkipDirective());
        cfg.setSharedVariable("page", new PageOutDirective());
    }
    
    private void assertPage0Content(OutputResource resource)throws Exception{
        StringBuilder builder = new StringBuilder();
        builder.append("listpage");
        for(int i = 0 ; i < 7 ; ++i ){
            builder.append("/news/cn/").append(i).append(".html|").append(i+1);
        }
        builder.append("|..");
        
        String content = this.getContent(resource);
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals(builder.toString(), content);
    }
    
    private void assertPage9Content(OutputResource resource)throws Exception{
        StringBuilder builder = new StringBuilder();
        builder.append("listpage");
        builder.append("|..");
        for(int i = 3 ; i < 10 ; ++i ){
            builder.append("/news/cn/").append(i).append(".html|").append(i+1);
        }
        String content = this.getContent(resource);
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals(builder.toString(), content);
    }
    
    @Test
    public void testListTemplate()throws Exception{
        ArticlePublishServiceable service = mock(ArticlePublishServiceable.class);
        when(service.getArticleCount(any(Integer.class))).thenReturn(100);
        
        ListGenerator generator = new ListGenerator(cfg,service,false);
        List<OutputResource> resources = generator.process(initSite(),initChannel(),initTemplate(getTemplatePath("list.html")));
        Assert.assertEquals(10, resources.size());
        Assert.assertEquals("/news/cn/0.html", resources.get(0).getUri());
        assertPage0Content(resources.get(0));
        Assert.assertEquals("/news/cn/9.html", resources.get(9).getUri());
        assertPage9Content(resources.get(9));
    }
    
    @Test
    public void teestListTemplateCreateHome()throws Exception{
    	 ArticlePublishServiceable service = mock(ArticlePublishServiceable.class);
         when(service.getArticleCount(any(Integer.class))).thenReturn(100);
         
         ListGenerator generator = new ListGenerator(cfg,service,true);
         List<OutputResource> resources = generator.process(initSite(),initChannel(),initTemplate(getTemplatePath("list.html")));
         Assert.assertEquals(11, resources.size());
         Assert.assertEquals("/news/cn/index.html", resources.get(0).getUri());
         assertPage0Content(resources.get(0));
         Assert.assertEquals("/news/cn/0.html", resources.get(1).getUri());
         assertPage0Content(resources.get(1));
         Assert.assertEquals("/news/cn/9.html", resources.get(10).getUri());
         assertPage9Content(resources.get(10));
    }
  
}
