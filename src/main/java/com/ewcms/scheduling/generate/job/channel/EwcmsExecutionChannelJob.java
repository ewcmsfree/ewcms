/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.SchedulePublishFacable;
import com.ewcms.scheduling.generate.job.BaseEwcmsExecutionJob;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;

/**
 * 执行频道定时发布工作任务
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionChannelJob extends BaseEwcmsExecutionJob {

    private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionChannelJob.class);
    
    private static final String JOB_CHANNEL_FACTORY = "ewcmsJobChannelFac";
    private static final String PUBLISH_FACTORY = "schedulePublishFac";

    protected EwcmsJobChannel jobDetails;
    
    protected void jobExecute() throws Exception {
        jobDetails = getJobDetails();
        Channel channel = jobDetails.getChannel();
        String channelName = "【" + channel.getName() + "】";
        
        Boolean subChannel = jobDetails.getSubChannel();
    	EwcmsJobChannelFacable ewcmsSchedulingFac = getEwcmsJobChannelFac();
    	EwcmsJobChannel jobChannel = ewcmsSchedulingFac.findJobChannelByChannelId(channel.getId());
    	if (jobChannel != null && jobChannel.getId().intValue() > 0){
    		if (channel.getPublicenable()){
				logger.info("定时发布 " + channelName + " 频道开始...");
				try{
					getSchedulePublishFacable().publishChannel(channel.getId(), subChannel);
				}catch (PublishException e){
					logger.error("定时发布 " + channelName + " 频道发布异常");
				}
				logger.info("定时发布 " + channelName + " 频道结束.");
    		}
    	}
    }
       
    protected void jobClear() {
        jobDetails = null;
    }

    protected EwcmsJobChannel getJobDetails() {
        EwcmsJobChannelFacable ewcmsJobChannelFac = getEwcmsJobChannelFac();
        JobDataMap jobDataMap = jobContext.getTrigger().getJobDataMap();

        int jobId = jobDataMap.getInt(JOB_DATA_KEY_DETAILS_ID);
        EwcmsJobChannel ewcmsJobChannel = ewcmsJobChannelFac.getScheduledJobChannel(jobId);
        return ewcmsJobChannel;
    }

    protected SchedulePublishFacable getSchedulePublishFacable(){
    	return (SchedulePublishFacable) applicationContext.getBean(PUBLISH_FACTORY);
    }
    
    protected EwcmsJobChannelFacable getEwcmsJobChannelFac() {
        return (EwcmsJobChannelFacable) applicationContext.getBean(JOB_CHANNEL_FACTORY);
    }
}
