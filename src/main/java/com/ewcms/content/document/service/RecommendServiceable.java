/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import com.ewcms.content.document.model.Recommend;

/**
 * 推荐文章操作接口
 *
 * @author 吴智俊
 */
public interface RecommendServiceable {
	/**
	 * 保存推荐文章
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void saveRecommend(Long articleId, Long[] recommendArticleIds);
	
	/**
	 * 删除推荐文章
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void deleteRecommend(Long articleId, Long[] recommendArticleIds);
	
	/**
	 * 推荐文章向上移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void upRecommend(Long articleId, Long[] recommendArticleIds);

	/**
	 * 推荐文章向下移动一位
	 * 
	 * @param articleId
	 * @param recommendArticleIds
	 */
	public void downRecommend(Long articleId, Long[] recommendArticleIds);
	
	/**
	 * 查询推荐文章
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Recommend> findRecommendByArticle(Long articleId);
}
