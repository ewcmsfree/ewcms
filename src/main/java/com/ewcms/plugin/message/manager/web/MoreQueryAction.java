/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.message.model.MsgSend.Type;
import com.ewcms.web.QueryBaseAction;

public class MoreQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 8414448374757960563L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		String hql = "From MsgSend As s Where s.type=:type ";
		String countHql = "Select Count(s.id) From MsgSend As s Where s.type=:type ";
		
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)) {
			hql += " And s.title Like :title";
			countHql += " And s.title Like :title";
		}
		
		hql += " Order By s.sendTime Desc, s.id Desc ";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (type.equals(Type.NOTICE.name())){
			query.setParameter("type", Type.NOTICE);
		}else if (type.equals(Type.SUBSCRIPTION.name())){
			query.setParameter("type", Type.SUBSCRIPTION);
		}
		
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		
		setDateFormat(DATE_FORMAT);

		return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "From MsgSend As s Where s.type=:type ";
		String countHql = "Select Count(s.id) From MsgSend As s Where s.type=:type ";
		
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)) {
			hql += " And s.title Like :title";
			countHql += " And s.title Like :title";
		}
		
		hql += " Order By s.sendTime Desc, s.id Desc";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (type.equals(Type.NOTICE.name())){
			query.setParameter("type", Type.NOTICE);
		}else if (type.equals(Type.SUBSCRIPTION.name())){
			query.setParameter("type", Type.SUBSCRIPTION);
		}
		
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		
		setDateFormat(DATE_FORMAT);

		return query.setRow(rows).setPage(page).queryResult();
	}
}
