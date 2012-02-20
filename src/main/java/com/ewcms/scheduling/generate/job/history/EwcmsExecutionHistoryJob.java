/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.history;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.history.fac.HistoryModelFacable;
import com.ewcms.scheduling.generate.job.BaseEwcmsExecutionJob;

/**
 * 定时清除历史记录
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionHistoryJob extends BaseEwcmsExecutionJob {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionHistoryJob.class);

	private static final String SCHEDULER_FACTORY = "historyModelFac";

	@Override
	protected void jobExecute() throws Exception {
		logger.info("定时清除历史记录开始...");
		getHistoryModelFac().delHistoryModelBeforeDate();
		logger.info("定时清除历史记录结束.");
	}

	@Override
	protected void jobClear() {
	}

	protected HistoryModelFacable getHistoryModelFac() {
		return (HistoryModelFacable) applicationContext.getBean(SCHEDULER_FACTORY);
	}
}
