/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ewcms.content.document.dao.ArticleDAO;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;

/**
 * 
 * @author 吴智俊
 *
 */
public class ArticleServiceTest {
	
	private ArticleService articleService;
	private ArticleDAO articleDAO;
	private ArticleMainDAO articleMainDAO;
	
	@Before
	public void setUp() {
		articleService = new ArticleService();
		articleDAO = mock(ArticleDAO.class);
		articleMainDAO = mock(ArticleMainDAO.class);
		articleService.setArticleDAO(articleDAO);
		articleService.setArticleMainDAO(articleMainDAO);
	}
	
	@Test
	public void addArticle(){
		Integer channelId = 1;
		Article article = new Article();
		article.setTitle("addTest");
		article.setType(ArticleType.GENERAL);
		
		articleService.addArticle(article, channelId, null);
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).persist(argument.capture());
		assertEquals(argument.getValue().getArticle().getTitle(), article.getTitle());
	}
	
	@Test
	public void updArticle(){
		Integer channelId = 1;
		Article article = new Article();
		article.setTitle("updTest");
		article.setType(ArticleType.GENERAL);
		
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleService.updArticle(article, articleMain.getId(), channelId, null);
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertEquals(argument.getValue().getArticle().getTitle(), "updTest");
	}
	
	private ArticleMain initArticleMain(){
		ArticleMain articleMain = new ArticleMain();
		articleMain.setId(1L);
		articleMain.setChannelId(1);
		
		Article article = new Article();
		article.setId(2L);
		article.setTitle("test");
		article.setType(ArticleType.GENERAL);
		article.setStatus(ArticleStatus.DRAFT);
		articleMain.setArticle(article);
		
		return articleMain;
	}
}
