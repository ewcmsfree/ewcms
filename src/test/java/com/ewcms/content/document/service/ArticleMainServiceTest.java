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
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ewcms.content.document.BaseException;
import com.ewcms.content.document.dao.ArticleMainDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;
import com.ewcms.content.document.model.ArticleType;
import com.ewcms.security.manage.service.UserServiceable;

/**
 * 
 * @author 吴智俊
 *
 */
public class ArticleMainServiceTest {
	
	private ArticleMainService articleMainService;
	private ArticleMainDAO articleMainDAO;
	private UserServiceable userService;
	
	@Before
	public void setUp() {
		articleMainService = new ArticleMainService();
		articleMainDAO = mock(ArticleMainDAO.class);
		userService = mock(UserServiceable.class);
		articleMainService.setArticleMainDAO(articleMainDAO);
	}
	
	@Test
	public void addArticle(){
		Integer channelId = 1;
		Article article = new Article();
		article.setTitle("addTest");
		article.setType(ArticleType.GENERAL);
		
		articleMainService.addArticleMain(article, channelId, null);
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
		//articleService.updArticle(article, articleMain.getId(), channelId, null);
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertEquals(argument.getValue().getArticle().getTitle(), "updTest");
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
		when(userService.getUserRealName()).thenReturn("吴智俊");
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertTrue(argument.getValue().getArticle().getDelete());
	}
	
