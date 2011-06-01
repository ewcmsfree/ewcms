/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.ArticleCategory;

/**
 * @author 吴智俊
 */
public interface ArticleCategoryServiceable {

	/**
	 * 新增文章分类属性
	 * 
	 * @param articleCategory
	 * @return Integer
	 */
	public Integer addArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 修改文章分类属性
	 * 
	 * @param articleCategory
	 * @return Integer
	 */
	public Integer updArticleCategory(ArticleCategory articleCategory);
	
	/**
	 * 删除文章分类属性
	 * 
	 * @param articleCategoryId
	 */
	public void delArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询文章分类属性
	 * 
	 * @param articleCategoryId
	 * @return
	 */
	public ArticleCategory findArticleCategory(Integer articleCategoryId);
	
	/**
	 * 查询�?��文章分类属性
	 * 
	 * @return
	 */
	public List<ArticleCategory> findArticleCategoryAll();
}
