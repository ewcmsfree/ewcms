/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.service;

/**
 * 相关文章操作接口
 *
 * @author 吴智俊
 */
public interface RelatedServiceable {
	/**
	 * 保存相关文章
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void saveRelated(Integer articleId, Integer[] relatedArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void deleteRelated(Integer articleId, Integer[] relatedArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void upRelated(Integer articleId, Integer[] relatedArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void downRelated(Integer articleId, Integer[] relatedArticleIds);
}
