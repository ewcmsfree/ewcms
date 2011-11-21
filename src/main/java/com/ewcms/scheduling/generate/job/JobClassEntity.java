/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public class JobClassEntity {
	//频道执行JOB
	public static final String JOB_CHANNEL = "com.ewcms.scheduling.generate.job.channel.EwcmsExecutionChannelJob";
	//采集器执行JOB
	public static final String JOB_CRAWLER = "com.ewcms.scheduling.generate.job.crawler.EwcmsExecutionCrawlerJob";
	//报表执行JOB
	public static final String JOB_REPORT = "com.ewcms.scheduling.generate.job.report.EwcmsExecutionReportJob";
}
