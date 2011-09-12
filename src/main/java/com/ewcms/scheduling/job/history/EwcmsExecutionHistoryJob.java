/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.history;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.history.fac.HistoryModelFacable;
import com.ewcms.scheduling.job.BaseEwcmsExecutionJob;

/**
 * 定时清除历史记录
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionHistoryJob extends BaseEwcmsExecutionJob {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionHistoryJob.class);
	
	private static final String SCHEDULER_FACTORY = "historyModelFac";
	
	@Override
	protected void jobExecute(JobExecutionContext context) throws JobExecutionException {
        try {
        	logger.info("定时清除历史记录开始...");
            getHistoryModelFac().delHistoryModelBeforeDate();
            logger.info("定时清除历史记录结束.");
        } catch (Exception e) {
        	logger.error("发生异常");
        	throw new JobExecutionException(e);
        } finally {
            this.clear();
        }
	}

	@Override
	protected void jobClear() {
	}

    protected HistoryModelFacable getHistoryModelFac() {
        return (HistoryModelFacable) applicationContext.getBean(SCHEDULER_FACTORY);
    }
}
