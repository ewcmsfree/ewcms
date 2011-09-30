/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.notes.model.Memoranda;

/**
 * 备忘录DAO
 * 
 * @author wu_zhijun
 */
@Repository
public class MemorandaDAO extends JpaDAO<Long, Memoranda>  {
	@SuppressWarnings("unchecked")
	public List<Memoranda> findMemorandaByDate(Date beginDate, Date endDate, String userName){
		if (beginDate == null && endDate == null){
			return new ArrayList<Memoranda>();
		}
		String hql = "From Memoranda As m Where m.noteDate>=? And m.noteDate<? And m.userName=? Order By m.warnTime Desc, m.noteDate Desc, m.id Desc";
		List<Memoranda> list = this.getJpaTemplate().find(hql, beginDate, endDate, userName);
		if (list.isEmpty()) return new ArrayList<Memoranda>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Memoranda> findMemorandaByWarn(String userName){
		String hql = "From Memoranda As m Where m.userName=? And m.warn=? And m.warnTime Is Not Null And m.noteDate Is Not Null";
		List<Memoranda> list = this.getJpaTemplate().find(hql, userName, true);
		if (list.isEmpty()) return new ArrayList<Memoranda>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Memoranda> findMemorandaByUserName(String userName){
		String hql = "From Memoranda As m Where m.userName=? Order By m.noteDate Desc, m.warnTime Desc, m.id Desc";
		List<Memoranda> list = this.getJpaTemplate().find(hql, userName);
		if (list.isEmpty()) return new ArrayList<Memoranda>();
		return list;
	}
}
