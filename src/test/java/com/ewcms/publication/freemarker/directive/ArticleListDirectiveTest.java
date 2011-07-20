/**

 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * ArticleListDirective单元测试
 * 
 * @author wangwei
 */
public class ArticleListDirectiveTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("article", new ArticleDirective());
        cfg.setSharedVariable("index", new IndexDirective());
    }

    private String getTemplatePath(String name){
        return String.format("directive/articlelist/%s", name);
    }
    
    @Test
    public void testChannelIsNull()throws Exception{
        ChannelPublishServiceable channelLoaderService = mock(ChannelPublishServiceable.class);
        when(channelLoaderService.getChannel(any(Integer.class), any(Integer.class))).thenReturn(null);
        ArticleListDirective directive = new ArticleListDirective();
        directive.setChannelService(channelLoaderService);
        cfg.setSharedVariable("alist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("value.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(1);
        params.put(GlobalVariable.SITE.toString(), site);
        String value = process(template, params);
        Assert.assertEquals("throws Exception", value);
    }
    
    @Test
    public void testChannelNotPublicenable()throws Exception{
        ChannelPublishServiceable channelLoaderService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        channel.setPublicenable(false);
        when(channelLoaderService.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ArticleListDirective directive = new ArticleListDirective();
        directive.setChannelService(channelLoaderService);
        cfg.setSharedVariable("alist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("value.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(1);
        params.put(GlobalVariable.SITE.toString(), site);
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("", value);
    }
    
    private List<Article> createArticleRow(int row) {
        List<Article> articles = new ArrayList<Article>();
        for(int i = 0 ; i < row ; i++){
            
            Article article = new Article();
            article.setId(new Long(i));
            article.setAuthor("王伟");
            article.setOrigin("163.com");
            article.setTitle("ewcms文章标签使用" + String.valueOf(i));
            article.setShortTitle("文章标签使用");
            Calendar calendar = Calendar.getInstance();
            article.setPublished(new Date(calendar.getTimeInMillis()));
            article.setSummary("介绍ewcms文章中的标签使用方法。");
            article.setImage("http://www.jict.org/image/test.jpg");
            
            articles.add(article);
        }
        return articles;
    }
    
    @Test
    public void testValueTemplate()throws Exception{
        ChannelPublishServiceable channelLoaderService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        channel.setPublicenable(true);
        when(channelLoaderService.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ArticleListDirective directive = new ArticleListDirective();
        directive.setChannelService(channelLoaderService);
        
        ArticlePublishServiceable articleLoaderService = mock(ArticlePublishServiceable.class);
        when(articleLoaderService.findReleaseArticlePage(1, 0, 10, false)).thenReturn(createArticleRow(10));
        directive.setArticleService(articleLoaderService);
        
        cfg.setSharedVariable("alist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("value.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(1);
        params.put(GlobalVariable.SITE.toString(), site);
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        
        StringBuilder expected = new StringBuilder();
        for(int i = 0 ; i < 10 ; i++){
            expected.append(i+1).append(".ewcms文章标签使用").append(i);
        }
        
        Assert.assertEquals(expected.toString(), value);
    }
    
    @Test
    public void testLoopsTemplate()throws Exception{
        ChannelPublishServiceable channelLoaderService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        channel.setId(1);
        channel.setPublicenable(true);
        when(channelLoaderService.getChannelByUrlOrPath(any(Integer.class), any(String.class))).thenReturn(channel);
        when(channelLoaderService.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ArticleListDirective directive = new ArticleListDirective();
        directive.setChannelService(channelLoaderService);
        
        ArticlePublishServiceable articleLoaderService = mock(ArticlePublishServiceable.class);
        when(articleLoaderService.findReleaseArticlePage(1, 0, 25, true)).thenReturn(createArticleRow(25));
        directive.setArticleService(articleLoaderService);
        
        cfg.setSharedVariable("alist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("loop.html"));
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(1);
        params.put(GlobalVariable.SITE.toString(), site);
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        
        StringBuilder expected = new StringBuilder();
        for(int i = 0 ; i < 25 ; i++){
            expected.append("ewcms文章标签使用").append(i);
        }
        
        Assert.assertEquals(expected.toString(), value);
    }
}
