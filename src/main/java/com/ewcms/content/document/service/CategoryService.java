/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.dao.CategoryDAO;
import com.ewcms.content.document.model.Category;

/**
 * @author 吴智俊
 */
@Service
public class CategoryService implements CategoryServiceable {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	public void setCategoryDAO(CategoryDAO categoryDAO){
		this.categoryDAO = categoryDAO;
	}
	
	@Override
	public Integer addCategory(Category category) {
		Assert.notNull(category);
		categoryDAO.persist(category);
		return category.getId();
	}

	@Override
	public Integer updCategory(Category category) {
		Assert.notNull(category);
		categoryDAO.merge(category);
		return category.getId();
	}

	@Override
	public void delCategory(Integer categoryId) {
		categoryDAO.removeByPK(categoryId);
	}

	@Override
	public Category findCategory(Integer categoryId) {
		return categoryDAO.get(categoryId);
	}

	@Override
	public List<Category> findCategoryAll() {
		return categoryDAO.findAll();
	}

}
