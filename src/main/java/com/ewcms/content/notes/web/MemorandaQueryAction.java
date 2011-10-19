/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.notes.model.BeforeStatus;
import com.ewcms.content.notes.model.FrequencyStatus;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 *
 * @author wu_zhijun
 */
public class MemorandaQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = 5942472910913752255L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "From Memoranda As m Where m.userName=:userName ";
		String countHql = "Select count(m.id) From Memoranda As m Where m.userName=:userName ";
		
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And m.id=:id ";
			countHql += " And m.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And m.title Like :title";
			countHql += " And m.title Like :title";
		}
		String noteDateStart = getParameterValue(String.class, "noteDateStart","");
		if (isStringNotEmpty(noteDateStart)){
			hql += " And m.noteDate>=:noteDateStart";
			countHql += " And m.noteDate>=:noteDateStart";
		}
		String noteDateEnd = getParameterValue(String.class, "noteDateEnd","");
		if (isStringNotEmpty(noteDateEnd)){
			hql += " And m.noteDate<=:noteDateEnd";
			countHql += " And m.noteDate<=:noteDateEnd";
		}
		String frequencyStatus = getParameterValue(String.class, "frequencyStatus","");
		if (isStringNotEmpty(frequencyStatus) && !frequencyStatus.equals("-1")){
			hql += " And m.frequency=:frequencyStatus";
			countHql += " And m.frequency=:frequencyStatus";
		}
		String beforeStatus = getParameterValue(String.class, "beforeStatus","");
		if (isStringNotEmpty(beforeStatus) && !beforeStatus.equals("-1")){
			hql += " And m.before=:beforeStatus";
			countHql += " And m.before=:beforeStatus";
		}
		String warn = getParameterValue(String.class, "warn", "");
		if (isStringNotEmpty(warn) && !warn.equals("-1")){
			hql += " And m.warn=:warn";
			countHql += " And m.warn=:warn";
		}
		String missRemind = getParameterValue(String.class, "missRemind", "");
		if (isStringNotEmpty(missRemind) && !missRemind.equals("-1")){
			hql += " And m.missRemind=:missRemind";
			countHql += " And m.missRemind=:missRemind";
		}
		
		hql += " Order By m.noteDate Desc, m.warnTime Desc, m.id Desc";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(noteDateStart)){
			try {
				query.setParameter("noteDateStart", DATE_FORMAT.parse(noteDateStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(noteDateEnd)){
			try {
				query.setParameter("noteDateEnd", DATE_FORMAT.parse(noteDateEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(frequencyStatus) && !frequencyStatus.equals("-1")){
			query.setParameter("frequencyStatus", FrequencyStatus.valueOf(frequencyStatus));
		}
		if (isStringNotEmpty(beforeStatus) && !beforeStatus.equals("-1")){
			query.setParameter("beforeStatus", BeforeStatus.valueOf(beforeStatus));
		}
		if (isStringNotEmpty(warn) && !warn.equals("-1")){
			query.setParameter("warn", Boolean.parseBoolean(warn));
		}
		if (isStringNotEmpty(missRemind) && !missRemind.equals("-1")){
			query.setParameter("missRemind", Boolean.parseBoolean(missRemind));
		}
		
		query.setParameter("userName", EwcmsContextUtil.getUserName());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "From Memoranda As m Where m.userName=:userName And m.id In (:id) Order By m.noteDate Desc, m.warnTime Desc, m.id Desc";
		String countHql = "Select count(m.id) From Memoranda As m Where m.userName=:userName And m.id In (:id) ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Long.class));
		query.setParameter("userName", EwcmsContextUtil.getUserName());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
    }
}
