/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.Date;

import com.ewcms.content.document.model.Article;

/**
 * @author 吴智俊
 */
public interface ArticleServiceable {
	/**
	 * 新增文章
	 * 
	 * @param articleMain
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Long addArticle(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章
	 * 
	 * @param article
	 * @param articleMainId
	 * @param channelId
	 * @param published
	 * @return
	 */
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published);

	/**
	 * 判断文章与文章属性是否有关联
	 * @param articleId
	 * @param articleCategoryId
	 * @return
	 */
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId);
	

}
