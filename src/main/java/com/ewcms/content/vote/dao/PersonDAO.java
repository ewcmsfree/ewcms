/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.vote.model.Person;
import com.ewcms.content.vote.model.Record;

/**
 * 投票人员信息DAO
 * 
 * @author 吴智俊
 */
@Repository
public class PersonDAO extends JpaDAO<Long, Person>{
	@SuppressWarnings("unchecked")
	public Boolean findPersonIsEntity(Long questionnaireId, String ip){
		String hql = "From Person p Where p.questionnaireId=? And p.ip=?";
		List<Person> list = this.getJpaTemplate().find(hql, questionnaireId, ip);
		if (list.isEmpty()) return false;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Record> findRecordBySubjectTitle(Long personId, String subjectName){
		String hql = "Select r From Person As p Right Join p.records As r Where p.id=? And r.subjectName=?";
		List<Record> list = this.getJpaTemplate().find(hql, personId, subjectName);
		if (list.isEmpty()) return null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Record findRecordBySubjectItemTitle(Long personId, String subjectItemName){
		String hql = "Select r From Person As p Right Join p.records As r Where p.id=? And r.subjectName=?";
		List<Record> list = this.getJpaTemplate().find(hql, personId, subjectItemName);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
}
