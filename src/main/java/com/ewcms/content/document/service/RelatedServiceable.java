/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.Related;

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
	public void saveRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void deleteRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void upRelated(Long articleId, Long[] relatedArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId
	 * @param relatedArticleIds
	 */
	public void downRelated(Long articleId, Long[] relatedArticleIds);
	
	/**
	 * 查询相关文章
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Related> findRelatedByArticle(Long articleId);
}
