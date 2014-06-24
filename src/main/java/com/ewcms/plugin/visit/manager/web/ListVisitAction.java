/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.web.QueryBaseAction;

/**
 * @author 吴智俊
 */
public class ListVisitAction extends QueryBaseAction {

	private static final long serialVersionUID = 8600868922673131146L;
	
	private static final String SUMMARY_CLASS_NAME = SummaryVo.class.getPackage().getName() + "." + SummaryVo.class.getSimpleName();
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private String startDate;
	private String endDate;
	private Integer siteId = getCurrentSite().getId();
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select new " + SUMMARY_CLASS_NAME + "(v.ip, i.remotePort, v.country, v.province, v.city, i.url, i.visitDate, i.visitTime, i.referer, v.browser, v.os, v.screen, v.language, v.flashVersion) "
				+ "From VisitItem As i, Visit As v "
				+ "Where i.uniqueId = v.uniqueId And i.siteId=:siteId ";
				
		String countHql = "select count(i.id) From VisitItem As i, Visit As v "
				+ "Where i.uniqueId = v.uniqueId And i.siteId=:siteId ";
		
		if (isStringNotEmpty(getStartDate())){
			hql += " And i.visitDate>=:startDate ";
			countHql += " And i.visitDate>=:startDate ";
		}
		if (isStringNotEmpty(getEndDate())){
			hql += " And i.visitDate<=:endDate ";
			countHql += " And i.visitDate<=:endDate ";
		}
		
		hql +=  "Order By i.visitDate Desc, i.visitTime Desc Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("siteId", siteId);
		
		if (isStringNotEmpty(getStartDate())){
			try {
				query.setParameter("startDate", DATE_FORMAT.parse(getStartDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(getEndDate())){
			try {
				query.setParameter("endDate", DATE_FORMAT.parse(getEndDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}

}
