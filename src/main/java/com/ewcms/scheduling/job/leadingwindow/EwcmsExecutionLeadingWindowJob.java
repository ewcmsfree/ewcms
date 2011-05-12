/**
 * 创建日期 2011-4-27
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.leadingwindow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ewcms.plugin.leadingwindow.LeadingWindowFacable;
import com.ewcms.scheduling.job.BaseEwcmsExecutionJob;

/**
 * @author 吴智俊
 */
public class EwcmsExecutionLeadingWindowJob extends BaseEwcmsExecutionJob {

	private static final Log log = LogFactory.getLog(EwcmsExecutionLeadingWindowJob.class);

	private static final String PUBLISH_LEADINGWINDOW_FACTORY = "leadingWindowFac";
	
	@Override
	protected void alqcJobExecute(JobExecutionContext context) throws JobExecutionException {
	    try {
	    	getLeadingReleaseable().pubPosition(309);
	    } catch (Exception e) {
	    	log.error("发生异常");
	    	throw new JobExecutionException(e);
	    } finally {
	        this.clear();
	    }
	}

	@Override
	protected void alqcJobClear() {
	}
	
    protected LeadingWindowFacable getLeadingReleaseable(){
    	return (LeadingWindowFacable) applicationContext.getBean(PUBLISH_LEADINGWINDOW_FACTORY);
    }

}
