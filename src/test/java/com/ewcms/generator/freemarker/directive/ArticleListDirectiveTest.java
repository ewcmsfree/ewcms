/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.generator.dao.GeneratorDAOable;
import freemarker.template.Configuration;

import com.ewcms.generator.freemarker.directive.DirectiveVariable;
import com.ewcms.generator.freemarker.directive.article.PubDateDirective;
import com.ewcms.generator.freemarker.directive.article.TitleDirective;
import com.ewcms.generator.freemarker.directive.article.UrlDirective;
import com.ewcms.generator.freemarker.directive.page.PageParam;

import freemarker.template.Template;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class ArticleListDirectiveTest extends AbstractDirectiveTest {

    private static final Log log = LogFactory.getLog(ArticleListDirectiveTest.class);

    @Override
    protected void setDirective(Configuration cfg) {
        cfg.setSharedVariable("article_title", new TitleDirective());
        cfg.setSharedVariable("article_url", new UrlDirective());
        cfg.setSharedVariable("article_date", new PubDateDirective());
      
    }

    private void setArticleListDirective(final int id,final int page,final int row){
      //TODO mockito instend jmock
//        Mockery  context = new Mockery();
//        ArticleListDirective articleListDirective = new ArticleListDirective();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        articleListDirective.setGeneratorDAO(dao);
//        context.checking(new Expectations() {{
//                oneOf(dao).findArticlePage(with(aNonNull(Integer.class)), with(any(Integer.class)), with(equal(20)));will(returnValue(findArticlePage(id, page, row)));
//                oneOf(dao).getChannel(with(aNonNull(Integer.class)));will(returnValue(getChannel(id)));
//        }});
//        cfg.setSharedVariable("article_list", articleListDirective);
    }

    @Test
    public void testExecute() throws Exception {
        setArticleListDirective(1,0,20);
        Template template = cfg.getTemplate("www/article-list/article_list.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.length() > 300);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用19") != -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用20") == -1);
        Assert.assertTrue(value.indexOf("频道编号：1") != -1);
    }

    @Test
    public void testExecuteDefault() throws Exception {
        setArticleListDirective(1,0,20);
        Template template = cfg.getTemplate("www/article-list/article_list_default.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);

        Assert.assertTrue(value.length() > 100);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用4") != -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用20") == -1);
        Assert.assertTrue(value.indexOf("频道编号：1") != -1);

    }

    @Test
    public void testExecutePage() throws Exception {
        setArticleListDirective(1,1,20);
        Template template = cfg.getTemplate("www/article-list/article_list_page.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        PageParam pageParam = new PageParam();
        pageParam.setPage(1);
        pageParam.setRow(20);
        params.put(DirectiveVariable.PageParam.toString(), pageParam);
        String value = this.process(template, params);
        log.info(value);
        Assert.assertTrue(value.length() > 100);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用19") == -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用20") != -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用39") != -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用40") == -1);
        Assert.assertTrue(value.indexOf("频道编号：1") != -1);
    }

      private void setArticleListDirective(final String url,final int page,final int row){
        //TODO mockito instend jmock
//        Mockery  context = new Mockery();
//        ArticleListDirective articleListDirective = new ArticleListDirective();
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//        articleListDirective.setGeneratorDAO(dao);
//        context.checking(new Expectations() {{
//            int channelId = 1;
//            oneOf(dao).findArticlePage(with(aNonNull(Integer.class)), with(any(Integer.class)), with(equal(20)));will(returnValue(findArticlePage(channelId, page, row)));
//            oneOf(dao).getChannelByUrlOrDir(with(any(Integer.class)),with(aNonNull(String.class)));will(returnValue(getChannel(channelId)));
//            oneOf(dao).getChannel(with(aNonNull(Integer.class)));will(returnValue(getChannel(channelId)));
//        }});
//        cfg.setSharedVariable("article_list", articleListDirective);
    }

    @Test
    public void testExecuteLoop() throws Exception {
        setArticleListDirective("/db/test/",0,20);
        Template template = cfg.getTemplate("www/article-list/article_list_loop.html");

        Map params = new HashMap();
        params.put(DirectiveVariable.CurrentChannel.toString(), getCurrentChannel());
        params.put(DirectiveVariable.CurrentSite.toString(), getCurrentChannel().getSite());
        String value = this.process(template, params);
        log.info(value);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用19") != -1);
        Assert.assertTrue(value.indexOf("ewcms文章标签使用20") == -1);
    }
    
    private Channel getCurrentChannel() {
        Channel channel = new Channel();
        channel.setId(10);
        Site site = new Site();
        site.setId(1);
        channel.setSite(site);
        channel.setPublicenable(true);

        return channel;
    }

    public List<Article> findArticlePage(int id, int page, int row) {
        Channel channel = getChannel(id);
        List<Article> list = new ArrayList<Article>();
        int start = page * row;
        for (int i = 0; i < row; ++i) {
            Article article = createArticle(start + i);
//            article.setChannel(channel);
            list.add(article);
        }
        return list;
    }

    private Channel getChannel(int id) {
        Channel channel = new Channel();

        channel.setId(id);
        channel.setName("test" + String.valueOf(id));
        channel.setDir(channel.getName());
        channel.setUrl(channel.getName());
        channel.setPublicenable(true);
        channel.setListSize(20);
        Site site = new Site();
        site.setId(1);
        channel.setSite(site);
        
        return channel;
    }

    private Article createArticle(int id) {
        Article article = new Article();

        article.setId(id);
        article.setAuthor("王伟");
        article.setOrigin("163.com");
        article.setTitle("ewcms文章标签使用" + String.valueOf(id));
        article.setShortTitle("文章标签使用");
        Calendar calendar = Calendar.getInstance();
//        article.setPublished(new Date(calendar.getTimeInMillis()));
        article.setSummary("介绍ewcms文章中的标签使用方法。");
        article.setImage("http://www.jict.org/image/test.jpg");

        return article;
    }
}
