/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.Category;

/**
 * 文章分类属性操作接口
 * 
 * @author 吴智俊
 */
public interface CategoryServiceable {

	/**
	 * 新增文章分类属性
	 * 
	 * @param category 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Long addCategory(Category category);
	
	/**
	 * 修改文章分类属性
	 * 
	 * @param category 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Long updCategory(Category category);
	
	/**
	 * 删除文章分类属性
	 * 
	 * @param categoryId 文章分类属性编号
	 */
	public void delCategory(Long categoryId);
	
	/**
	 * 查询文章分类属性
	 * 
	 * @param categoryId 文章分类属性编号
	 * @return Category 文章分类属性对象
	 */
	public Category findCategory(Long categoryId);
	
	/**
	 * 查询所有文章分类属性集合
	 * 
	 * @return List 文章分类属性对象集合
	 */
	public List<Category> findCategoryAll();
}
