/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ArticleStatus;

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
	public ArticleMain findArticleMainByChannelAndEqualSort(Integer channelId, Long sort, Boolean top){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=? And c.sort=? And c.top=? Order By c.sort";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, sort, top);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainByChannelAndThanSort(Integer channelId, Long sort, Boolean top){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=? And c.sort>=? And c.top=? Order By c.sort";
		List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, sort, top);
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
	
    @SuppressWarnings("unchecked")
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer categoryId){
    	String hql = "Select a From Article As a Left Join a.categories As c Where a.id=? And c.id=?";
    	List<Article> list = this.getJpaTemplate().find(hql, articleId, categoryId);
    	if (list.isEmpty()) return false;
    	return true;
    }
    
    @SuppressWarnings("unchecked")
	public Integer findArticleReleseMaxSize(Integer channelId){
    	String hql = "Select Count(m.id) From ArticleMain As m Where m.channelId=? And m.article.status=?";
    	List<Integer> list = this.getJpaTemplate().find(hql, channelId, ArticleStatus.RELEASE);
    	if (list.isEmpty()) return 0;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Article> findArticlePreReleaseByChannelAndLimit(final Integer channelId, final Integer limit){
    	Object result = this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (channelId == null){
					return new ArrayList<Article>();
				}
				String hql = "Select m.article From ArticleMain As m Where m.channelId=? And m.article.status=? And m.reference=false";
				return em.createQuery(hql).setParameter(1, channelId).setParameter(2, ArticleStatus.PRERELEASE).setMaxResults(limit).getResultList();
			}
    	});
    	return (List<Article>)result;
    }
    
    @SuppressWarnings("unchecked")
	public List<Article> findArticleReleasePage(final Integer channelId, final Integer page, final Integer row, final Boolean top){
    	Object result = this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (channelId == null){
					return new ArrayList<Article>();
				}
				String hql = "Select m.article From ArticleMain As m Where m.channelId=? And m.article.status=? And m.top=? Order By Case When m.top Is Null Then 1 Else 0 End, m.top Desc, m.sort Asc, Case When m.article.published Is Null Then 1 Else 0 End, m.article.published Desc, Case When m.article.modified Is Null Then 1 Else 0 End, m.article.modified Desc, m.id";
				return em.createQuery(hql).setParameter(1, channelId).setParameter(2, ArticleStatus.RELEASE).setParameter(3, top).getResultList();
			}
    	});
    	return (List<Article>)result;
    }
    
    @SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainRelease(final Integer channelId){
    	Object result = this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (channelId == null){
					return new ArrayList<ArticleMain>();
				}
				String hql = "From ArticleMain As m Where m.channelId=? And m.article.status=?";
				return em.createQuery(hql).setParameter(1, channelId).setParameter(2, ArticleStatus.RELEASE).getResultList();
			}
    	});
    	return (List<ArticleMain>)result;
    }
}
