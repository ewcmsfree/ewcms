/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.content.document.model.ReviewProcess;

/**
 * 审核流程DAO
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class ReviewProcessDAO extends JpaDAO<Long, ReviewProcess> {
	
	public Long findReviewProcessCountByChannel(final Integer channelId){
		String hql = "Select Count(p.id) From ReviewProcess As p Where p.channelId=:channelId";

		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("channelId", channelId);
		
		return query.getSingleResult();
	}
	
	public List<ReviewProcess> findReviewProcessByChannel(final Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=:channelId";

		TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
		query.setParameter("channelId", channelId);

		return query.getResultList();
	}
	
	public ReviewProcess findReviewProcessByIdAndChannel(final Long reviewProcessid, final Integer channelId){
		String hql = "From ReviewProcess As p Where p.id=:reviewProcessid And p.channelId=:channelId";

		TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
		query.setParameter("reviewProcessid", reviewProcessid);
		query.setParameter("channelId", channelId);

		ReviewProcess reviewProcess = null;
		try{
			reviewProcess = (ReviewProcess) query.getSingleResult();
		}catch (NoResultException e){
		}
		return reviewProcess;
	}
	
	public ReviewProcess findLastReviewProcessByChannel(final Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=:channelId And p.nextProcess Is Null";

		TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
		query.setParameter("channelId", channelId);

		ReviewProcess reviewProcess = null;
		try{
			reviewProcess = (ReviewProcess) query.getSingleResult();
		}catch(NoResultException e){
		}
		return reviewProcess;
	}
	
	public ReviewProcess findFirstReviewProcessByChannel(final Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=:channelId And p.prevProcess Is Null";

		TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
		query.setParameter("channelId", channelId);

		ReviewProcess reviewProcess = null;
		try{
			reviewProcess = (ReviewProcess) query.getSingleResult();
		}catch(NoResultException e){
		}
		return reviewProcess;
	}
	
	public Boolean findReviewUserIsEntityByProcessIdAndUserName(final Long reviewProcessId, final String userName){
    	String hql = "Select p From ReviewProcess As p Left Join p.reviewUsers As u Where p.id=:reviewProcessId And u.userName=:userName";

    	TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
    	query.setParameter("reviewProcessId", reviewProcessId);
    	query.setParameter("userName", userName);

    	List<ReviewProcess> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }

	public Boolean findReviewGroupIsEntityByProcessIdAndUserName(final Long reviewProcessId, final String groupName){
    	String hql = "Select p From ReviewProcess As p Left Join p.reviewGroups As g Where p.id=:reviewProcessId And g.groupName=:groupName";

    	TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
    	query.setParameter("reviewProcessId", reviewProcessId);
    	query.setParameter("groupName", groupName);

    	List<ReviewProcess> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }

	public ReviewProcess findIsEntityReviewProcessByChannelAndName(final Integer channelId, final String name){
    	String hql = "From ReviewProcess As p Where p.channelId=:channelId And p.name=:name";

    	TypedQuery<ReviewProcess> query = this.getEntityManager().createQuery(hql, ReviewProcess.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("name", name);

    	ReviewProcess reviewProcess = null;
    	try{
    		reviewProcess = (ReviewProcess) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return reviewProcess;
    }
    
	public List<ArticleMain> findArticleMainByReviewProcess(final Integer channelId, final Long reviewProcessId){
    	String hql = "Select m From ArticleMain As m Left Join m.article As a Left Join a.reviewProcess As r Where m.channelId=:channelId And r.id=:reviewProcessId And m.reference=false";

    	TypedQuery<ArticleMain> query = this.getEntityManager().createQuery(hql, ArticleMain.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("reviewProcessId", reviewProcessId);

    	return query.getResultList();
    }
}
