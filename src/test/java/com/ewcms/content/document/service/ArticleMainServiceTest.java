/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.generator.GeneratorServiceable;
import com.ewcms.security.manage.service.UserServiceable;

/**
 * 
 * @author wu_zhijun
 *
 */
public class ArticleMainServiceTest {
	
	private ArticleMainService articleMainService;
	private GeneratorServiceable generatorService;
	private ArticleMainDAO articleMainDAO;
	private UserServiceable userService;
	
	@Before
	public void setUp() {
		articleMainService = new ArticleMainService();
		generatorService = mock(GeneratorServiceable.class);
		articleMainDAO = mock(ArticleMainDAO.class);
		userService = mock(UserServiceable.class);
		articleMainService.setGeneratorService(generatorService);
		articleMainService.setArticleMainDAO(articleMainDAO);
		articleMainService.setUserService(userService);
	}
	
	@Test
	public void findArticleMainByArticleMainAndChannel(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		ArticleMain vo = articleMainService.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId());
		assertNotNull(vo);
	}
	
	@Test
	public void findArticleMainByArticleMainAndChannelIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		ArticleMain vo = articleMainService.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId());
		assertNull(vo);
	}
	
	@Test
	public void delArticleMain(){
		ArticleMain articleMain = initArticleMain();
		
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId() ,articleMain.getChannelId())).thenReturn(articleMain);
		
		articleMainService.delArticleMain(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).remove(argument.capture());
	}
	
	@Test
	public void delArticleMainIsReference(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setReference(true);
		
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		
		articleMainService.delArticleMain(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).remove(argument.capture());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delArticleMainIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.delArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test
	public void delArticleMainToRecycleBin(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertTrue(argument.getValue().getArticle().getDeleteFlag());
	}
	
	@Test
	public void delArticleMainToRecycleBinIsReference(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setReference(true);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).remove(argument.capture());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delArticleMainToRecycleBinIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delArticleMainToRecycleBinIsArticleNull(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
	}
	
	@Test
	public void restoreArticleMain(){
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setDeleteFlag(true);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertFalse(argument.getValue().getArticle().getDeleteFlag());
		assertEquals(argument.getValue().getArticle().getStatus(), ArticleStatus.REEDIT);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void restoreArticleMainIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void restoreArticleMainIsArticleNull(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId(), "wuzhijun");
	}
	
	@Test
	public void submitReviewArticleMainIsReturnTrue(){
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setStatus(ArticleStatus.DRAFT);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		Boolean flag = articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertTrue(flag);
		assertNotNull(argument.getValue().getArticle().getPublished());
		assertEquals(argument.getValue().getArticle().getStatus(), ArticleStatus.REVIEW);
	}
	
	@Test
	public void submitReviewArticleMainIsReturnFalse(){
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setStatus(ArticleStatus.RELEASE);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		Boolean flag = articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
		assertFalse(flag);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainIsArticleNull(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainsIsNull(){
		articleMainService.submitReviewArticleMains(null, 1);
	}
	
	@Test
	public void copyArticleMainToChannel(){
		fail("not test code");
	}
	
	@Test
	public void findArticleMainByChannel(){
		List<ArticleMain> articleMains = new ArrayList<ArticleMain>();
		ArticleMain articleMain = initArticleMain();
		articleMains.add(articleMain);
		
		ArticleMain articleMain_1 = new ArticleMain();
		articleMain_1.setId(2L);
		articleMain_1.setChannelId(1);
		Article article = new Article();
		article.setId(3L);
		article.setTitle("test2");
		articleMain_1.setArticle(article);
		articleMains.add(articleMain_1);
		
		when(articleMainDAO.findArticleMainByChannel(1)).thenReturn(articleMains);
		List<ArticleMain> lists = articleMainService.findArticleMainByChannel(1);
		assertEquals(lists.size(), 2);
	}
	
	private ArticleMain initArticleMain(){
		ArticleMain articleMain = new ArticleMain();
		articleMain.setId(1L);
		articleMain.setChannelId(1);
		
		Article article = new Article();
		article.setId(2L);
		article.setTitle("test");
		articleMain.setArticle(article);
		
		return articleMain;
	}
}
