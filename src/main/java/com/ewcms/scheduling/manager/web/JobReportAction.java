/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.manager.web;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.report.EwcmsJobReportFacable;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author wu_zhijun
 *
 */
public class JobReportAction extends ActionSupport {

	private static final long serialVersionUID = 5346861972849849266L;

	@Autowired
	private EwcmsJobReportFacable ewcmsJobReportFac; 
	@Autowired
	private TextFactoryable textFactory;
	@Autowired
	private ChartFactoryable chartFactory;
	@Autowired
	private ReportFacable reportFac;

	private PageDisplayVO pageDisplayVo = new PageDisplayVO();
	private Long reportId;
	private String reportName;
	private String reportType;
	private List<PageShowParam> pageShowParams = new ArrayList<PageShowParam>();

	public PageDisplayVO getPageDisplayVo() {
		return pageDisplayVo;
	}

	public void setPageDisplayVo(PageDisplayVO pageDisplayVo) {
		this.pageDisplayVo = pageDisplayVo;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<PageShowParam> getPageShowParams() {
		return pageShowParams;
	}

	public void setPageShowParams(List<PageShowParam> pageShowParams) {
		this.pageShowParams = pageShowParams;
	}

	public String getJobReport() {
		EwcmsJobReport ewcmsJobReport = ewcmsJobReportFac.getSchedulingByReportId(getReportId(), getReportType());
		if (ewcmsJobReport != null){
			PageDisplayVO vo = ConversionUtil.constructPageVo(ewcmsJobReport);
			if (getReportType().equals("text")){
				TextReport text = ewcmsJobReport.getTextReport();
				vo.setReportId(text.getId());
				vo.setReportName(text.getName());
				vo.setReportType("text");
				vo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(ewcmsJobReport.getId()), textFactory.textParameters(text)));
				vo.setOutputFormats(ConversionUtil.stringToArray(((EwcmsJobReport) ewcmsJobReport).getOutputFormat()));
			}else if (getReportType().equals("chart")){
				ChartReport chart = ewcmsJobReport.getChartReport();
				vo.setReportId(chart.getId());
				vo.setReportName(chart.getName());
				vo.setReportType("chart");
				vo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(ewcmsJobReport.getId()), chartFactory.chartParameters(chart)));
			}
			setPageDisplayVo(vo);
			setPageShowParams(vo.getPageShowParams());
		}else{
			if (getReportType().equals("text")){
				TextReport text = reportFac.findTextReportById(getReportId());
				getPageDisplayVo().setLabel(text.getName());
				getPageDisplayVo().setReportName(text.getName());
				getPageDisplayVo().setPageShowParams(textFactory.textParameters(text));
			}else if (getReportType().equals("chart")){
				ChartReport chart =  reportFac.findChartReportById(getReportId());
				getPageDisplayVo().setLabel(chart.getName());
				getPageDisplayVo().setReportName(chart.getName());
				getPageDisplayVo().setPageShowParams(chartFactory.chartParameters(chart));
			}
			setPageShowParams(getPageDisplayVo().getPageShowParams());
		}
		return INPUT;
	}

	public String saveJobReport() {
		try {
			Set<EwcmsJobParameter> ewcmsJobParameters = new LinkedHashSet<EwcmsJobParameter>();
			if (getReportType().equals("text")){
				TextReport text = reportFac.findTextReportById(getReportId());
				ewcmsJobParameters = ConversionUtil.pageToJob(new LinkedHashSet<EwcmsJobParameter>(),text.getParameters(),ServletActionContext.getRequest());
			}else if (getReportType().equals("chart")){
				ChartReport chart = reportFac.findChartReportById(getReportId());
				ewcmsJobParameters = ConversionUtil.pageToJob(new LinkedHashSet<EwcmsJobParameter>(),chart.getParameters(),ServletActionContext.getRequest());
			}
			Integer jobId = ewcmsJobReportFac.saveOrUpdateJobReport(getReportId(), getPageDisplayVo(), getReportType(), ewcmsJobParameters);
			if (jobId == null) {
				addActionMessage("操作失败");
			}
			EwcmsJobReport jobReport = ewcmsJobReportFac.getScheduledJobReport(jobId);
			if (jobReport != null) {
				PageDisplayVO vo = ConversionUtil.constructPageVo(jobReport);
				if (getReportType().equals("text")){
					vo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(jobId), textFactory.textParameters(jobReport.getTextReport())));
					vo.setOutputFormats(ConversionUtil.stringToArray(((EwcmsJobReport) jobReport).getOutputFormat()));
				}else if (getReportType().equals("chart")){
					vo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(jobId), chartFactory.chartParameters(jobReport.getChartReport())));
				}
				setPageDisplayVo(vo);
				setPageShowParams(vo.getPageShowParams());
				addActionMessage("数据保存成功!");
			}
		} catch (BaseException e) {
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
}
