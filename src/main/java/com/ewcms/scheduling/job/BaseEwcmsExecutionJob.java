/**
 * 创建日期 2009-10-22
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * 执行定时工作任务抽象类
 * 
 * @author 吴智俊
 */
public abstract class BaseEwcmsExecutionJob implements Job {
    private static final Log log = LogFactory.getLog(BaseEwcmsExecutionJob.class);
    
    public static final String SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT = "applicationContext";
    public static final String JOB_DATA_KEY_DETAILS_ID = "jobDetailsID";

    protected ApplicationContext applicationContext;
    protected JobExecutionContext jobContext;
    protected SchedulerContext schedulerContext;
    

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	log.info("定时器开始...");
        try {
            this.jobContext = context;
            this.schedulerContext = jobContext.getScheduler().getContext();

            this.applicationContext = (ApplicationContext) schedulerContext.get(SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
            
            alqcJobExecute(context);
        } catch (JobExecutionException e) {
        	throw new JobExecutionException(e);
        } catch (SchedulerException e) {
            throw new JobExecutionException(e);
        } 
        log.info("定时器结束.");
    }
    
    protected void clear() {
        jobContext = null;
        schedulerContext = null;
        alqcJobClear();
    }

    protected abstract void alqcJobExecute(JobExecutionContext context) throws JobExecutionException;

    protected abstract void alqcJobClear();
}
