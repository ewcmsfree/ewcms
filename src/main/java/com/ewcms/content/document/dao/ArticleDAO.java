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
import com.ewcms.content.document.model.ArticleStatus;

/**
 * 文章信息DAO
 *
 * @author 吴智俊
 */
@Repository
public class ArticleDAO extends JpaDAO<Long, Article> {
	
    @SuppressWarnings("unchecked")
	public Boolean findArticleIsEntityByArticleAndCategory(Long articleId, Integer articleCategoryId){
    	String hql = "Select a From Article As a Left Join a.categories As c Where a.id=? And c.id=?";
    	List<Article> list = this.getJpaTemplate().find(hql, articleId, articleCategoryId);
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
				//TODO page、row、top三个参数还未传入hql查询语句中
				String hql = "Select m.article From ArticleMain As m Where m.channelId=? And m.article.status=? Order By m.article.topFlag Desc, m.sort Asc, Case When m.article.published Is Null Then 1 Else 0 End, m.article.published Desc, Case When m.article.modified Is Null Then 1 Else 0 End, m.article.modified Desc, m.id";
				return em.createQuery(hql).setParameter(1, channelId).setParameter(2, ArticleStatus.RELEASE).getResultList();
			}
    	});
    	return (List<Article>)result;
    }
    
    @SuppressWarnings("unchecked")
	public List<Article> findArticleRelease(final Integer channelId){
    	Object result = this.getJpaTemplate().execute(new JpaCallback<Object>(){
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				if (channelId == null){
					return new ArrayList<Article>();
				}
				//TODO 未修改完
				String hql = "Select m.article From ArticleMain As m Where m.channelId=? And m.article.status=?";
				return em.createQuery(hql).setParameter(1, channelId).setParameter(2, ArticleStatus.RELEASE).getResultList();
			}
    	});
    	return (List<Article>)result;
    }
}
