/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.vote.model.Questionnaire;

/**
 * 问卷调查主体DAO
 * 
 * @author 吴智俊
 */
@Repository
public class QuestionnaireDAO extends JpaDAO<Long, Questionnaire> {
	
	public Long findQuestionnaireMaxSort(final Integer channelId){
    	String hql = "Select Max(q.sort) FROM Questionnaire AS q Where q.channelId=:channelId";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	
    	Long maxSort = 0L;
    	try{
    		maxSort = (Long) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	if (maxSort == null){
    		maxSort = 0L;
    	}
    	return maxSort;
	}
	
	public Long findPersonCount(final Long questionnaireId){
		String hql = "Select Count(p.id) From Person p Where p.questionnaireId=:questionnaireId";
		
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("questionnaireId", questionnaireId);
		
		return query.getSingleResult();
	}
}
