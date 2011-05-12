/**
 * 创建日期 2009-4-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
import com.ewcms.scheduling.manage.AlqcValidationException;
import com.ewcms.scheduling.manage.service.AlqcJobClassServiceable;
import com.ewcms.scheduling.manage.service.AlqcJobServiceable;
import com.ewcms.scheduling.model.AlqcJob;
import com.ewcms.scheduling.model.AlqcJobClass;
import com.ewcms.scheduling.model.AlqcJobTrigger;
import com.ewcms.scheduling.quartz.AlqcJobsQuartzSchedulerable;
import com.ewcms.scheduling.quartz.AlqcSchedulerListenerable;
import com.ewcms.scheduling.validator.AlqcJobValidatorable;

/**
 * @author 吴智俊
 */
public class AlqcSchedulingFac implements AlqcSchedulingFacable, AlqcSchedulerListenerable, InitializingBean {

	private static final Log log = LogFactory.getLog(AlqcSchedulingFac.class);

	private AlqcJobServiceable alqcJobService;
	private AlqcJobsQuartzSchedulerable scheduler;
	private AlqcJobValidatorable validator;
	private AlqcJobClassServiceable alqcJobClassService;

	public AlqcJobServiceable getAlqcJobService() {
		return alqcJobService;
	}

	public void setAlqcJobService(AlqcJobServiceable alqcJobService) {
		this.alqcJobService = alqcJobService;
	}

	public AlqcJobsQuartzSchedulerable getScheduler() {
		return scheduler;
	}

	public void setScheduler(AlqcJobsQuartzSchedulerable scheduler) {
		this.scheduler = scheduler;
	}

	public AlqcJobValidatorable getValidator() {
		return validator;
	}

	public void setValidator(AlqcJobValidatorable validator) {
		this.validator = validator;
	}

	public AlqcJobClassServiceable getAlqcJobClassService() {
		return alqcJobClassService;
	}

	public void setAlqcJobClassService(AlqcJobClassServiceable alqcJobClassService) {
		this.alqcJobClassService = alqcJobClassService;
	}

	public void afterPropertiesSet() throws Exception {
		getScheduler().addAlqcSchedulerListener(this);
	}

	@Override
	public Integer saveScheduleJob(AlqcJob alqcJob) throws BaseException {
		validate(alqcJob);
		AlqcJob savedJob = alqcJobService.saveJob(alqcJob);
		scheduler.scheduleJob(savedJob);
		return savedJob.getId();
	}

	protected void validate(AlqcJob alqcJob) throws BaseException {
		ValidationErrorsable errors = validator.validateJob(alqcJob);
		if (errors.isError()) {
			throw new AlqcValidationException(errors);
			//throw new BaseException(errors.toString(),errors.toString());
		}
	}

	@Override
	public List<AlqcJob> getScheduledJobs() throws BaseException {
		List<AlqcJob> jobs = alqcJobService.findByAllJob();
		setRuntimeInformation(jobs);
		return removeDuplicateAndSort(jobs);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<AlqcJob> removeDuplicateAndSort(List<AlqcJob> list) {
		//剔除重复记录
		HashSet<AlqcJob> h = new HashSet<AlqcJob>(list);
		list.clear();
		list.addAll(h);
		//排序
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				AlqcJob p1 = (AlqcJob)o1;
				AlqcJob p2 = (AlqcJob)o2;
				if (p1.getId() > p2.getId())
					return 1;
				else 
					return 0;
			}
		});
		return list;
	}

	protected void setRuntimeInformation(List<AlqcJob> jobs)
			throws BaseException {
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
		alqcJobService.deletedJob(jobId);
	}

	@Override
	public AlqcJob getScheduledJob(Integer jobId) {
		return alqcJobService.findByJob(jobId);
	}

	@Override
	public void alqcJobFinalized(Integer jobId) throws BaseException {
		log.info("任务 " + jobId + " 已完成,将删除数据");
		deleteJob(jobId);
	}

	@Override
	public Integer updateScheduledJob(AlqcJob alqcJob) throws BaseException {
		validate(alqcJob);

		AlqcJobTrigger origTrigger = alqcJob.getTrigger();
		Integer origTriggerId = origTrigger.getId();
		Integer origTriggerVersion = origTrigger.getVersion();

		AlqcJob savedJob = alqcJobService.updateJob(alqcJob);
		AlqcJobTrigger updatedTrigger = savedJob.getTrigger();

		if (updatedTrigger.getId() != origTriggerId
				|| updatedTrigger.getVersion() != origTriggerVersion) {
			scheduler.rescheduleJob(savedJob);
		} else {
			log.info("触发器属性没有改变 " + alqcJob.getId() + " 任务,任务将不会被改变");
		}
		return alqcJob.getId();
	}

	@Override
	public ValidationErrorsable validateJob(AlqcJob alqcJob)
			throws BaseException {
		ValidationErrorsable errors = validator.validateJob(alqcJob);
		if (!hasTriggerErrors(errors)) {
			scheduler.validate(alqcJob, errors);
		}
		return errors;
	}

	@Override
	public Integer saveJobClass(AlqcJobClass alqcJobClass) throws BaseException {
		return alqcJobClassService.saveJobClass(alqcJobClass);
	}

	@Override
	public Integer updateJobClass(AlqcJobClass alqcJobClass) throws BaseException {
		return alqcJobClassService.updateJobClass(alqcJobClass);
	}

	@Override
	public AlqcJobClass findByJobClass(Integer id) throws BaseException {
		return (AlqcJobClass) alqcJobClassService.findByJobClass(id);
	}

	@Override
	public List<AlqcJobClass> findByAllJobClass() throws BaseException {
		return (List<AlqcJobClass>) alqcJobClassService.findByAllJobClass();
	}
	
	@Override
	public void deletedJobClass(Integer id) throws BaseException {
		alqcJobClassService.deletedJobClass(id);
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
	public AlqcJobClass findByAlqcJobClassByClassEntity(String classEntity)
			throws BaseException {
		return alqcJobClassService.findByAlqcJobClassByClassEntity(classEntity);
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
