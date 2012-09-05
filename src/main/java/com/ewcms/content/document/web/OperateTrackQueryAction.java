/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class OperateTrackQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 2141489086820205940L;

	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Long articleMainId;
	
	public Long getArticleMainId() {
		return articleMainId;
	}

	public void setArticleMainId(Long articleMainId) {
		this.articleMainId = articleMainId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		String hql = "Select t From OperateTrack As t Where t.articleMainId=:articleMainId Order By t.id Desc ";
		String countHql = "Select count(t.id) From OperateTrack As t Where t.articleMainId=:articleMainId ";
		
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("articleMainId", getArticleMainId());
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select t From OperateTrack As t Where t.articleMainId=:articleMainId Order By t.id Desc ";
		String countHql = "Select count(t.id) From OperateTrack As t Where t.articleMainId=:articleMainId ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("articleMainId", getArticleMainId());
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
	}
}
