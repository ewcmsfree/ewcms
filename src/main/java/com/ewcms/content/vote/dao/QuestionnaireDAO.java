/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.vote.model.Questionnaire;

/**
 * @author wu_zhijun
 */
@Repository
public class QuestionnaireDAO extends JpaDAO<Long, Questionnaire> {
	@SuppressWarnings("unchecked")
	public Long findQuestionnaireMaxSort(Integer channelId){
    	String hql = "Select Max(q.sort) FROM Questionnaire AS q Where q.channelId=?";
    	List<Long> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Long findPersonCount(Long questionnaireId){
		String hql = "Select Count(p.id) From Person p Where p.questionnaireId=?";
		List<Long> list = this.getJpaTemplate().find(hql, questionnaireId);
		if (list.isEmpty()) return 0L;
		return list.get(0);
	}
}
