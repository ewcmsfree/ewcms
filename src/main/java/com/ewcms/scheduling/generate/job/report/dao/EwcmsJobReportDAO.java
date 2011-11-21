/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class EwcmsJobReportDAO extends JpaDAO<Integer, EwcmsJobReport> {
	@SuppressWarnings("unchecked")
	public EwcmsJobReport findJobReportByReportId(Long reportId, String reportType) {
		String hql = "Select o From EwcmsJobReport o Inner Join ";
		if (reportType.equals("text")){
			hql += " o.textReport c ";
		}else if (reportType.equals("chart")){
			hql += " o.chartReport c ";
		}
		hql += " Where c.id=? ";
		List<EwcmsJobReport> list = this.getJpaTemplate().find(hql, reportId);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<EwcmsJobParameter> findByJobReportParameterById(Integer jobReportId) {
		String hql = "Select p From EwcmsJobReport o Join o.ewcmsJobParameters p Where o.id=?";
		List<EwcmsJobParameter> list = getJpaTemplate().find(hql, jobReportId);
		if (list.isEmpty())
			return new ArrayList<EwcmsJobParameter>();
		return list;
	}
}
