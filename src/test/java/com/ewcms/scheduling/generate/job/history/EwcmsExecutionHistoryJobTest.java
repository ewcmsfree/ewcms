/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.history;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ewcms.content.history.fac.HistoryModelFacable;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class EwcmsExecutionHistoryJobTest{

	@TestedObject
	private EwcmsExecutionHistoryJob ewcmsExecutionHistoryJob;
	
	private Mock<JobExecutionContext> jobExecutionContextMock;
	private Mock<Scheduler> schedulerMock;
	private Mock<SchedulerContext> schedulerContextMock;
	private Mock<ApplicationContext> applicationContextMock;
	private Mock<HistoryModelFacable> historyModelFacMock;
	
	@Test
	public void jobExecute() throws Exception {
		//初始化
		jobExecutionContextMock.returns(schedulerMock).getScheduler();
		schedulerMock.returns(schedulerContextMock).getContext();
		schedulerContextMock.returns(applicationContextMock).get(EwcmsExecutionHistoryJob.SCHEDULER_CONTEXT_KEY_APPLICATION_CONTEXT);
		applicationContextMock.returns(historyModelFacMock).getBean(EwcmsExecutionHistoryJob.HISTORY_MODEL_FAC);
		
		//执行
		ewcmsExecutionHistoryJob.execute(jobExecutionContextMock.getMock());
		
		//断意
		historyModelFacMock.assertInvoked().delHistoryModelBeforeDate();
	}

}
