/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.ArticleCategory;

/**
 * 文章分类属性操作接口
 * 
 * @author 吴智俊
 */
public interface ArticleCategoryServiceable {

	/**
	 * 新增文章分类属性
	 * 
	 * @param articleCategory 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Integer addArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 修改文章分类属性
	 * 
	 * @param articleCategory 文章分类属性对象
	 * @return Integer 文章分类属性编号
	 */
	public Integer updArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 删除文章分类属性
	 * 
	 * @param articleCategoryId 文章分类属性编号
	 */
	public void delArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询文章分类属性
	 * 
	 * @param articleCategoryId 文章分类属性编号
	 * @return ArticleCategory 文章分类属性对象
	 */
	public ArticleCategory findArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询所有文章分类属性集合
	 * 
	 * @return List 文章分类属性对象集合
	 */
	public List<ArticleCategory> findArticleCategoryAll();
}
