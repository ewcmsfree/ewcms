/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

public class MsgReceiveQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 8414448374757960563L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		String hql = "From MsgReceive As m Where m.userName=:userName ";
		String countHql = "Select count(m.id) From MsgReceive As m Where m.userName=:userName ";

		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)) {
			hql += " And m.id=:id ";
			countHql += " And m.id=:id";
		}
		 String title = getParameterValue(String.class, "title", "");
		 if (isStringNotEmpty(title)){
			 hql += " And m.title Like :title";
			 countHql += " And m.title Like :title";
		 }
		String readTimeStart = getParameterValue(String.class, "readTimeStart", "");
		if (isStringNotEmpty(readTimeStart)) {
			hql += " And m.readTime>=:readTimeStart";
			countHql += " And m.readTime>=:readTimeStart";
		}
		String readTimeEnd = getParameterValue(String.class, "readTimeEnd", "");
		if (isStringNotEmpty(readTimeEnd)) {
			hql += " And m.readTime<=:readTimeEnd";
			countHql += " And m.readTime<=:readTimeEnd";
		}
		String subscription = getParameterValue(String.class, "subscription");
		if (isStringNotEmpty(subscription) && !subscription.equals("-1")) {
			hql += " And q.subscription=:subscription";
			countHql += " And q.subscription=:subscription";
		}

		hql += " Order By m.read, m.readTime Desc, m.id Desc";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (isNotNull(id)) {
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(readTimeStart)) {
			try {
				query.setParameter("readTimeStart", DATE_FORMAT.parse(readTimeStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(readTimeEnd)) {
			try {
				query.setParameter("readTimeEnd", DATE_FORMAT.parse(readTimeEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(subscription) && !subscription.equals("-1")){
			query.setParameter("subscription", Boolean.parseBoolean(subscription));
		}
		
		query.setParameter("userName", EwcmsContextUtil.getUserName());

		setDateFormat(DATE_FORMAT);

		return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "From MsgReceive As m Where m.userName=:userName And m.id In (:id) Order By m.read, m.readTime Desc, m.id Desc";
		String countHql = "Select count(m.id) From MsgReceive As m Where m.userName=:userName And m.id In (:id) ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Long.class));
		query.setParameter("userName", EwcmsContextUtil.getUserName());
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
	}
}
