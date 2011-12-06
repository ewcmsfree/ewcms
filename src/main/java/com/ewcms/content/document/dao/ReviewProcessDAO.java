/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.dao;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public Long findReviewProcessCountByChannel(Integer channelId){
		String hql = "Select Count(p.id) From ReviewProcess As p Where p.channelId=?";
		List<Long> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty()) return 0L;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReviewProcess> findReviewProcessByChannel(Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=?";
		List<ReviewProcess> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty()) return null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public ReviewProcess findReviewProcessByIdAndChannel(Long id, Integer channelId){
		String hql = "From ReviewProcess As p Where p.id=? And p.channelId=?";
		List<ReviewProcess> list = this.getJpaTemplate().find(hql, id, channelId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public ReviewProcess findLastReviewProcessByChannel(Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=? And p.nextProcess Is Null";
		List<ReviewProcess> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public ReviewProcess findFirstReviewProcessByChannel(Integer channelId){
		String hql = "From ReviewProcess As p Where p.channelId=? And p.prevProcess Is Null";
		List<ReviewProcess> list = this.getJpaTemplate().find(hql, channelId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
    @SuppressWarnings("unchecked")
	public Boolean findReviewUserIsEntityByProcessIdAndUserName(Long reviewProcessId, String userName){
    	String hql = "Select p From ReviewProcess As p Left Join p.reviewUsers As u Where p.id=? And u.userName=?";
    	List<ReviewProcess> list = this.getJpaTemplate().find(hql, reviewProcessId, userName);
    	if (list.isEmpty()) return false;
    	return true;
    }

    @SuppressWarnings("unchecked")
	public Boolean findReviewGroupIsEntityByProcessIdAndUserName(Long reviewProcessId, String goupName){
    	String hql = "Select p From ReviewProcess As p Left Join p.reviewGroups As g Where p.id=? And g.groupName=?";
    	List<ReviewProcess> list = this.getJpaTemplate().find(hql, reviewProcessId, goupName);
    	if (list.isEmpty()) return false;
    	return true;
    }

    @SuppressWarnings("unchecked")
	public ReviewProcess findIsEntityReviewProcessByChannelAndName(Integer channelId, String name){
    	String hql = "From ReviewProcess As p Where p.channelId=? And p.name=?";
    	List<ReviewProcess> list = this.getJpaTemplate().find(hql, channelId, name);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<ArticleMain> findArticleMainByReviewProcess(Integer channelId, Long reviewProcessId){
    	String hql = "Select m From ArticleMain As m Left Join m.article As a Left Join a.reviewProcess As r Where m.channelId=? And r.id=? And m.reference=false";
    	List<ArticleMain> list = this.getJpaTemplate().find(hql, channelId, reviewProcessId);
    	if (list.isEmpty()) return null;
    	return list;
    }
}
