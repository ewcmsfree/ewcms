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

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class CategoryReportDAO extends JpaDAO<Long, CategoryReport> {
	
	@SuppressWarnings("unchecked")
	public Boolean findTextIsEntityByTextAndCategory(Long textReportId, Long categoryReportId){
    	String hql = "Select c From CategoryReport As c Left Join c.texts As t Where t.id=? And c.id=?";
    	List<CategoryReport> list = this.getJpaTemplate().find(hql, textReportId, categoryReportId);
    	if (list.isEmpty()) return false;
    	return true;
    }
	
	@SuppressWarnings("unchecked")
	public Boolean findChartIsEntityByChartAndCategory(Long chartReportId, Long categoryReportId){
    	String hql = "Select c From CategoryReport As c Left Join c.charts As t Where t.id=? And c.id=?";
    	List<CategoryReport> list = this.getJpaTemplate().find(hql, chartReportId, categoryReportId);
    	if (list.isEmpty()) return false;
    	return true;
    }
}
