/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.crawler;

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

import com.ewcms.plugin.crawler.generate.EwcmsControllerable;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EwcmsExecutionCrawlerJobTest {

	@TestedObject
	private EwcmsExecutionCrawlerJob ewcmsExecutioinCrawlerJob;
	
	private Mock<JobExecutionContext> jobExecutionContextMock;
	private Mock<Scheduler> schedulerMock;
	private Mock<SchedulerContext> schedulerContextMock;
	private Mock<ApplicationContext> applicationContextMock;
	private Mock<Trigger> triggerMock;
	private Mock<JobDataMap> jobDataMapMock;

	private Mock<EwcmsJobCrawlerFacable> ewcmsJobCrawlerFacMock;
	private Mock<EwcmsControllerable> ewcmsControllerMock;
	private Mock<EwcmsJobCrawler> ewcmsJobCrawlerMock;
	private Mock<Gather> gatherMock;
	
	@Test
	public void jobExecute() throws Exception {
		jobExecutionContextMock.returns(schedulerMock).getScheduler();
		jobExecutionContextMock.returns(triggerMock).getTrigger();
		schedulerMock.returns(schedulerContextMock).getContext();
		schedulerContextMock.returns(applicationContextMock).get(EwcmsExecutionCrawlerJob.SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
		applicationContextMock.returns(ewcmsJobCrawlerFacMock).getBean(EwcmsExecutionCrawlerJob.JOB_CRAWLER_FACTORY);
		applicationContextMock.returns(ewcmsControllerMock).getBean(EwcmsExecutionCrawlerJob.CRAWL_FACTORY);
		
		triggerMock.returns(jobDataMapMock).getJobDataMap();
		jobDataMapMock.returns(1L).getLong(EwcmsExecutionCrawlerJob.JOB_DATA_KEY_DETAILS_ID);
		ewcmsJobCrawlerFacMock.returns(ewcmsJobCrawlerMock).getScheduledJobCrawler(1L);
		ewcmsJobCrawlerMock.returns(gatherMock).getGather();

		gatherMock.returns(true).getStatus();
		gatherMock.returns("test").getName();
		gatherMock.returns("http://localhost").getBaseURI();
		gatherMock.returns(1L).getId();
		
		ewcmsControllerMock.getMock().startCrawl(gatherMock.getMock().getId());
		
		ewcmsExecutioinCrawlerJob.execute(jobExecutionContextMock.getMock());
	}
}