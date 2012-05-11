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
import com.ewcms.plugin.vote.model.SubjectItem;

/**
 * 问卷调查主题明细DAO
 * 
 * @author 吴智俊
 */
@Repository
public class SubjectItemDAO extends JpaDAO<Long, SubjectItem> {
	
	public Long findSubjectItemMaxSort(final Long subjectId){
    	String hql = "Select Max(i.sort) FROM Subject As s Right Join s.subjectItems As i Where s.id=:subjectId";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("subjectId", subjectId);
    	
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

	public SubjectItem findSubjectItemBySubjectAndInputStatus(final Long subjectId){
		String hql = "Select i From Subject As s Right Join s.subjectItems As i Where s.id=:subjectId And s.subjectStatus=subjectStatus";
		
		TypedQuery<SubjectItem> query = this.getEntityManager().createQuery(hql, SubjectItem.class);
		query.setParameter("subjectId", subjectId);
		query.setParameter("subjectStatus", Subject.Status.INPUT);
		
		SubjectItem subjectItem = null;
		try{
			subjectItem = (SubjectItem) query.getSingleResult();
		}catch(NoResultException e){
		}
		return subjectItem;
	}
	
	public SubjectItem findSubjectItemBySort(final Long subjectId, final Long sort){
		String hql = "Select i From Subject As s Right Join s.subjectItems As i Where s.id=:subjectId And i.sort=:sort";
		
		TypedQuery<SubjectItem> query = this.getEntityManager().createQuery(hql, SubjectItem.class);
		query.setParameter("subjectId", subjectId);
		query.setParameter("sort", sort);
		
		SubjectItem subjectItem = null;
		try{
			subjectItem = (SubjectItem) query.getSingleResult();
		}catch(NoResultException e){
		}
		return subjectItem;
	}
}
