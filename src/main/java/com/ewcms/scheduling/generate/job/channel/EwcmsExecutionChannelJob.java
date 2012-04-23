/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel;

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
    
    public static final String JOB_CHANNEL_FAC = "ewcmsJobChannelFac";
    public static final String SCHEDULE_PUBLISH_FAC = "schedulePublishFac";

    private EwcmsJobChannel ewcmsJobChannel;
    
    protected void jobExecute(Long jobId) throws Exception {
        ewcmsJobChannel = getEwcmsJobChannelFac().getScheduledJobChannel(jobId);
        if (ewcmsJobChannel != null){
        	Channel channel = ewcmsJobChannel.getChannel();
            if (channel != null && channel.getPublicenable()){
	            String channelName = "【" + channel.getName() + "】";
	            Boolean subChannel = ewcmsJobChannel.getSubChannel();
	            
				logger.info("定时发布 {} 频道开始...", channelName);
				try{
					getSchedulePublishFac().publishChannel(channel.getId(), subChannel);
				}catch (PublishException e){
					logger.error("定时发布 {} 频道发布异常", channelName);
				}
				logger.info("定时发布 {} 频道结束.", channelName);
            }
    	}
    }
       
    protected void jobClear() {
        ewcmsJobChannel = null;
    }

    private SchedulePublishFacable getSchedulePublishFac(){
    	return (SchedulePublishFacable) applicationContext.getBean(SCHEDULE_PUBLISH_FAC);
    }
    
    private EwcmsJobChannelFacable getEwcmsJobChannelFac() {
        return (EwcmsJobChannelFacable) applicationContext.getBean(JOB_CHANNEL_FAC);
    }
}
