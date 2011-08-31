/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.Date;

import com.ewcms.content.document.model.Article;

/**
 * 文章信息操作接口
 * 
 * @author 吴智俊
 */
public interface ArticleServiceable {
	/**
	 * 新增文章信息
	 * 
	 * @param article 文章信息对象
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * 
	 * @return Long 文章主体编号
	 */
	public Long addArticle(Article article, Integer channelId, Date published);
	
	/**
	 * 修改文章信息
	 * 
	 * @param article 文章信息对象
	 * @param articleMainId 文章主体编号
	 * @param channelId 频道编号
	 * @param published 发布时间
	 * @param userName 用户名
	 * 
	 * @return Long 文章主体编号
	 */
	public Long updArticle(Article article, Long articleMainId, Integer channelId, Date published, String userName);

	/**
	 * 文章与文章分类属性是否有关联
	 * 
	 * @param articleId 文章信息编号
	 * @param articleCategoryId 文章分类属性编号
	 * 
	 * @return Boolean true:是,false:否
	 */
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId);
	
	/**
	 * 
	 * @param channelId
	 * @return
	 */
	public int getArticleCount(Integer channelId);
}
