/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler;

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
    
    public static final String JOB_CRAWLER_FACTORY = "ewcmsJobCrawlerFac";
    public static final String CRAWL_FACTORY = "ewcmsController";

    private EwcmsJobCrawler ewcmsJobCrawler;
    
    protected void jobExecute(Long jobId) throws Exception {
        ewcmsJobCrawler = getEwcmsJobCrawlerFac().getScheduledJobCrawler(jobId);
        if (ewcmsJobCrawler != null){
	        Gather gather = ewcmsJobCrawler.getGather();
	    	if (gather != null && gather.getStatus()){
	    		String gatherName = "【" + gather.getName() + " - " + gather.getBaseURI() + "】";
	    		
	    		logger.info("定时采集 {} 地址开始...", gatherName);
	    		getEwcmsController().startCrawl(gather.getId());
	    		logger.info("定时采集 {} 地址结束.", gatherName);
	    	}
        }
    }
       
    protected void jobClear() {
        ewcmsJobCrawler = null;
    }

    private EwcmsJobCrawlerFacable getEwcmsJobCrawlerFac() {
        return (EwcmsJobCrawlerFacable) applicationContext.getBean(JOB_CRAWLER_FACTORY);
    }
    
    private EwcmsControllerable getEwcmsController(){
    	return (EwcmsControllerable) applicationContext.getBean(CRAWL_FACTORY); 
    }
}
