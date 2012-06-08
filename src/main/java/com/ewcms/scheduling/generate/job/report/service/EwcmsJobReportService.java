/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.report.manager.service.ChartReportServiceable;
import com.ewcms.plugin.report.manager.service.TextReportServiceable;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.plugin.report.model.TextReport.Type;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.JobClassEntity;
import com.ewcms.scheduling.generate.job.report.dao.EwcmsJobReportDAO;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;
import com.ewcms.scheduling.manager.SchedulingFacable;
import com.ewcms.scheduling.manager.dao.JobClassDAO;
import com.ewcms.scheduling.manager.dao.JobInfoDAO;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class EwcmsJobReportService implements EwcmsJobReportServiceable {

	@Autowired
	private EwcmsJobReportDAO ewcmsJobReportDAO;
	@Autowired
	private ChartReportServiceable chartService;
	@Autowired
	private TextReportServiceable textService;
	@Autowired
	private JobInfoDAO jobInfoDAO;
	@Autowired
	private JobClassDAO jobClassDAO;
	@Autowired
	private SchedulingFacable schedulingFac;
	
	@Override
	public Long saveOrUpdateJobReport(Long reportId, PageDisplayVO vo, String reportType, Set<EwcmsJobParameter> ewcmsJobParameters) throws BaseException {
		TextReport textReport = null;
		ChartReport chartReport = null;
		if (reportType.equals("text")){
			textReport = textService.findTextReportById(reportId);
		}else if (reportType.equals("chart")){
			chartReport = chartService.findChartReportById(reportId);
		}
		
		if (textReport != null || chartReport != null){
			JobInfo jobInfo = new JobInfo();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				jobInfo = jobInfoDAO.get(vo.getJobId());
			}
			
			if (jobInfo == null) {
				throw new BaseException("定时任务已经被删除,请重新操作!","定时任务已经被删除,请重新操作!");
			}
			
			jobInfo = ConversionUtil.constructJobInfoVo(jobInfo,vo);

			EwcmsJobReport jobReport = new EwcmsJobReport();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0) {
				jobReport.setId(vo.getJobId());
				jobReport.setJobClass(jobInfo.getJobClass());
			}else{
				JobClass jobClass = jobClassDAO.findByJobClassByClassEntity(JobClassEntity.JOB_REPORT);
				if (jobClass == null) {
					jobClass = new JobClass();
					jobClass.setClassEntity(JobClassEntity.JOB_REPORT);
					jobClass.setClassName("报表定时器类");
					jobClass.setDescription("报表定时器类");
					jobClassDAO.persist(jobClass);
				}
				jobReport.setJobClass(jobClass);
			}
			
			if (reportType.equals("text")){
				if (vo.getOutputFormats() != null && vo.getOutputFormats().length > 0){
					jobReport.setOutputFormat(ConversionUtil.arrayToString(vo.getOutputFormats()));
				}else{
					jobReport.setOutputFormat(String.valueOf(Type.PDF));
				}
			}
			jobReport.setEwcmsJobParameters(ewcmsJobParameters);
			jobReport.setDescription(jobInfo.getDescription());
			jobReport.setLabel(jobInfo.getLabel());
			jobReport.setNextFireTime(jobInfo.getNextFireTime());
			jobReport.setOutputLocale(jobInfo.getOutputLocale());
			jobReport.setPreviousFireTime(jobInfo.getPreviousFireTime());
			jobReport.setState(jobInfo.getState());
			jobReport.setTrigger(jobInfo.getTrigger());
			jobReport.setUserName(jobInfo.getUserName());
			jobReport.setVersion(jobInfo.getVersion());
			jobReport.setTextReport(textReport);
			jobReport.setChartReport(chartReport);
			if (jobReport.getId() == null) {
				return schedulingFac.saveScheduleJob(jobReport);
			} else {
				return schedulingFac.updateScheduledJob(jobReport);
			}
		}
		return null;
	}

	@Override
	public EwcmsJobReport getScheduledJobReport(Long jobId) {
		return ewcmsJobReportDAO.get(jobId);
	}

	@Override
	public EwcmsJobReport getSchedulingByReportId(Long reportId, String reportType) {
		return ewcmsJobReportDAO.findJobReportByReportId(reportId, reportType);
	}

	@Override
	public List<EwcmsJobParameter> findByJobReportParameterById(Long jobReportId) {
		return ewcmsJobReportDAO.findByJobReportParameterById(jobReportId);
	}

}
