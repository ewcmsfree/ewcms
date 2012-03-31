/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report;

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

import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EwcmsExecutionReportJobTest {
	
	@TestedObject
	private EwcmsExecutionReportJob ewcmsExecutionReportJob;
	
	private Mock<JobExecutionContext> jobExecutionContextMock;
	private Mock<Scheduler> schedulerMock;
	private Mock<SchedulerContext> schedulerContextMock;
	private Mock<ApplicationContext> applicationContextMock;
	private Mock<Trigger> triggerMock;
	private Mock<JobDataMap> jobDataMapMock;
	
	private Mock<EwcmsJobReportFacable> ewcmsJobReportFacMock;
	private Mock<ReportFacable> reportFacMock;
	private Mock<ChartFactoryable> chartFactoryMock;
	private Mock<TextFactoryable> textFactoryMock;
	private Mock<EwcmsJobReport> ewcmsJobReportMock;
	
	@Test
	public void jobExecute() throws Exception {
		jobExecutionContextMock.returns(schedulerMock).getScheduler();
		jobExecutionContextMock.returns(triggerMock).getTrigger();
		schedulerMock.returns(schedulerContextMock).getContext();
		schedulerContextMock.returns(applicationContextMock).get(EwcmsExecutionReportJob.SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
		applicationContextMock.returns(ewcmsJobReportFacMock).getBean(EwcmsExecutionReportJob.JOB_REPORT_FACTORY);
		applicationContextMock.returns(reportFacMock).getBean(EwcmsExecutionReportJob.REPORT_FACTORY);
		applicationContextMock.returns(chartFactoryMock).getBean(EwcmsExecutionReportJob.CHART_FACTORY);
		applicationContextMock.returns(textFactoryMock).getBean(EwcmsExecutionReportJob.TEXT_FACTORY);
		
		triggerMock.returns(jobDataMapMock).getJobDataMap();
		jobDataMapMock.returns(1L).getLong(EwcmsExecutionReportJob.JOB_DATA_KEY_DETAILS_ID);
		ewcmsJobReportFacMock.returns(ewcmsJobReportMock).getScheduledJobReport(null);
		reportFacMock.returns(1L).addRepository(null);
		
		ewcmsExecutionReportJob.execute(jobExecutionContextMock.getMock());
	}
}
