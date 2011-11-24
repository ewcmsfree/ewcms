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
import com.ewcms.plugin.report.model.TextReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class TextReportDAO extends JpaDAO<Long, TextReport> {
	@SuppressWarnings("unchecked")
	public List<CategoryReport> findCategoryReportByTextReportId(Long textReportId){
		String hql = "Select c From CategoryReport As c Left Join c.texts As t Where t.id=?";
    	List<CategoryReport> list = this.getJpaTemplate().find(hql, textReportId);
    	if (list.isEmpty()) return null;
    	return list;
	}
}
