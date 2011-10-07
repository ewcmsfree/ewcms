/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public class JobClassEntity {
	//频道执行JOB
	public static final String JOB_CHANNEL = "com.ewcms.scheduling.job.channel.EwcmsExecutionChannelJob";
	//采集器执行JOB
	public static final String JOB_CRAWLER = "com.ewcms.scheduling.job.crawler.EwcmsExecutionCrawlerJob";
}
