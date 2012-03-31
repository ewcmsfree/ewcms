/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.quartz;

import java.lang.reflect.Method;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @author wuzhijun
 *
 */
public abstract class EwcmsQuartzJobBean implements Job {

	private static final Method getSchedulerMethod;
	private static final Method getMergedJobDataMapMethod;

	static {
		try {
			getSchedulerMethod = org.quartz.impl.JobExecutionContextImpl.class.getMethod("getScheduler", new Class[0]);
			getMergedJobDataMapMethod = org.quartz.impl.JobExecutionContextImpl.class.getMethod("getMergedJobDataMap", new Class[0]);
		} catch (NoSuchMethodException ex) {
			throw new IllegalStateException((new StringBuilder("Incompatible Quartz API: ")).append(ex).toString());
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Scheduler scheduler = (Scheduler) ReflectionUtils.invokeMethod(getSchedulerMethod, context);
			Map mergedJobDataMap = (Map) ReflectionUtils.invokeMethod(getMergedJobDataMapMethod, context);
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
			MutablePropertyValues pvs = new MutablePropertyValues();
			pvs.addPropertyValues(scheduler.getContext());
			pvs.addPropertyValues(mergedJobDataMap);
			bw.setPropertyValues(pvs, true);
		} catch (SchedulerException ex) {
			throw new JobExecutionException(ex);
		}
		executeInternal(context);
	}
	
	protected abstract void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException;

}
