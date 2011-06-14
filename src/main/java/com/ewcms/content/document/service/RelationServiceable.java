/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.Relation;

/**
 * 相关文章操作接口
 *
 * @author 吴智俊
 */
public interface RelationServiceable {
	/**
	 * 保存相关文章
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void saveRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 删除相关文章
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void deleteRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 相关文章向上移动一位
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void upRelation(Long articleId, Long[] relationArticleIds);

	/**
	 * 相关文章向下移动一位
	 * 
	 * @param articleId 文章信息编号
	 * @param relationArticleIds 相关文章编号集合
	 */
	public void downRelation(Long articleId, Long[] relationArticleIds);
	
	/**
	 * 查询相关文章集合
	 * 
	 * @param articleId 文章信息编号
	 * @return List 相关文章集合
	 */
	public List<Relation> findRelationByArticle(Long articleId);
}
