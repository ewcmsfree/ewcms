/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.channel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.SchedulePublishFacable;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EwcmsExecutionChannelJobTest {
	
	@TestedObject
	private EwcmsExecutionChannelJob ewcmsExecutionChannelJob;
	
	private Mock<JobExecutionContext> jobExecutionContextMock;
	private Mock<Scheduler> schedulerMock;
	private Mock<SchedulerContext> schedulerContextMock;
	private Mock<ApplicationContext> applicationContextMock;
	private Mock<Trigger> triggerMock;
	private Mock<JobDataMap> jobDataMapMock;
	
	private Mock<SchedulePublishFacable> schedulePublishFacMock;
	private Mock<EwcmsJobChannelFacable> ewcmsJobChannelFacMock;
	private Mock<EwcmsJobChannel> ewcmsJobChannelMock;
	private Mock<Channel> channelMock;
	
	@Test
	public void jobExecute() throws Exception {
		jobExecutionContextMock.returns(schedulerMock).getScheduler();
		jobExecutionContextMock.returns(triggerMock).getTrigger();
		schedulerMock.returns(schedulerContextMock).getContext();
		schedulerContextMock.returns(applicationContextMock).get(EwcmsExecutionChannelJob.SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
		applicationContextMock.returns(schedulePublishFacMock).getBean(EwcmsExecutionChannelJob.SCHEDULE_PUBLISH_FAC);
		applicationContextMock.returns(ewcmsJobChannelFacMock).getBean(EwcmsExecutionChannelJob.JOB_CHANNEL_FAC);
		
		triggerMock.returns(jobDataMapMock).getJobDataMap();
		jobDataMapMock.returns(1L).getLong(EwcmsExecutionChannelJob.JOB_DATA_KEY_DETAILS_ID);
		ewcmsJobChannelFacMock.returns(ewcmsJobChannelMock).getScheduledJobChannel(1L);
		
		ewcmsJobChannelMock.returns(true).getSubChannel();
		ewcmsJobChannelMock.returns(channelMock).getChannel();
		
		channelMock.returns("test").getName();
		channelMock.returns(true).getPublicenable();
		
		schedulePublishFacMock.getMock().publishChannel(channelMock.getMock().getId(), true);
		
		ewcmsExecutionChannelJob.execute(jobExecutionContextMock.getMock());
	}
}
