/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job;

/**
 * 执行定时工作任务实体类路径
 * 
 * @author 吴智俊
 */
public class JobClassEntity {
	public static final String JOB_CHANNEL = "com.ewcms.scheduling.job.channel.EwcmsExecutionChannelJob";
	public static final String JOB_LEADINGWINDOW = "com.ewcms.scheduling.job.leadingwindow.EwcmsExecutionLeadingWindowJob";
}
