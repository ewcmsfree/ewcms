/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.release;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.generator.release.html.GeneratorHtmlable;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.freemarker.directive.page.PageParam;
import com.ewcms.web.util.GlobaPath;

import freemarker.template.Configuration;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;
/**
 *
 * @author wangwei
 */
public class ArticleReleaseTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final String ROOT = "/tmp";
    
    @Test
    public void testMkdir()throws ReleaseException{
        ArticleRelease release = new ArticleRelease();
        String dir = release.mkdir(ROOT,new Date());
        String day = format.format(new Date());
        String actule =  String.format("%s/%s/%s", ROOT,GlobaPath.DocumentDir.getPath(),day);
        Assert.assertEquals(dir, actule);
        dir  = release.mkdir(ROOT,new Date());
        Assert.assertEquals(dir, actule);
    }

    @Test
    public void testGetUrlPattern(){
        int id = 1;
        ArticleRelease release = new ArticleRelease();
        String urlPattern = release.getUrlPattern(id,new Date());
        String day = format.format(new Date());
        String actule = String.format("/%s/%s/%s", GlobaPath.DocumentDir.getPath(),day,"1_%d.html");
        Assert.assertEquals(urlPattern, actule);
    }

    @Test
    public void testGetFilePattern(){
        int id = 1;
        ArticleRelease release = new ArticleRelease();
        String filePattern = release.getFilePattern("/home/wangwei/2010-08-20", id);
        String actule = "/home/wangwei/2010-08-20/1_%d.html";
        Assert.assertEquals(filePattern, actule);
    }

    @Test
    public void testReleaseArticle()throws ReleaseException{
        //TODO mockito instend jmock
//        Mockery context = new Mockery();
//        final GeneratorHtmlable generator = context.mock(GeneratorHtmlable.class);
//        final GeneratorDAOable dao = context.mock(GeneratorDAOable.class);
//
//        ArticleRelease release = new ArticleRelease();
//        release.setGeneratorHtml(generator);
//        final String day = format.format(new Date());
//        final String dir =  String.format("/%s/%s/%s", ROOT,GlobaPath.DocumentDir.getPath(),day);
//        final Article article = getArticle();
//
//        context.checking(new Expectations(){{
//            exactly(3).of(generator).process(with(aNull(Configuration.class)), with(any(Writer.class)), with(aNonNull(Article.class)), with(any(PageParam.class)));
//            String url = String.format("/%s/%s/%d_0.html",GlobaPath.DocumentDir.getPath(), day,article.getId());
//            oneOf(dao).articlePublish(with(equal(article.getId())),with(equal(url)));
//        }});
////        release.releaseArticle(null, dao, article, dir,false);
    }

    public Article getArticle(){
        Article article = new Article();

        article.setId(new Long(20000));
        List<Content> contents = new ArrayList<Content>();
        article.setContents(contents);
        contents.add(new Content());
        contents.add(new Content());
        contents.add(new Content());

        return article;
    }
}
