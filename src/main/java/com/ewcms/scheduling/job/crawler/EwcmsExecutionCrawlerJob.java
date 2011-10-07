/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.crawler;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.model.Gather;
import com.ewcms.scheduling.job.BaseEwcmsExecutionJob;
import com.ewcms.scheduling.job.crawler.fac.EwcmsJobCrawlerFacable;
import com.ewcms.scheduling.job.crawler.model.EwcmsJobCrawler;

/**
 * 执行采集器定时采集工作任务
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionCrawlerJob extends BaseEwcmsExecutionJob {

    private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionCrawlerJob.class);
    
    private static final String SCHEDULER_FACTORY = "ewcmsJobCrawlerFac";

    protected EwcmsJobCrawler jobDetails;
    
    protected void jobExecute(JobExecutionContext context) throws JobExecutionException {
        try {
            excuteCrawler();
        } catch (JobExecutionException e) {
        	logger.error("工作任务异常");
        	throw new JobExecutionException(e);
        } catch (SchedulerException e) {
        	logger.error("定时器异常");
            throw new JobExecutionException(e);
        } catch (Exception e) {
        	logger.error("发生异常");
        	throw new JobExecutionException(e);
        } finally {
            this.clear();
        }
    }

    protected void excuteCrawler() throws Exception {
        jobDetails = getJobDetails();
        Gather basicInfo = jobDetails.getBasicInfo();
        String basicInfoName = "【" + basicInfo.getName() + "】";
        
    	EwcmsJobCrawlerFacable ewcmsSchedulingFac = getEwcmsSchedulingFac();
    	
    	if (basicInfo.getStatus()){
    		logger.info("定时采集 " + basicInfoName + " 地址开始...");
    		logger.info("定时采集 " + basicInfoName + " 地址结束.");
    	}
    }
       
    protected void jobClear() {
        jobDetails = null;
    }

    protected EwcmsJobCrawler getJobDetails() {
        EwcmsJobCrawlerFacable ewcmsSchedulingService = getEwcmsSchedulingFac();
        JobDataMap jobDataMap = jobContext.getTrigger().getJobDataMap();

        int jobId = jobDataMap.getInt(JOB_DATA_KEY_DETAILS_ID);
        EwcmsJobCrawler jobCrawler = ewcmsSchedulingService.getScheduledJobCrawler(jobId);
        return jobCrawler;
    }

    protected EwcmsJobCrawlerFacable getEwcmsSchedulingFac() {
        return (EwcmsJobCrawlerFacable) applicationContext.getBean(SCHEDULER_FACTORY);
    }
}
