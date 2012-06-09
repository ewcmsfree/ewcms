/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class ChartReportDAO extends JpaDAO<Long, ChartReport> {
	
	public List<CategoryReport> findCategoryReportByChartReportId(final Long chartReportId){
		String hql = "Select c From CategoryReport As c Left Join c.charts As t Where t.id=:chartReportId";
		
		TypedQuery<CategoryReport> query = this.getEntityManager().createQuery(hql, CategoryReport.class);
		query.setParameter("chartReportId", chartReportId);
		
		return query.getResultList();
	}
	
	public List<EwcmsJobReport> findEwcmsJobReportByChartReportId(final Long chartReportId){
		String hql = "Select e From EwcmsJobReport As e Where e.chartReport.id=:chartReportId";
		
		TypedQuery<EwcmsJobReport> query = this.getEntityManager().createQuery(hql, EwcmsJobReport.class);
		query.setParameter("chartReportId", chartReportId);
		
		return query.getResultList();
	}
}
