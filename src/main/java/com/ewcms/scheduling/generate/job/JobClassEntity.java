/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job;

import com.ewcms.scheduling.generate.job.channel.EwcmsExecutionChannelJob;
import com.ewcms.scheduling.generate.job.crawler.EwcmsExecutionCrawlerJob;
import com.ewcms.scheduling.generate.job.report.EwcmsExecutionReportJob;
import com.ewcms.scheduling.generate.job.trs.EwcmsExecutionTrsJob;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public final class JobClassEntity {
	//频道执行JOB
	public static final String JOB_CHANNEL = EwcmsExecutionChannelJob.class.getName().toString();
	//采集器执行JOB
	public static final String JOB_CRAWLER = EwcmsExecutionCrawlerJob.class.getName().toString();
	//报表执行JOB
	public static final String JOB_REPORT = EwcmsExecutionReportJob.class.getName().toString();
	//TRS
	public static final String JOB_TRS = EwcmsExecutionTrsJob.class.getName().toString();
}
