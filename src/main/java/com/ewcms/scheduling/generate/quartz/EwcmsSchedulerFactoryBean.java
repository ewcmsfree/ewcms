/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.ThreadExecutor;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author wuzhijun
 */
public class EwcmsSchedulerFactoryBean extends SchedulerFactoryBean {

    private static final Logger logger = LoggerFactory.getLogger(EwcmsSchedulerFactoryBean.class);
	
	private ThreadExecutor ThreadExecutor;
	
	public EwcmsSchedulerFactoryBean() {
		setSchedulerFactoryClass(EwcmsSchedulerFactory.class);
	}
	
	protected Scheduler createScheduler(SchedulerFactory schedulerFactory, String schedulerName) throws SchedulerException {
		try {
			if (ThreadExecutor != null) {
				if (!(schedulerFactory instanceof EwcmsSchedulerFactory)) {
					logger.error("A EwcmsSchedulerFactory scheduler factory is required");
					throw new RuntimeException("A EwcmsSchedulerFactory scheduler factory is required");
				}
				
				LocalThreadExecutor.setLocalThreadExecutor(ThreadExecutor);
				
				EwcmsSchedulerFactory factory = (EwcmsSchedulerFactory) schedulerFactory;
				factory.getInitProps().setProperty(StdSchedulerFactory.PROP_THREAD_EXECUTOR_CLASS, LocalThreadExecutor.class.getName());
                //factory.getInitProps().setProperty(StdSchedulerFactory.PROP_THREAD_RUNNER_CLASS, LocalThreadExecutor.class.getName());
				factory.reinit();
			}
			
			return super.createScheduler(schedulerFactory, schedulerName);
		} finally {
			if (ThreadExecutor != null) {
				LocalThreadExecutor.setLocalThreadExecutor(null);
			}
		}
	}

	public ThreadExecutor getThreadExecutor() {
		return ThreadExecutor;
	}

	public void setThreadExecutor(ThreadExecutor ThreadExecutor) {
		if (ThreadExecutor instanceof NullThreadExecutor) {
			ThreadExecutor = null;
		}
			
		this.ThreadExecutor = ThreadExecutor;
	}

    //Spring 3:  java.util.concurrent.Executor
	public void setTaskExecutor(Executor taskExecutor) {
		if (taskExecutor instanceof NullTaskExecutor) {
			taskExecutor = null;
		}
		
		super.setTaskExecutor(taskExecutor);
	}
	
    /*
    //Spring 2:  org.springframework.core.task.TaskExecutor
    public void setTaskExecutor(TaskExecutor taskExecutor) {
		if (taskExecutor instanceof NullTaskExecutor) {
			taskExecutor = null;
		}

		super.setTaskExecutor(taskExecutor);
	}
	*/

}
