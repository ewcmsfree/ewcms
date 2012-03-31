/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.Article.Status;

/**
 * 文章主体DAO
 * 
 * @author 吴智俊
 */
@Repository
public class ArticleMainDAO extends JpaDAO<Long, ArticleMain> {
	
	public ArticleMain findArticleMainByArticleMainAndChannel(final Long articleMainId, final Integer channelId){
		String hql = "FROM ArticleMain AS r WHERE r.id=:articleMainId And r.channelId=:channelId";

		TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
		query.setParameter("articleMainId", articleMainId);
		query.setParameter("channelId", channelId);

		ArticleMain articleMain = null;
		try{
			articleMain = (ArticleMain)query.getSingleResult();
		}catch(NoResultException e){
		}
		return articleMain;
	}
	
	public List<ArticleMain> findArticleMainByChannel(final Integer channelId){
		String hql = "Select r FROM ArticleMain AS r WHERE r.channelId=:channelId Order By r.sort";

		TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
		query.setParameter("channelId", channelId);

		return query.getResultList();
	}
	
	public ArticleMain findArticleMainByChannelAndEqualSort(final Integer channelId, final Long sort, final Boolean top){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=:channelId And c.sort=:sort And c.top=:top Order By c.sort";

		TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
		query.setParameter("channelId", channelId);
		query.setParameter("sort", sort);
		query.setParameter("top", top);

		ArticleMain articleMain = null;
		try{
			articleMain = (ArticleMain)query.getSingleResult();
		}catch(NoResultException e){
		}
		
		return articleMain;
	}
	
	public List<ArticleMain> findArticleMainByChannelAndThanSort(final Integer channelId, final Long sort, final Boolean top){
		String hql = "Select c From ArticleMain c Left Join c.article o Where c.channelId=:channelId And c.sort>=:sort And c.top=:top Order By c.sort";

		TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
		query.setParameter("channelId", channelId);
		query.setParameter("sort", sort);
		query.setParameter("top", top);

		return query.getResultList();
	}
	
	public List<ArticleMain> findArticleMainByChannelIdAndUserName(final Integer channelId, final String userName){
		String hql = "Select m From ArticleMain As m Left Join m.article As a Where m.channelId=:channelId And a.owner=:owner";

		TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
		query.setParameter("channelId", channelId);
		query.setParameter("owner", userName);

		return query.getResultList();
	}
	
	public Boolean findArticleIsEntityByArticleAndCategory(final Long articleId, final Long categoryId){
    	String hql = "Select a From Article As a Left Join a.categories As c Where a.id=:articleId And c.id=:categoryId";

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("articleId", articleId);
    	query.setParameter("categoryId", categoryId);

    	List<Article> list = query.getResultList();
    	return list.isEmpty()? false : true;
    }
    
	public Map<Integer, Long> findBeApprovalArticleMain(final String userName, final List<String> groupNames){
    	Map<Integer, Long> map = new HashMap<Integer, Long>();
        String hql = "Select o.channelId, Count(o) From ArticleMain As o Left Join o.article AS r Left Join r.reviewProcess As p Left Join p.reviewUsers As u Left Join p.reviewGroups As g Where r.delete=false And r.status=:status And (u.userName=:userName ";
        if (groupNames != null && !groupNames.isEmpty()){
        	for (String groupName : groupNames)
        		hql += " Or g.groupName='" + groupName + "' ";
        }
        hql += ") Group By o.channelId "; 
        
        TypedQuery<Object[]> query = this.getEntityManager().createQuery(hql, Object[].class);
        query.setParameter("status", Status.REVIEW);
        query.setParameter("userName", userName);
        
        List<Object[]> results = query.getResultList();
        for (Object[] result : results) {
        	Integer channelId = (Integer)result[0];
            Long count = (Long)result[1];
            if (count > 0){
            	map.put(channelId, count);
            }
        }
        return map;
    }
    
	public Boolean findArticleTitleIsEntityByCrawler(final String title, final Integer channelId, final String userName){
    	String hql = "Select o From ArticleMain c Left Join c.article o Where o.title=:title And c.channelId=:channelId And o.owner=:owner And c.reference=false";

    	TypedQuery<Article> query = this.getEntityManager().createQuery(hql, Article.class);
    	query.setParameter("title", title);
    	query.setParameter("channelId", channelId);
    	query.setParameter("owner", userName);

    	List<Article> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }
    
	public Long findArticleMainCountByCrawler(final Integer channelId, final String userName){
    	String hql = "Select Count(c.id) From ArticleMain As c Left Join c.article As o Where c.channelId=:channelId And o.owner=:owner And c.reference=false";

    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("owner", userName);

    	return query.getSingleResult();
    }
}
