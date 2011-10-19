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

import com.ewcms.content.document.dao.CategoryDAO;
import com.ewcms.content.document.model.Category;

/**
 * 
 * @author 吴智俊
 *
 */
public class CategoryServiceTest {

	CategoryService categoryService;
	CategoryDAO categoryDAO;

	@Before
	public void setUp() {
		categoryService = new CategoryService();
		categoryDAO = mock(CategoryDAO.class);
		categoryService.setCategoryDAO(categoryDAO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addCategoryIsNull() {
		categoryService.addCategory(null);
	}

	@Test
	public void addCategory() {
		when(categoryDAO.get(1)).thenReturn(null);

		Category category = new Category();
		category.setId(1);
		category.setCategoryName("addTest");

		categoryService.addCategory(category);
		ArgumentCaptor<Category> argument = ArgumentCaptor.forClass(Category.class);
		verify(categoryDAO).persist(argument.capture());
		assertEquals(argument.getValue().getId(), new Integer(1));
		assertEquals(argument.getValue().getCategoryName(), "addTest");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updCategoryIsNull() {
		categoryService.updCategory(null);
	}

	@Test
	public void updCategory() {
		Category category = new Category();
		category.setId(1);
		category.setCategoryName("addTest");

		categoryService.updCategory(category);
		ArgumentCaptor<Category> argument = ArgumentCaptor.forClass(Category.class);
		verify(categoryDAO).merge(argument.capture());
		assertEquals(argument.getValue().getId(), new Integer(1));
		assertEquals(argument.getValue().getCategoryName(), "addTest");
	}
	
	@Test
	public void delCategory(){
		Integer categoryId = 1;
		categoryService.delCategory(categoryId);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		verify(categoryDAO).removeByPK(argument.capture());
	}
	
	@Test
	public void findCategory(){
		Category articleCategory = new Category();
		
		articleCategory.setId(1);
		articleCategory.setCategoryName("findTest");
		
        when(categoryDAO.get(1)).thenReturn(articleCategory);
        
        Category vo = categoryService.findCategory(1);
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(categoryDAO).get(argument.capture());
        assertEquals(articleCategory.getId(),vo.getId());
        assertEquals(articleCategory.getCategoryName(), vo.getCategoryName());
	}
	
	@Test
	public void findCategoryAll(){
		List<Category> categories = new ArrayList<Category>();
		
		Category vo = new Category();
		vo.setId(1);
		vo.setCategoryName("findAllTest");
		categories.add(vo);
		
		Category vo1 = new Category();
		vo1.setId(2);
		vo1.setCategoryName("findAllTest1");
		categories.add(vo1);
		
		when(categoryDAO.findAll()).thenReturn(categories);
		List<Category> lists = categoryService.findCategoryAll();
		assertNotNull(lists);
		assertEquals(lists.size(), categories.size());
	}
}
