/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.notes.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.notes.model.Memoranda;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class MemorandaDAO extends JpaDAO<Long, Memoranda>  {
	@SuppressWarnings("unchecked")
	public List<Memoranda> findMemorandaByDate(Date beginDate, Date endDate, String userName){
		String hql = "From Memoranda As m Where m.noteDate>=? And m.noteDate<? And m.userName=? Order By m.id Desc";
		List<Memoranda> list = this.getJpaTemplate().find(hql, beginDate, endDate, userName);
		if (list.isEmpty()) return new ArrayList<Memoranda>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Memoranda> findMemorandaByWarn(String userName){
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		String hql = "From Memoranda As m Where m.userName=? And m.warn=? And m.remindTime>=? And m.warnTime Is Not Null And m.remindTime Is Not Null";
		List<Memoranda> list = this.getJpaTemplate().find(hql, userName, true, now);
		if (list.isEmpty()) return new ArrayList<Memoranda>();
		return list;
	}
}
