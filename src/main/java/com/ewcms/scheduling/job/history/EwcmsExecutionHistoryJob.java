/**
 * 创建日期 2011-4-8
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.history;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ewcms.aspect.history.fac.HistoryModelFacable;
import com.ewcms.scheduling.job.BaseEwcmsExecutionJob;

/**
 * 定时清除历史记录
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionHistoryJob extends BaseEwcmsExecutionJob {

	private static final Log log = LogFactory.getLog(EwcmsExecutionHistoryJob.class);
	
	private static final String SCHEDULER_FACTORY = "historyModelFac";
	
	@Override
	protected void alqcJobExecute(JobExecutionContext context) throws JobExecutionException {
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
	protected void alqcJobClear() {
	}

    protected HistoryModelFacable getHistoryModelFac() {
        return (HistoryModelFacable) applicationContext.getBean(SCHEDULER_FACTORY);
    }

}
