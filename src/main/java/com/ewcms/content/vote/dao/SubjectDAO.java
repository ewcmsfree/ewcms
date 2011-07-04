/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.vote.model.Subject;

/**
 * @author wu_zhijun
 */
@Repository
public class SubjectDAO extends JpaDAO<Long, Subject> {
	
	@SuppressWarnings("unchecked")
	public Long findSubjectMaxSort(Long questionnaireId){
    	String hql = "Select Max(s.sort) FROM Questionnaire As q Right Join q.subjects As s Where q.id=?";
    	List<Long> list = this.getJpaTemplate().find(hql, questionnaireId);
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Subject findSubjectBySort(Long questionnaireId, Long sort){
		String hql = "Select s From Questionnaire As q Right Join q.subjects As s Where q.id=? And s.sort=?";
		List<Subject> list = this.getJpaTemplate().find(hql, questionnaireId, sort);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
}