	@Test
	public void delArticleMainToRecycleBinIsReference(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setReference(true);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).remove(argument.capture());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delArticleMainToRecycleBinIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delArticleMainToRecycleBinIsArticleNull(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.delArticleMainToRecycleBin(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test
	public void restoreArticleMain(){
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setDelete(true);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertFalse(argument.getValue().getArticle().getDelete());
		assertEquals(argument.getValue().getArticle().getStatus(), ArticleStatus.REEDIT);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void restoreArticleMainIsNull(){
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void restoreArticleMainIsArticleNull(){
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.restoreArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test
	public void submitReviewArticleMainIsReturnTrue() throws BaseException{
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setStatus(ArticleStatus.DRAFT);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertNotNull(argument.getValue().getArticle().getPublished());
		assertEquals(argument.getValue().getArticle().getStatus(), ArticleStatus.REVIEW);
	}
	
	@Test
	public void submitReviewArticleMainIsReturnFalse() throws BaseException{
		ArticleMain articleMain = initArticleMain();
		articleMain.getArticle().setStatus(ArticleStatus.RELEASE);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainIsNull() throws BaseException{
		ArticleMain articleMain = initArticleMain();
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(null);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainIsArticleNull() throws BaseException{
		ArticleMain articleMain = initArticleMain();
		articleMain.setArticle(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.submitReviewArticleMain(articleMain.getId(), articleMain.getChannelId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void submitReviewArticleMainsIsNull1() throws BaseException{
		articleMainService.submitReviewArticleMain(null, 1);
	}
	
	@Test
	public void reviewArticleMainPass(){
		ArticleMain articleMain_1 = initArticleMain();
		articleMain_1.getArticle().setStatus(ArticleStatus.REVIEW);
		
//		ArticleMain articleMain_2 = new ArticleMain();
//		articleMain_2.setId(2L);
//		articleMain_2.setChannelId(1);
//		Article article_2 = new Article();
//		article_2.setId(3L);
//		article_2.setStatus(ArticleStatus.REVIEW);
//		article_2.setTitle("test3");
//		articleMain_2.setArticle(article_2);
//		
//		ArticleMain articleMain_3 = new ArticleMain();
//		articleMain_3.setId(3L);
//		articleMain_3.setChannelId(1);
//		Article article_3 = new Article();
//		article_3.setId(4L);
//		article_3.setStatus(ArticleStatus.REVIEW);
//		article_3.setTitle("test4");
//		articleMain_3.setArticle(article_3);
//		
//		List<Long> articleMainIds = new ArrayList<Long>();
//		articleMainIds.add(1L);
//		articleMainIds.add(2L);
//		articleMainIds.add(3L);
		
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(1L, 1)).thenReturn(articleMain_1);
//		when(articleMainDAO.findArticleMainByArticleMainAndChannel(2L, 1)).thenReturn(articleMain_2);
//		when(articleMainDAO.findArticleMainByArticleMainAndChannel(3L, 1)).thenReturn(articleMain_3);
//		when(userService.getUserRealName()).thenReturn("wuzhijun");
		
		articleMainService.reviewArticleMain(articleMain_1.getId(), 1, 0, "");
		
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO, times(3)).merge(argument.capture());
		assertEquals(argument.getValue().getArticle().getStatus().getDescription(), ArticleStatus.PRERELEASE.getDescription());
		//assertEquals(argument.getValue().getArticle().getAuditReal(), "wuzhijun");
	}
	
	@Test
	public void reviewArticleMainNotPass(){
		ArticleMain articleMain_1 = initArticleMain();
		articleMain_1.getArticle().setStatus(ArticleStatus.REVIEW);
		
//		ArticleMain articleMain_2 = new ArticleMain();
//		articleMain_2.setId(2L);
//		articleMain_2.setChannelId(1);
//		Article article_2 = new Article();
//		article_2.setId(3L);
//		article_2.setStatus(ArticleStatus.PRERELEASE);
//		article_2.setTitle("test3");
//		articleMain_2.setArticle(article_2);
//		
//		ArticleMain articleMain_3 = new ArticleMain();
//		articleMain_3.setId(3L);
//		articleMain_3.setChannelId(1);
//		Article article_3 = new Article();
//		article_3.setId(4L);
//		article_3.setStatus(ArticleStatus.RELEASE);
//		article_3.setTitle("test4");
//		articleMain_3.setArticle(article_3);
//		
//		List<Long> articleMainIds = new ArrayList<Long>();
//		articleMainIds.add(1L);
//		articleMainIds.add(2L);
//		articleMainIds.add(3L);
		
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(1L, 1)).thenReturn(articleMain_1);
//		when(articleMainDAO.findArticleMainByArticleMainAndChannel(2L, 1)).thenReturn(articleMain_2);
//		when(articleMainDAO.findArticleMainByArticleMainAndChannel(3L, 1)).thenReturn(articleMain_3);
//		when(userService.getUserRealName()).thenReturn("wuzhijun");
		
		articleMainService.reviewArticleMain(articleMain_1.getId(), 1, 1, "");
		
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO, times(3)).merge(argument.capture());
		assertEquals(argument.getValue().getArticle().getStatus().getDescription(), ArticleStatus.REEDIT.getDescription());
		//assertEquals(argument.getValue().getArticle().getAuditReal(), "wuzhijun");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void reviewArticleMainIsNull(){
		articleMainService.reviewArticleMain(null, 1, 0, "");
	}
	
	@Test
	public void reviewArticleMainHasNull(){
		ArticleMain articleMain_1 = initArticleMain();
		articleMain_1.getArticle().setStatus(ArticleStatus.REVIEW);
		
		articleMainService.reviewArticleMain(articleMain_1.getId(), 1, 0, "");
		
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO, times(1)).merge(argument.capture());
		assertEquals(argument.getValue().getArticle().getStatus().getDescription(), ArticleStatus.PRERELEASE.getDescription());
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
	
	@Test
	public void moveArticleMainSortIsNull(){
		ArticleMain articleMain = initArticleMain();
		Long sort = 1L;
		when(articleMainDAO.findArticleMainByChannelAndEqualSort(articleMain.getChannelId(), sort, true)).thenReturn(null);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(articleMain.getId(), articleMain.getChannelId())).thenReturn(articleMain);
		articleMainService.moveArticleMainSort(articleMain.getId(), articleMain.getChannelId(), sort, null, null);
		
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO).merge(argument.capture());
		assertEquals(argument.getValue().getSort(), sort);
	}
	
	@Test
	public void clearArticleMainSort(){
		ArticleMain articleMain_1 = initArticleMain();
		articleMain_1.getArticle().setStatus(ArticleStatus.REVIEW);
		
		ArticleMain articleMain_2 = new ArticleMain();
		articleMain_2.setId(2L);
		articleMain_2.setChannelId(1);
		articleMain_2.setSort(1L);
		Article article_2 = new Article();
		article_2.setId(3L);
		article_2.setStatus(ArticleStatus.PRERELEASE);
		article_2.setTitle("test3");
		articleMain_2.setArticle(article_2);
		
		ArticleMain articleMain_3 = new ArticleMain();
		articleMain_3.setId(3L);
		articleMain_3.setChannelId(1);
		articleMain_3.setSort(2L);
		Article article_3 = new Article();
		article_3.setId(4L);
		article_3.setStatus(ArticleStatus.RELEASE);
		article_3.setTitle("test4");
		articleMain_3.setArticle(article_3);
		
		List<Long> articleMainIds = new ArrayList<Long>();
		articleMainIds.add(1L);
		articleMainIds.add(2L);
		articleMainIds.add(3L);
		
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(1L, 1)).thenReturn(articleMain_1);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(2L, 1)).thenReturn(articleMain_2);
		when(articleMainDAO.findArticleMainByArticleMainAndChannel(3L, 1)).thenReturn(articleMain_3);
		
		articleMainService.clearArticleMainSort(articleMainIds, 1);
		ArgumentCaptor<ArticleMain> argument = ArgumentCaptor.forClass(ArticleMain.class);
		verify(articleMainDAO, times(2)).merge(argument.capture());
		assertEquals(argument.getAllValues().size(), 2);
		assertNull(argument.getValue().getSort());
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
