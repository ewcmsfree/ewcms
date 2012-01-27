/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.dao;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public Long findSubjectItemMaxSort(Long subjectId){
    	String hql = "Select Max(i.sort) FROM Subject As s Right Join s.subjectItems As i Where s.id=?";
    	List<Long> list = this.getJpaTemplate().find(hql, subjectId);
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}

	@SuppressWarnings("unchecked")
	public SubjectItem findSubjectItemBySubjectAndInputStatus(Long subjectId){
		String hql = "Select i From Subject As s Right Join s.subjectItems As i Where s.id=? And s.subjectStatus=?";
		List<SubjectItem> list = this.getJpaTemplate().find(hql, subjectId, Subject.Status.INPUT);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public SubjectItem findSubjectItemBySort(Long subjectId, Long sort){
		String hql = "Select i From Subject As s Right Join s.subjectItems As i Where s.id=? And i.sort=?";
		List<SubjectItem> list = this.getJpaTemplate().find(hql, subjectId, sort);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
}
