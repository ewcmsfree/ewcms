/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class ChartReportDAO extends JpaDAO<Long, ChartReport> {
	@SuppressWarnings("unchecked")
	public List<CategoryReport> findCategoryReportByChartReportId(Long chartReportId){
		String hql = "Select c From CategoryReport As c Left Join c.charts As t Where t.id=?";
    	List<CategoryReport> list = this.getJpaTemplate().find(hql, chartReportId);
    	if (list.isEmpty()) return null;
    	return list;
	}
}
