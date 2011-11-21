/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class DomainQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -6105582233144966796L;
	
	private Long gatherId;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		String hql = "Select u From Gather As g Left Join g.domains As u Where g.id=:gatherId ";
		String countHql = "Select Count(u.id) From Gather As g Left Join g.domains As u Where g.id=:gatherId ";
		
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And u.id=:id ";
			countHql += " And u.id=:id";
		}
		String url = getParameterValue(String.class, "url", "");
		if (isStringNotEmpty(url)){
			hql += " And u.url Like :url";
			countHql += " And u.url Like :url";
		}
		
		hql += " Order By u.level";

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(url)){
			query.setParameter("url", "%" + url + "%");
		}

		query.setParameter("gatherId", getGatherId());
		
		return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select u From Gather As g Left Join g.domains As u Where g.id=:gatherId And u.id In (:id) Order By u.level";
		String countHql = "Select Count(u.id) From Gather As g Left Join g.domains As u Where g.id=:gatherId And u.id In (:id)";

		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Long.class));
		query.setParameter("gatherId", getGatherId());
		
		return query.setRow(rows).setPage(page).queryResult();
	}
}
