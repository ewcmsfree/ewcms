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

	public static final String HISTORY_MODEL_FAC = "historyModelFac";

	protected void jobExecute(Long jobId) throws Exception {
		logger.info("定时清除历史记录开始...");
		getHistoryModelFac().delHistoryModelBeforeDate();
		logger.info("定时清除历史记录结束.");
	}

	protected void jobClear() {
	}

	private HistoryModelFacable getHistoryModelFac() {
		return (HistoryModelFacable) applicationContext.getBean(HISTORY_MODEL_FAC);
	}
}
