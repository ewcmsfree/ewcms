package com.ewcms.plugin.report.manager.web;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class ParameterQueryAction extends QueryBaseAction{

	private static final long serialVersionUID = -859365860117918404L;
	
	private Long reportId;
	private String reportType;
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long textId) {
		this.reportId = textId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String reportName = "TextReport";
		if (reportType.equals("chart")){
			reportName = "ChartReport";
		}
		String hql = "Select p From " + reportName + " As r Left Join r.parameters As p Where r.id=:reportId Order By p.id ";
		String countHql = "Select Count(p.id) From " + reportName + " As r Left Join r.parameters As p Where r.id=:reportId ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("reportId", getReportId());
		
		return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String reportName = "TextReport";
		if (reportType.equals("chart")){
			reportName = "ChartReport";
		}
		String hql = "Select p From " + reportName + " As r Left Join r.parameters As p Where r.id=:reportId Order By p.id ";
		String countHql = "Select Count(p.id) From " + reportName + " As r Left Join r.parameters As p Where r.id=:reportId ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		query.setParameter("reportId", getReportId());
		
		return query.setRow(rows).setPage(page).queryResult();
	}

}
