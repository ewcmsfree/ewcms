/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.notes.manager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.notes.model.Memoranda;

/**
 * 备忘录DAO
 * 
 * @author wu_zhijun
 */
@Repository
public class MemorandaDAO extends JpaDAO<Long, Memoranda>  {
	
	public List<Memoranda> findMemorandaByDate(final Date beginDate, final Date endDate, final String userName){
		if (beginDate == null && endDate == null){
			return new ArrayList<Memoranda>();
		}
		String hql = "From Memoranda As m Where m.noteDate>=:beginDate And m.noteDate<:endDate And m.userName=:userName Order By m.warnTime Desc, m.noteDate Desc, m.id Desc";
		
		TypedQuery<Memoranda> query = this.getEntityManager().createQuery(hql, Memoranda.class);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}
	
	public List<Memoranda> findMemorandaByWarn(final String userName){
		String hql = "From Memoranda As m Where m.userName=:userName And m.warn=true And m.warnTime Is Not Null And m.noteDate Is Not Null";
		
		TypedQuery<Memoranda> query = this.getEntityManager().createQuery(hql, Memoranda.class);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}
	
	public List<Memoranda> findMemorandaByUserName(final String userName){
		String hql = "From Memoranda As m Where m.userName=:userName Order By m.noteDate Desc, m.warnTime Desc, m.id Desc";
		
		TypedQuery<Memoranda> query = this.getEntityManager().createQuery(hql, Memoranda.class);
		query.setParameter("userName", userName);
		
		return query.getResultList();
	}
}
