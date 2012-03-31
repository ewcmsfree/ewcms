/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
public class EwcmsJobReportDAO extends JpaDAO<Long, EwcmsJobReport> {

	public EwcmsJobReport findJobReportByReportId(final Long reportId, final String reportType) {
		String hql = "Select o From EwcmsJobReport o Inner Join ";
		if (reportType.equals("text")){
			hql += " o.textReport c ";
		}else if (reportType.equals("chart")){
			hql += " o.chartReport c ";
		}
		hql += " Where c.id=:reportId ";

		TypedQuery<EwcmsJobReport> query = this.getEntityManager().createQuery(hql, EwcmsJobReport.class);
    	query.setParameter("reportId", reportId);

    	EwcmsJobReport ewcmsJobReport = null;
    	try{
    		ewcmsJobReport = (EwcmsJobReport) query.getSingleResult();
    	}catch(NoResultException e){
    		
    	}
    	return ewcmsJobReport;
	}

	public List<EwcmsJobParameter> findByJobReportParameterById(final Long jobReportId) {
		String hql = "Select p From EwcmsJobReport o Join o.ewcmsJobParameters p Where o.id=:jobReportId";
		
		TypedQuery<EwcmsJobParameter> query = this.getEntityManager().createQuery(hql, EwcmsJobParameter.class);
		query.setParameter("jobReportId", jobReportId);

		return query.getResultList();
	}
}
