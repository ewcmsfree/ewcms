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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ewcms.scheduling.validator.JobInfoValidatorable;

/**
 * @author 吴智俊
 */
public class SchedulingFac implements SchedulingFacable, SchedulerListenerable, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(SchedulingFac.class);

	private JobInfoServiceable jobInfoService;
	private JobsQuartzSchedulerable scheduler;
	private JobInfoValidatorable validator;
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

	public JobInfoValidatorable getValidator() {
		return validator;
	}

	public void setValidator(JobInfoValidatorable validator) {
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
		JobInfo savedJob = jobInfoService.saveJob(jobInfo);
		scheduler.scheduleJob(savedJob);
		return savedJob.getId();
	}

	protected void validate(JobInfo jobInfo) throws BaseException {
		ValidationErrorsable errors = validator.validateJob(jobInfo);
		if (errors.isError()) {
			logger.debug("JobInfo Validate Error {}", errors);
			throw new ValidationException(errors);
		}
	}

	@Override
	public List<JobInfo> getScheduledJobs() throws BaseException {
		List<JobInfo> jobs = jobInfoService.findByAllJob();
		setRuntimeInformation(jobs);
		return removeDuplicateAndSort(jobs);
	}

	/*
	 * 剔除重复记录,并根据JobInfo对象的id进行排序
	 */
	protected List<JobInfo> removeDuplicateAndSort(List<JobInfo> list) {
		// 剔除重复记录
		HashSet<JobInfo> h = new HashSet<JobInfo>(list);
		list.clear();
		list.addAll(h);
		// 排序
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				JobInfo p1 = (JobInfo) o1;
				JobInfo p2 = (JobInfo) o2;
				if (p1.getId() > p2.getId())
					return 1;
				else
					return 0;
			}
		});
		return list;
	}

	protected void setRuntimeInformation(List<JobInfo> jobs) throws BaseException {
		if (jobs != null && !jobs.isEmpty()) {
			scheduler.getJobsRuntimeInformation(jobs);
		}
	}

	@Override
	public void deletedScheduledJob(Integer jobId) throws BaseException {
		deleteJob(jobId);
	}

	protected void unscheduleJobs(Integer[] deletedJobIds) throws BaseException {
		if (deletedJobIds != null && deletedJobIds.length > 0) {
			for (int i = 0; i < deletedJobIds.length; i++) {
				Integer jobId = deletedJobIds[i];
				scheduler.removeScheduledJob(jobId);
			}
		}
	}

	protected void deleteJob(Integer jobId) throws BaseException {
		scheduler.removeScheduledJob(jobId);
		jobInfoService.deletedJob(jobId);
	}

	@Override
	public JobInfo getScheduledJob(Integer jobId) {
		return jobInfoService.findByJob(jobId);
	}

	@Override
	public void jobFinalized(Integer jobId) throws BaseException {
		logger.info("任务 " + jobId + " 已完成,将删除数据");
		deleteJob(jobId);
	}

	@Override
	public Integer updateScheduledJob(JobInfo jobInfo) throws BaseException {
		validate(jobInfo);

		JobTrigger origTrigger = jobInfo.getTrigger();
		Integer origTriggerId = origTrigger.getId();
		Integer origTriggerVersion = origTrigger.getVersion();

		JobInfo savedJob = jobInfoService.updateJob(jobInfo);
		JobTrigger updatedTrigger = savedJob.getTrigger();

		if (updatedTrigger.getId() != origTriggerId || updatedTrigger.getVersion() != origTriggerVersion) {
			scheduler.rescheduleJob(savedJob);
		} else {
			logger.info("触发器属性没有改变 " + jobInfo.getId() + " 任务,任务将不会被改变");
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
	public JobClass findByJobClass(Integer id) throws BaseException {
		return (JobClass) jobClassService.findByJobClass(id);
	}

	@Override
	public List<JobClass> findByAllJobClass() throws BaseException {
		return (List<JobClass>) jobClassService.findByAllJobClass();
	}

	@Override
	public void deletedJobClass(Integer id) throws BaseException {
		jobClassService.deletedJobClass(id);
	}

	protected boolean hasTriggerErrors(ValidationErrorsable errors) {
		boolean triggerError = false;
		for (Iterator<ValidationErrorable> it = errors.getErrors().iterator(); !triggerError && it.hasNext();) {
			ValidationErrorable error = (ValidationErrorable) it.next();
			String field = error.getField();
			if (field != null && (field.equals("trigger") || field.startsWith("trigger."))) {
				triggerError = true;
			}
		}
		return triggerError;
	}

	@Override
	public JobClass findByJobClassByClassEntity(String classEntity) throws BaseException {
		return jobClassService.findByJobClassByClassEntity(classEntity);
	}

	@Override
	public void pauseJob(Integer id) throws BaseException {
		scheduler.pauseJob(id);
	}

	@Override
	public void resumedJob(Integer id) throws BaseException {
		scheduler.resumedJob(id);
	}
}
