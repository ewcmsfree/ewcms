/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.vote.model.Person;
import com.ewcms.plugin.vote.model.Record;

/**
 * 投票人员信息DAO
 * 
 * @author 吴智俊
 */
@Repository
public class PersonDAO extends JpaDAO<Long, Person>{
	public Boolean findPersonIsEntity(final Long questionnaireId, final String ip){
		String hql = "From Person p Where p.questionnaireId=:questionnaireId And p.ip=:ip";
		
		TypedQuery<Person> query = this.getEntityManager().createQuery(hql, Person.class);
		query.setParameter("questionnaireId", questionnaireId);
		query.setParameter("ip", ip);
		
		List<Person> list = query.getResultList();
		return list.isEmpty() ? false : true;
	}
	
	public List<Record> findRecordBySubjectTitle(final Long personId, final String subjectName){
		String hql = "Select r From Person As p Right Join p.records As r Where p.id=:personId And r.subjectName=:subjectName";
		
		TypedQuery<Record> query = this.getEntityManager().createQuery(hql, Record.class);
		query.setParameter("personId", personId);
		query.setParameter("subjectName", subjectName);
		
		return query.getResultList();
	}
	
	public Record findRecordBySubjectItemTitle(final Long personId, final String subjectItemName){
		String hql = "Select r From Person As p Right Join p.records As r Where p.id=:personId And r.subjectName=:subjectItemName";
		
		TypedQuery<Record> query = this.getEntityManager().createQuery(hql, Record.class);
		query.setParameter("personId", personId);
		query.setParameter("subjectItemName", subjectItemName);
		
		Record record = null;
		try{
			record = (Record) query.getSingleResult();
		}catch(NoResultException e){
		}
		
		return record;
	}
}
