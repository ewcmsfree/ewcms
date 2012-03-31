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

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class CategoryReportDAO extends JpaDAO<Long, CategoryReport> {
	
	public Boolean findTextIsEntityByTextAndCategory(final Long textReportId, final Long categoryReportId){
    	String hql = "Select c From CategoryReport As c Left Join c.texts As t Where t.id=:textReportId And c.id=:categoryReportId";
    	
    	TypedQuery<CategoryReport> query = this.getEntityManager().createQuery(hql, CategoryReport.class);
    	query.setParameter("textReportId", textReportId);
    	query.setParameter("categoryReportId", categoryReportId);
    	
    	List<CategoryReport> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }
	
	public Boolean findChartIsEntityByChartAndCategory(final Long chartReportId, final Long categoryReportId){
    	String hql = "Select c From CategoryReport As c Left Join c.charts As t Where t.id=:chartReportId And c.id=:categoryReportId";
    	
    	TypedQuery<CategoryReport> query = this.getEntityManager().createQuery(hql, CategoryReport.class);
    	query.setParameter("chartReportId", chartReportId);
    	query.setParameter("categoryReportId", categoryReportId);
    	
    	List<CategoryReport> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }
}
