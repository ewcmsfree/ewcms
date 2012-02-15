/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.service.ArticlePublishServiceable;

/**
 * ArticlePublishServiceWapper单元测试
 * 
 * @author wangwei
 */
public class ArticlePublishServiceWrapperTest {

    @Test
    public void testGetArticleMockDatabase()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        long id = ArticlePublishServiceWrapper.PREVIEW_ARTICLE_ID;
        Article article = new Article();
        article.setId(Long.MAX_VALUE);
        when(mock.getArticle(id)).thenReturn(article);

        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
        Article mockArticle = service.getArticleMock();
        Assert.assertNotNull(mockArticle);
        Assert.assertEquals(Long.MAX_VALUE,mockArticle.getId().longValue());
    }
    
    @Test
    public void testGetArticleMockProperties()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        long id = ArticlePublishServiceWrapper.PREVIEW_ARTICLE_ID;
        when(mock.getArticle(id)).thenReturn(null);

        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
        Article mockArticle = service.getArticleMock();
        Assert.assertNotNull(mockArticle);
        Assert.assertEquals(id,mockArticle.getId().longValue());
        Assert.assertNotNull(mockArticle.getTitle());
    }
    
    @Test
    public void testGetArticleIsMock()throws PublishException{
        
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        long id = ArticlePublishServiceWrapper.PREVIEW_ARTICLE_ID;
        when(mock.getArticle(id)).thenReturn(null);

        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
        Article mockArticle = service.getArticle(null);
        Assert.assertNotNull(mockArticle);
    }
    
    @Test
    public void testGetArticle()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        long id = Long.MAX_VALUE;
        Article article = new Article();
        article.setId(id);
        when(mock.getArticle(id)).thenReturn(article);

        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,false);
        Article mockArticle = service.getArticle(id);
        Assert.assertNotNull(mockArticle);
        Assert.assertEquals(id, mockArticle.getId().longValue());
    }
    
    @Test
    public void testPublishArticle()throws PublishException{
        try{
            ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
            ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
            service.publishArticleSuccess(Long.MAX_VALUE, "");
            Assert.fail();
        }catch(RuntimeException e){
            
        }
    }
    
    @Test
    public void testFindPreReleaseArticles()throws PublishException{
        try{
            ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
            ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
            service.findPublishArticles(Integer.MAX_VALUE, Boolean.FALSE, 1000);
            Assert.fail();
        }catch(RuntimeException e){
            
        }
    }
    
    @Test
    public void testFindReleaseArticlePageIsMock()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        long id = ArticlePublishServiceWrapper.PREVIEW_ARTICLE_ID;
        when(mock.getArticle(id)).thenReturn(null);

        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
        List<Article>articles = service.findArticleReleasePage(Integer.MAX_VALUE, 1, 20, false);
        Assert.assertNotNull(articles);
        Assert.assertEquals(20, articles.size());
        for(Article a : articles){
            Assert.assertNotNull(a.getId());
            Assert.assertTrue(a.getId().longValue() !=id );
            Assert.assertNotNull(a.getTitle());
        }
    }
    
    @Test
    public void testFindReleaseArticlePage()throws PublishException{
        
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        List<Article> list = new ArrayList<Article>();
        for(int i = 0 ; i < 10 ; i++){
            Article a = new Article();
            a.setId(new Long(i));
            list.add(a);
        }
        int channelId = Integer.MAX_VALUE;
        int page = 1;
        int row = 20;
        boolean top = false;
        when(mock.findArticleReleasePage(channelId, page, row, top)).thenReturn(list);
        
        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,false);
        List<Article>articles = service.findArticleReleasePage(channelId, page, row, top);
        Assert.assertNotNull(articles);
        Assert.assertEquals(10, articles.size());
        for(int i = 0 ; i < 10 ; i++){
            Article a = articles.get(i); 
            Assert.assertNotNull(a.getId());
            Assert.assertEquals(a.getId().intValue(), i);
        }
    }
    
    @Test
    public void testGetArticleCountIsMock()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,true);
        
        Assert.assertEquals(1000, service.getArticleReleaseCount(Integer.MAX_VALUE));
    }
    
    @Test
    public void testGetArticleCount()throws PublishException{
        ArticlePublishServiceable mock = mock(ArticlePublishServiceable.class);
        int channelId = Integer.MAX_VALUE;
        int count = 100;
        when(mock.getArticleReleaseCount(channelId)).thenReturn(count);
        ArticlePublishServiceWrapper service = new ArticlePublishServiceWrapper(mock,false);
        
        Assert.assertEquals(count, service.getArticleReleaseCount(channelId));
    }
}