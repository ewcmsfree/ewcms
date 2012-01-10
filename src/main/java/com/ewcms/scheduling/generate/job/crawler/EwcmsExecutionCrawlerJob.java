/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.crawler.generate.EwcmsControllerable;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.scheduling.generate.job.BaseEwcmsExecutionJob;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;

/**
 * 执行采集器定时采集工作任务
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionCrawlerJob extends BaseEwcmsExecutionJob {

    private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionCrawlerJob.class);
    
    private static final String JOB_CRAWLER_FACTORY = "ewcmsJobCrawlerFac";
    
    private static final String CRAWL_FACTORY = "ewcmsController";

    protected EwcmsJobCrawler jobDetails;
    
    protected void jobExecute() throws Exception {
        jobDetails = getJobDetails();
        Gather gather = jobDetails.getGather();
        String gather_name = "【" + gather.getName() + "】";
        
    	EwcmsJobCrawlerFacable ewcmsSchedulingFac = getEwcmsJobCrawlerFac();
    	ewcmsSchedulingFac.findJobCrawlerByGatherId(gather.getId());
    	if (gather.getStatus()){
    		logger.info("定时采集 " + gather_name + " 地址开始...");
    		getEwcmsController().startCrawl(gather.getId());
    		logger.info("定时采集 " + gather_name + " 地址结束.");
    	}
    }
       
    protected void jobClear() {
        jobDetails = null;
    }

    protected EwcmsJobCrawler getJobDetails() {
        EwcmsJobCrawlerFacable ewcmsJobCrawlerFac = getEwcmsJobCrawlerFac();
        JobDataMap jobDataMap = jobContext.getTrigger().getJobDataMap();

        int jobId = jobDataMap.getInt(JOB_DATA_KEY_DETAILS_ID);
        EwcmsJobCrawler jobCrawler = ewcmsJobCrawlerFac.getScheduledJobCrawler(jobId);
        return jobCrawler;
    }

    protected EwcmsJobCrawlerFacable getEwcmsJobCrawlerFac() {
        return (EwcmsJobCrawlerFacable) applicationContext.getBean(JOB_CRAWLER_FACTORY);
    }
    
    protected EwcmsControllerable getEwcmsController(){
    	return (EwcmsControllerable) applicationContext.getBean(CRAWL_FACTORY); 
    }
}
