/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.JobClassEntity;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.generate.job.report.EwcmsJobReportFacable;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;
import com.ewcms.scheduling.manager.SchedulingFacable;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.web.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
public class JobInfoAction extends CrudBaseAction<PageDisplayVO, Integer> {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private SchedulingFacable schedulingFac;
	@Autowired
	private EwcmsJobReportFacable ewcmsJobReportFac;
	@Autowired
	private TextFactoryable textFactory;
	@Autowired
	private ChartFactoryable chartFactory;
	
	private Boolean subChannel;
	private List<PageShowParam> pageShowParams = new ArrayList<PageShowParam>();

	public PageDisplayVO getPageDisplayVo(){
		return super.getVo();
	}
	
	public void setPageDisplayVo(PageDisplayVO pageDisplayVo){
		super.setVo(pageDisplayVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}

	public List<PageShowParam> getPageShowParams() {
		return pageShowParams;
	}

	public void setPageShowParams(List<PageShowParam> pageShowParams) {
		this.pageShowParams = pageShowParams;
	}

	@Override
	protected PageDisplayVO createEmptyVo() {
		return new PageDisplayVO();
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try {
			schedulingFac.deletedScheduledJob(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
		}
	}

	@Override
	protected PageDisplayVO getOperator(Integer pk) {
		PageDisplayVO pageDisplayVo = new PageDisplayVO();
		try {
			JobInfo jobInfo = schedulingFac.getScheduledJob(pk);
			pageDisplayVo = ConversionUtil.constructPageVo(jobInfo);
			
			pageDisplayVo.setIsJobChannel(false);
			pageDisplayVo.setIsJobReport(false);
			pageDisplayVo.setIsJobCrawler(false);
			
			if (jobInfo instanceof EwcmsJobChannel){
				setSubChannel(((EwcmsJobChannel)jobInfo).getSubChannel());
				pageDisplayVo.setIsJobChannel(true);
			}else if (jobInfo instanceof EwcmsJobReport){
				TextReport textReport = ((EwcmsJobReport) jobInfo).getTextReport();
				ChartReport chartReport = ((EwcmsJobReport) jobInfo).getChartReport();
				pageDisplayVo = ConversionUtil.constructPageVo((EwcmsJobReport) jobInfo);
				if (textReport != null) {
					pageDisplayVo.setReportId(textReport.getId());
					pageDisplayVo.setReportName(textReport.getName());
					pageDisplayVo.setReportType("text");
					pageDisplayVo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(jobInfo.getId()), textFactory.textParameters(textReport)));
					pageDisplayVo.setOutputFormats(ConversionUtil.stringToArray(((EwcmsJobReport) jobInfo).getOutputFormat()));
				} else if (chartReport != null) {
					pageDisplayVo.setReportId(chartReport.getId());
					pageDisplayVo.setReportName(chartReport.getName());
					pageDisplayVo.setReportType("chart");
					pageDisplayVo.setPageShowParams(ConversionUtil.coversionParameterFromPage(ewcmsJobReportFac.findByJobReportParameterById(jobInfo.getId()), chartFactory.chartParameters(chartReport)));
				}
				pageDisplayVo.setIsJobReport(true);
				
				setPageDisplayVo(pageDisplayVo);
				setPageShowParams(pageDisplayVo.getPageShowParams());
			}else if (jobInfo instanceof EwcmsJobCrawler){
				pageDisplayVo.setIsJobCrawler(true);
			}
		} catch (BaseException e) {
		}
		return pageDisplayVo;
	}

	@Override
	protected Integer getPK(PageDisplayVO vo) {
		return vo.getJobId();
	}

	@Override
	protected Integer saveOperator(PageDisplayVO vo, boolean isUpdate) {
//		AlqcJob alqcJob = new AlqcJob();
//		try{
//			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
//				alqcJob = alqcSchedulingFac.getScheduledJob(vo.getJobId());
//			}
//			if (vo.getJobClassId() != null && vo.getJobClassId().intValue() > 0){
//				AlqcJobClass alqcJobClass = alqcSchedulingFac.findByJobClass(vo.getJobClassId());
//				alqcJob.setJobClass(alqcJobClass);
//			}
//			if (isUpdate) {
//				return alqcSchedulingFac.updateScheduledJob(ConversionUtil.constructAlqcJobVo(alqcJob,vo));
//			}else{
//				return alqcSchedulingFac.saveScheduleJob(ConversionUtil.constructAlqcJobVo(alqcJob, vo));
//			}
//		}catch(BaseException e){
//			this.addActionMessage(e.getPageMessage());
//			return null;
//		}
		return null;
	}
	
	@Override
	public String save() throws Exception{
		JobInfo jobInfo = new JobInfo();
		try{
			if (getPageDisplayVo().getJobId() != null && getPageDisplayVo().getJobId().intValue() > 0){
				jobInfo = schedulingFac.getScheduledJob(getPageDisplayVo().getJobId());
			}
			if (getPageDisplayVo().getJobClassId() != null && getPageDisplayVo().getJobClassId().intValue() > 0){
				JobClass alqcJobClass = schedulingFac.findByJobClass(getPageDisplayVo().getJobClassId());
				jobInfo.setJobClass(alqcJobClass);
			}
			jobInfo = ConversionUtil.constructAlqcJobVo(jobInfo, getPageDisplayVo());
			
			if (isUpdateOperator()) {
				operatorState = OperatorState.UPDATE;
			    schedulingFac.updateScheduledJob(jobInfo);
			    operatorPK.remove(0);
			    if (!operatorPK.isEmpty()){
			    	setPageDisplayVo(getOperator(operatorPK.get(0)));
			    }
			} else {
				operatorState = OperatorState.ADD;
	            Integer id = schedulingFac.saveScheduleJob(jobInfo);
	            operatorPK.add(id);
	            setPageDisplayVo(createEmptyVo());
			}
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}finally{
			getPageDisplayVo().setIsJobChannel(false);
			getPageDisplayVo().setIsJobReport(false);
			getPageDisplayVo().setIsJobCrawler(false);
			if (jobInfo instanceof EwcmsJobChannel){
				setSubChannel(((EwcmsJobChannel)jobInfo).getSubChannel());
				getPageDisplayVo().setIsJobChannel(true);
			}else if (jobInfo instanceof EwcmsJobReport){
				getPageDisplayVo().setIsJobReport(true);
			}else if (jobInfo instanceof EwcmsJobCrawler){
				getPageDisplayVo().setIsJobCrawler(true);
			}
		}
		return SUCCESS;
	}
	
	public List<JobClass> getAllJobClassList() {
		List<JobClass> jobClasses = new ArrayList<JobClass>();
		try {
			jobClasses = schedulingFac.findByAllJobClass();
			if (!getPageDisplayVo().getIsJobChannel()){
				JobClass jobClass = schedulingFac.findByJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
				jobClasses.remove(jobClass);
			}
			if (!getPageDisplayVo().getIsJobReport()){
				JobClass jobClass = schedulingFac.findByJobClassByClassEntity(JobClassEntity.JOB_REPORT);
				jobClasses.remove(jobClass);
			}
			if (!getPageDisplayVo().getIsJobCrawler()){
				JobClass jobClass = schedulingFac.findByJobClassByClassEntity(JobClassEntity.JOB_CRAWLER);
				jobClasses.remove(jobClass);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return jobClasses;
	}
	
	private Integer jobId;
	
	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String pauseJob(){
		try {
			schedulingFac.pauseJob(getJobId());
		} catch (BaseException e) {
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
	
	public String resumedJob(){
		try{
			schedulingFac.resumedJob(getJobId());
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
}
