/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.history;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ewcms.history.fac.HistoryModelFacable;
import com.ewcms.scheduling.job.BaseExecutionJob;

/**
 * 定时清除历史记录
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionHistoryJob extends BaseExecutionJob {

	private static final Log log = LogFactory.getLog(EwcmsExecutionHistoryJob.class);
	
	private static final String SCHEDULER_FACTORY = "historyModelFac";
	
	@Override
	protected void jobExecute(JobExecutionContext context) throws JobExecutionException {
        try {
        	log.info("定时清除历史记录开始...");
            getHistoryModelFac().delHistoryModelBeforeDate();
            log.info("定时清除历史记录结束.");
        } catch (Exception e) {
        	log.error("发生异常");
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
