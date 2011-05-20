/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.content.document.model.ArticleRmc;
import com.ewcms.content.document.model.Recommend;
import com.ewcms.content.document.model.Related;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class ArticleRmcDAO extends JpaDAO<Integer, ArticleRmc> {
    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> findContentHistoryToPage(Integer articleId){
    	String hql = "SELECT o.version, max(o.page) AS maxPage,o.historyTime FROM Article AS a RIGHT JOIN a.contentHistories AS o WHERE a.id=? GROUP BY o.version,o.historyTime ORDER BY o.version DESC";
    	List<Object[]> list = this.getJpaTemplate().find(hql,articleId);
    	List<Map<String,Object>> listValues = new ArrayList<Map<String,Object>>();
    	for(Object[] values : list){
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("version", values[0]);
    		map.put("maxPage",values[1]);
    		map.put("historyTime", values[2]);
    		listValues.add(map);
    	}
    	return listValues;
    }

    @SuppressWarnings("unchecked")
	public List<ArticleRmc> findArticleRmcByChannelId(Integer channelId){
    	String hql = "SELECT r FROM ArticleRmc AS r RIGHT JOIN r.article AS a RIGHT JOIN a.channel AS c WHERE a.channel.id=? ORDER BY a.id Desc";
    	List<ArticleRmc> list = this.getJpaTemplate().find(hql,channelId);
    	if (list.isEmpty()) return new ArrayList<ArticleRmc>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public List<Related> findRelatedByArticleRmcId(Integer articleRmcId){
    	String hql = "SELECT r FROM ArticleRmc AS o RIGHT JOIN o.relateds AS r WHERE o.id=? ORDER BY r.sort";
    	List<Related> list = this.getJpaTemplate().find(hql,articleRmcId);
    	if (list.isEmpty()) return new ArrayList<Related>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Related findRelatedByArticleRmcIdAndSort(Integer articleRmcId, Integer sort){
    	String hql = "SELECT r FROM ArticleRmc AS o RIGHT JOIN o.relateds AS r WHERE o.id=?1 AND r.sort=?2";
    	List<Related> list = this.getJpaTemplate().find(hql,articleRmcId,sort);
    	if (list.isEmpty()) return new Related();
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Recommend> findRecommendByArticleRmcId(Integer articleRmcId){
    	String hql = "SELECT r FROM ArticleRmc AS o RIGHT JOIN o.recommends AS r WHERE o.id=? ORDER BY r.sort";
    	List<Recommend> list = this.getJpaTemplate().find(hql,articleRmcId);
    	if (list.isEmpty()) return new ArrayList<Recommend>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Recommend findRecommendByArticleRmcIdAndSort(Integer articleRmcId, Integer sort){
    	String hql = "SELECT r FROM ArticleRmc AS o RIGHT JOIN o.recommends AS r WHERE o.id=? AND r.sort=?";
    	List<Recommend> list = this.getJpaTemplate().find(hql,articleRmcId,sort);
    	if (list.isEmpty()) return new Recommend();
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
    public List<ArticleRmc> findSiteArticleRmc(Integer siteId,Integer refId){
    	String hql = "FROM ArticleRmc AS o  WHERE o.refArticleRmc.id=? and o.channel.site.id=?";
    	List<ArticleRmc> list = this.getJpaTemplate().find(hql,refId,siteId);
    	if (list.isEmpty()) return new ArrayList<ArticleRmc>();
    	return list;    	
    }
}
