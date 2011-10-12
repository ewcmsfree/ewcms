/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.ArticleMain;

/**
 * 文章主体DAO
 * 
 * @author 吴智俊
 */
@Repository
public class ArticleMainDAO extends JpaDAO<Long, ArticleMain> {
	
	@SuppressWarnings("unchecked")
	public ArticleMain findArticleMainByArticleMainAndChannel(Long articleMainId, Integer channelId){
    	String hql = "FROM ArticleMain AS r WHERE r.id=? And r.channelId=?";
    	List<ArticleMain> list = this.getJpaTemplate().find(hql, articleMainId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainByChannel(Integer channelId){
		String hql = "Select r FROM ArticleMain AS r WHERE r.channelId=? Order By r.sort";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty()) return new ArrayList<ArticleMain>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public ArticleMain findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean isTop){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=? And c.sort=? And o.topFlag=? Order By c.sort";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, sort, isTop);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainByChannelAndThanSort(Integer channelId, Long sort, Boolean isTop){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=? And c.sort>=? And o.topFlag=? Order By c.sort";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, sort, isTop);
		if (list.isEmpty()) return new ArrayList<ArticleMain>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainByChannelIdAndUserName(Integer channelId, String userName){
		String hql = "Select m From ArticleMain As m Left Join m.article As a Where m.channelId=? And a.owner=?";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, userName);
		if (list.isEmpty()) return new ArrayList<ArticleMain>();
		return list;
	}
}
