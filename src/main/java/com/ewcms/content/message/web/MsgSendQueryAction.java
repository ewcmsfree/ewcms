/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.notes.model.FrequencyStatus;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;

public class MsgSendQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 8414448374757960563L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "From MsgSend As m Where m.userName=:userName ";
		String countHql = "Select count(m.id) From MsgSend As m Where m.userName=:userName ";

		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)) {
			hql += " And m.id=:id ";
			countHql += " And m.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)) {
			hql += " And m.title Like :title";
			countHql += " And m.title Like :title";
		}
		String sendTimeStart = getParameterValue(String.class, "sendTimeStart",	"");
		if (isStringNotEmpty(sendTimeStart)) {
			hql += " And m.sendTime>=:sendTimeStart";
			countHql += " And m.sendTime>=:sendTimeStart";
		}
		String sendTimeEnd = getParameterValue(String.class, "sendTimeEnd", "");
		if (isStringNotEmpty(sendTimeEnd)) {
			hql += " And m.sendTime<=:sendTimeEnd";
			countHql += " And m.sendTime<=:sendTimeEnd";
		}
		String status = getParameterValue(String.class, "status", "");
		if (isStringNotEmpty(status) && !status.equals("-1")) {
			hql += " And m.status=:status";
			countHql += " And m.status=:status";
		}

		hql += " Order By m.sendTime Desc, m.id Desc";

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (isNotNull(id)) {
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(sendTimeStart)) {
			try {
				query.setParameter("sendTimeStart", DATE_FORMAT.parse(sendTimeStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(sendTimeEnd)) {
			try {
				query.setParameter("sendTimeEnd", DATE_FORMAT.parse(sendTimeEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(status) && !status.equals("-1")) {
			query.setParameter("status", FrequencyStatus.valueOf(status));
		}

		query.setParameter("userName", EwcmsContextUtil.getUserName());

		setDateFormat(DATE_FORMAT);

		return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory,
			int rows, int page, String[] selections, Order order) {
		String hql = "From MsgSend As m Where m.userName=:userName And m.id In (:id) Order By m.sendTime Desc, m.id Desc";
		String countHql = "Select count(m.id) From MsgSend As m Where m.userName=:userName And m.id In (:id) ";

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);

		query.setParameter("id", getIds(Long.class));
		query.setParameter("userName", EwcmsContextUtil.getUserName());

		setDateFormat(DATE_FORMAT);

		return query.setRow(rows).setPage(page).queryResult();
	}
}
