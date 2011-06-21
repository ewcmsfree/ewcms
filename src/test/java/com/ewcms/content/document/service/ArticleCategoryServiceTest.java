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

import com.ewcms.content.document.dao.ArticleCategoryDAO;
import com.ewcms.content.document.model.ArticleCategory;

/**
 * 
 * @author wu_zhijun
 *
 */
public class ArticleCategoryServiceTest {

	ArticleCategoryService articleCategoryService;
	ArticleCategoryDAO articleCategoryDAO;

	@Before
	public void setUp() {
		articleCategoryService = new ArticleCategoryService();
		articleCategoryDAO = mock(ArticleCategoryDAO.class);
		articleCategoryService.setArticleCategoryDAO(articleCategoryDAO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addArticleCategoryIsNull() {
		articleCategoryService.addArticleCategory(null);
	}

	@Test
	public void addArticleCategory() {
		when(articleCategoryDAO.get(1)).thenReturn(null);

		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setId(1);
		articleCategory.setCategoryName("addTest");

		articleCategoryService.addArticleCategory(articleCategory);
		ArgumentCaptor<ArticleCategory> argument = ArgumentCaptor.forClass(ArticleCategory.class);
		verify(articleCategoryDAO).persist(argument.capture());
		assertEquals(argument.getValue().getId(), new Integer(1));
		assertEquals(argument.getValue().getCategoryName(), "addTest");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updArticleCategoryIsNull() {
		articleCategoryService.updArticleCategory(null);
	}

	@Test
	public void updArticleCategory() {
		ArticleCategory articleCategory = new ArticleCategory();
		articleCategory.setId(1);
		articleCategory.setCategoryName("addTest");

		articleCategoryService.updArticleCategory(articleCategory);
		ArgumentCaptor<ArticleCategory> argument = ArgumentCaptor.forClass(ArticleCategory.class);
		verify(articleCategoryDAO).merge(argument.capture());
		assertEquals(argument.getValue().getId(), new Integer(1));
		assertEquals(argument.getValue().getCategoryName(), "addTest");
	}
	
	@Test
	public void delArticleCategory(){
		Integer articleCategoryId = 1;
		articleCategoryService.delArticleCategory(articleCategoryId);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		verify(articleCategoryDAO).removeByPK(argument.capture());
	}
	
	@Test
	public void findArticleCategory(){
		ArticleCategory articleCategory = new ArticleCategory();
		
		articleCategory.setId(1);
		articleCategory.setCategoryName("findTest");
		
        when(articleCategoryDAO.get(1)).thenReturn(articleCategory);
        
        ArticleCategory vo = articleCategoryService.findArticleCategory(1);
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(articleCategoryDAO).get(argument.capture());
        assertEquals(articleCategory.getId(),vo.getId());
        assertEquals(articleCategory.getCategoryName(), vo.getCategoryName());
	}
	
	@Test
	public void findArticleCategoryAll(){
		List<ArticleCategory> articleCategorys = new ArrayList<ArticleCategory>();
		
		ArticleCategory vo = new ArticleCategory();
		vo.setId(1);
		vo.setCategoryName("findAllTest");
		articleCategorys.add(vo);
		
		ArticleCategory vo1 = new ArticleCategory();
		vo1.setId(2);
		vo1.setCategoryName("findAllTest1");
		articleCategorys.add(vo1);
		
		when(articleCategoryDAO.findAll()).thenReturn(articleCategorys);
		List<ArticleCategory> lists = articleCategoryService.findArticleCategoryAll();
		assertNotNull(lists);
		assertEquals(lists.size(), articleCategorys.size());
	}
}
