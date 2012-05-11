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
import com.ewcms.plugin.vote.model.Subject;

/**
 * 问卷调查主题DAO
 * 
 * @author 吴智俊
 */
@Repository
public class SubjectDAO extends JpaDAO<Long, Subject> {
	
	public Long findSubjectMaxSort(final Long questionnaireId){
    	String hql = "Select Max(s.sort) FROM Questionnaire As q Right Join q.subjects As s Where q.id=:questionnaireId";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("questionnaireId", questionnaireId);
    	
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
	
	public Subject findSubjectBySort(final Long questionnaireId, final Long sort){
		String hql = "Select s From Questionnaire As q Right Join q.subjects As s Where q.id=:questionnaireId And s.sort=:sort";
		
		TypedQuery<Subject> query = this.getEntityManager().createQuery(hql, Subject.class);
		query.setParameter("questionnaireId", questionnaireId);
		query.setParameter("sort", sort);
		
		Subject subject = null;
		try{
			subject = (Subject) query.getSingleResult();
		}catch(NoResultException e){
		}
		
		return subject;
	}
}
