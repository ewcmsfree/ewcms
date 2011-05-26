/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.fac;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorable;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.manage.ValidationException;
import com.ewcms.scheduling.manage.service.JobClassServiceable;
import com.ewcms.scheduling.manage.service.JobInfoServiceable;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobTrigger;
import com.ewcms.scheduling.quartz.JobsQuartzSchedulerable;
import com.ewcms.scheduling.quartz.SchedulerListenerable;
import com.ewcms.scheduling.validator.JobValidatorable;

/**
 * @author 吴智俊
 */
public class SchedulingFac implements SchedulingFacable, SchedulerListenerable, InitializingBean {

	private static final Log log = LogFactory.getLog(SchedulingFac.class);

	private JobInfoServiceable jobInfoService;
	private JobsQuartzSchedulerable scheduler;
	private JobValidatorable validator;
	private JobClassServiceable jobClassService;

	public JobInfoServiceable getJobInfoService() {
		return jobInfoService;
	}

	public void setJobInfoService(JobInfoServiceable jobInfoService) {
		this.jobInfoService = jobInfoService;
	}

	public JobsQuartzSchedulerable getScheduler() {
		return scheduler;
	}

	public void setScheduler(JobsQuartzSchedulerable scheduler) {
		this.scheduler = scheduler;
	}

	public JobValidatorable getValidator() {
		return validator;
	}

	public void setValidator(JobValidatorable validator) {
		this.validator = validator;
	}

	public JobClassServiceable getJobClassService() {
		return jobClassService;
	}

	public void setJobClassService(JobClassServiceable jobClassService) {
		this.jobClassService = jobClassService;
	}

	public void afterPropertiesSet() throws Exception {
		getScheduler().addSchedulerListener(this);
	}

	@Override
	public Integer saveScheduleJob(JobInfo jobInfo) throws BaseException {
		validate(jobInfo);
		JobInfo savedJob = jobInfoService.saveJobInfo(jobInfo);
		scheduler.scheduleJob(savedJob);
		return savedJob.getId();
	}

	protected void validate(JobInfo jobInfo) throws BaseException {
		ValidationErrorsable errors = validator.validateJob(jobInfo);
		if (errors.isError()) {
			throw new ValidationException(errors);
			//throw new BaseException(errors.toString(),errors.toString());
		}
	}

	@Override
	public List<JobInfo> getScheduledJobs() throws BaseException {
		List<JobInfo> jobInfos = jobInfoService.findJobInfoAll();
		setRuntimeInformation(jobInfos);
		return removeDuplicateAndSort(jobInfos);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<JobInfo> removeDuplicateAndSort(List<JobInfo> jobInfos) {
		//剔除重复记录
		HashSet<JobInfo> h = new HashSet<JobInfo>(jobInfos);
		jobInfos.clear();
		jobInfos.addAll(h);
		//排序
		Collections.sort(jobInfos, new Comparator() {
			public int compare(Object o1, Object o2) {
				JobInfo p1 = (JobInfo)o1;
				JobInfo p2 = (JobInfo)o2;
				if (p1.getId() > p2.getId())
					return 1;
				else 
					return 0;
			}
		});
		return jobInfos;
	}

	protected void setRuntimeInformation(List<JobInfo> jobInfos) throws BaseException {
		if (jobInfos != null && !jobInfos.isEmpty()) {
			scheduler.getJobsRuntimeInformation(jobInfos);
		}
	}

	@Override
	public void deletedScheduledJob(Integer jobInfoId) throws BaseException {
		deleteJob(jobInfoId);
	}

	protected void unscheduleJobs(Integer[] deletedJobIds) throws BaseException {
		if (deletedJobIds != null && deletedJobIds.length > 0) {
			for (int i = 0; i < deletedJobIds.length; i++) {
				Integer jobInfoId = deletedJobIds[i];
				scheduler.removeScheduledJob(jobInfoId);
			}
		}
	}

	protected void deleteJob(Integer jobInfoId) throws BaseException {
		scheduler.removeScheduledJob(jobInfoId);
		jobInfoService.deletedJobInfo(jobInfoId);
	}

	@Override
	public JobInfo getScheduledJob(Integer jobInfoId) {
		return jobInfoService.findJobInfoById(jobInfoId);
	}

	@Override
	public void jobFinalized(Integer jobInfoId) throws BaseException {
		log.info("任务 " + jobInfoId + " 已完成,将删除数据");
		deleteJob(jobInfoId);
	}

	@Override
	public Integer updateScheduledJob(JobInfo jobInfo) throws BaseException {
		validate(jobInfo);

		JobTrigger origTrigger = jobInfo.getTrigger();
		Integer origTriggerId = origTrigger.getId();
		Integer origTriggerVersion = origTrigger.getVersion();

		JobInfo savedJob = jobInfoService.updateJobInfo(jobInfo);
		JobTrigger updatedTrigger = savedJob.getTrigger();

		if (updatedTrigger.getId() != origTriggerId	|| updatedTrigger.getVersion() != origTriggerVersion) {
			scheduler.rescheduleJob(savedJob);
		} else {
			log.info("触发器属性没有改变 " + jobInfo.getId() + " 任务,任务将不会被改变");
		}
		return jobInfo.getId();
	}

	@Override
	public ValidationErrorsable validateJob(JobInfo jobInfo) throws BaseException {
		ValidationErrorsable errors = validator.validateJob(jobInfo);
		if (!hasTriggerErrors(errors)) {
			scheduler.validate(jobInfo, errors);
		}
		return errors;
	}

	@Override
	public Integer saveJobClass(JobClass jobClass) throws BaseException {
		return jobClassService.saveJobClass(jobClass);
	}

	@Override
	public Integer updateJobClass(JobClass jobClass) throws BaseException {
		return jobClassService.updateJobClass(jobClass);
	}

	@Override
	public JobClass findJobClassById(Integer jobClassId) throws BaseException {
		return (JobClass) jobClassService.findJobClassById(jobClassId);
	}

	@Override
	public List<JobClass> findJobClassAll() throws BaseException {
		return (List<JobClass>) jobClassService.findJobClassAll();
	}
	
	@Override
	public void deletedJobClass(Integer jobClassId) throws BaseException {
		jobClassService.deletedJobClass(jobClassId);
	}

	protected boolean hasTriggerErrors(ValidationErrorsable errors) {
		boolean triggerError = false;
		for (Iterator<ValidationErrorable> it = errors.getErrors().iterator(); !triggerError
				&& it.hasNext();) {
			ValidationErrorable error = (ValidationErrorable) it.next();
			String field = error.getField();
			if (field != null
					&& (field.equals("trigger") || field.startsWith("trigger."))) {
				triggerError = true;
			}
		}
		return triggerError;
	}
	
	@Override
	public JobClass findJobClassByClassEntity(String classEntity) throws BaseException {
		return jobClassService.findJobClassByClassEntity(classEntity);
	}

	@Override
	public void pauseJob(Integer jobInfoId) throws BaseException {
		scheduler.pauseJob(jobInfoId);
	}

	@Override
	public void resumedJob(Integer jobInfoId) throws BaseException {
		scheduler.resumedJob(jobInfoId);
	}
}
