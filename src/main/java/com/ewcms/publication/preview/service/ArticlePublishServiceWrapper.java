/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

import com.ewcms.common.message.EwcmsMessageSource;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Article.Status;
import com.ewcms.content.document.model.Article.Type;
import com.ewcms.content.document.model.Category;
import com.ewcms.content.document.model.Content;
import com.ewcms.publication.service.ArticlePublishServiceable;

/**
 * 为预览提供文章数据。
 * <p>
 * 为了预览模版和文章的样式，提供所需要的文章数据。
 * 
 * @author wangwei
 */
public class ArticlePublishServiceWrapper implements ArticlePublishServiceable,MessageSourceAware{

    public static final long PREVIEW_ARTICLE_ID = -10000L; 
    private static final Random random = new Random();
    
    private MessageSourceAccessor messages = EwcmsMessageSource.getAccessor();
    private final ArticlePublishServiceable service;
    private final boolean mock;
    
    public ArticlePublishServiceWrapper(ArticlePublishServiceable articlePublishService,boolean mock){
        Assert.notNull(articlePublishService,"articlePublishService must setting");
        this.service = articlePublishService;
        this.mock = mock;
    }
    
    Article getArticleMock(){
        Article article = service.getArticle(PREVIEW_ARTICLE_ID);
        article = (article == null ? newDefaultArticleMock() : article);
        return article;
    }
    
    @Override
    public Article getArticle(Long id) {
        if(mock){
            return getArticleMock();
        }
        return service.getArticle(id);
    }

    @Override
    public void publishArticleSuccess(Long id, String url) {
        throw new RuntimeException("it's not instance.");
    }

    @Override
    public List<Article> findPublishArticles(Integer channelId, Boolean forceAgain, Integer limit) {
        throw new RuntimeException("it's not instance.");
    }

	@Override
	public List<Article> findChildChannelArticleReleasePage(Integer channelId, Integer page, Integer row, Boolean top) {
		if(mock){
			Article article = getArticleMock();
	        return releaseArticlesMock(article,row);
	     }
	     return service.findArticleReleasePage(channelId, page, row, top);
	}
	
    private List<Article> releaseArticlesMock(Article article,int row){
        List<Article> articles = new ArrayList<Article>();
        for(int i = 0 ; i < row ; i++){
            articles.add(copyToNewArticle(article));
        }
        return articles;
    }
    
    @Override
    public List<Article> findArticleReleasePage(Integer channelId, Integer page, Integer row, Boolean top) {
        if(mock){
           Article article = getArticleMock();
           return releaseArticlesMock(article,row);
        }
        return service.findArticleReleasePage(channelId, page, row, top);
    }

    @Override
    public int getArticleReleaseCount(Integer channelId) {
        if(mock){
            return 1000;
        }
        return service.getArticleReleaseCount(channelId);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
    
    /**
     * 创建缺省的模拟文章
     * 
     * @return
     */
    private Article newDefaultArticleMock(){
        
        Date now = new Date();
        Article article = new Article();
        
        article.setId(PREVIEW_ARTICLE_ID);
        article.setAuthor(messages.getMessage("preview.artilce.author"));
        article.setKeyword(messages.getMessage("preview.artilce.keyword"));
        article.setOrigin(messages.getMessage("preview.artilce.origin"));
        article.setOwner(messages.getMessage("preview.artilce.owner"));
        article.setShortTitle(messages.getMessage("preview.artilce.shortTitle"));
        article.setSubTitle(messages.getMessage("preview.artilce.subTitle"));
        article.setSummary(messages.getMessage("preview.artilce.summary"));
        article.setTag(messages.getMessage("preview.artilce.tag"));
        article.setTitle(messages.getMessage("preview.artilce.title"));
        article.setContentTotal(7);
        article.setInside(false);
        article.setComment(true);
        article.setCreateTime(now);
        article.setDelete(false);
        article.setModified(now);
        article.setPublished(now);
        article.setStatus(Status.RELEASE);
        article.setType(Type.GENERAL);
        article.setUrl("#");
        
        List<Category> categories = new ArrayList<Category>();
        String categoryContent = messages.getMessage("preview.artilce.categories");
        for(String c : categoryContent.split(",")){
            Category category = new Category();
            category.setCategoryName(c);
            categories.add(category);
        }
        article.setCategories(categories);
        
        List<Content> contents = new ArrayList<Content>();
        Content content = new Content();
        content.setDetail("preview.artilce.content");
        content.setPage(1);
        article.setContents(contents);
        
       return article;
    }

    /**
     * 拷贝已有的文章到新文章对象中
     * </p>
     * 只用于文章列表显示，不拷贝文章内容。
     * 
     * @param article 文章对象
     * @return
     */
    private Article copyToNewArticle(Article article){
        
        Article newArticle = new Article();
        newArticle.setId(Math.abs(random.nextLong()));
        newArticle.setAuthor(article.getAuthor());
        newArticle.setComment(article.getComment());
        newArticle.setContentTotal(article.getContentTotal());
        newArticle.setCreateTime(article.getCreateTime());
        newArticle.setDelete(article.getDelete());
        newArticle.setInside(article.getInside());
        newArticle.setKeyword(article.getKeyword());
        newArticle.setModified(article.getModified());
        newArticle.setOrigin(article.getOrigin());
        newArticle.setOwner(article.getOwner());
        newArticle.setPublished(article.getPublished());
        newArticle.setShortTitle(article.getShortTitle());
        newArticle.setStatus(article.getStatus());
        newArticle.setSubTitle(article.getSubTitle());
        newArticle.setSummary(article.getSummary());
        newArticle.setTag(article.getTag());
        newArticle.setTitle(article.getTitle());
        newArticle.setType(article.getType());
        newArticle.setUrl(article.getUrl());
                
        return newArticle;
    }
}
