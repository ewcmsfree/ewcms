/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.channel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.GeneratorServiceable;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.scheduling.job.BaseEwcmsExecutionJob;
import com.ewcms.scheduling.job.channel.fac.EwcmsJobChannelFacable;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;

/**
 * 执行频道定时发布工作任务
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionChannelJob extends BaseEwcmsExecutionJob {

    private static final Log log = LogFactory.getLog(EwcmsExecutionChannelJob.class);
    
    private static final String SCHEDULER_FACTORY = "ewcmsJobChannelFac";
    private static final String PUBLISH_CHANNEL_FACTORY = "generatorService";

    protected EwcmsJobChannel jobDetails;
    
    protected void alqcJobExecute(JobExecutionContext context) throws JobExecutionException {
        try {
            excutePublic();
        } catch (JobExecutionException e) {
        	log.error("工作任务异常");
        	throw new JobExecutionException(e);
        } catch (SchedulerException e) {
        	log.error("定时器异常");
            throw new JobExecutionException(e);
        } catch (Exception e) {
        	log.error("发生异常");
        	throw new JobExecutionException(e);
        } finally {
            this.clear();
        }
    }

    protected void excutePublic() throws Exception {
        jobDetails = getJobDetails();
        Channel channel = jobDetails.getChannel();
        String channelName = "【" + channel.getName() + "】";
        
        Boolean subChannel = jobDetails.getSubChannel();
    	EwcmsJobChannelFacable ewcmsSchedulingFac = getEwcmsSchedulingFac();
    	EwcmsJobChannel jobChannel = ewcmsSchedulingFac.findJobChannelByChannelId(channel.getId());
    	if (jobChannel != null && jobChannel.getId().intValue() > 0){
			List<Channel> childens = ewcmsSchedulingFac.findChildenChannelByParentChannelId(channel.getId());
			if (subChannel && childens != null){
				subPublic(childens);
			}
    		if (channel.getPublicenable()){
				log.info("定时发布 " + channelName + " 频道开始...");
				try{
					getGeneratorServiceable().generator(channel.getId());
				}catch(ReleaseException e){
					log.error("定时发布 " + channelName + " 频道发布异常");
    			}
				log.info("定时发布 " + channelName + " 频道结束.");
    		}
    	}
    }
       
    private void subPublic(List<Channel> channels) throws Exception {
    	EwcmsJobChannelFacable ewcmsSchedulingFac = getEwcmsSchedulingFac();
    	for (Channel channel : channels){
    		if (channel != null && channel.getId().intValue() > 0){
		    	List<Channel> childens = ewcmsSchedulingFac.findChildenChannelByParentChannelId(channel.getId());
		    	if (childens != null){
			    	subPublic(childens);
		    	}
    			if (channel.getPublicenable()){
			    	EwcmsJobChannel jobChannel = ewcmsSchedulingFac.findJobChannelByChannelId(channel.getId());
			    	if (jobChannel == null){
			    		String channelName = "【" + channel.getName() + "】";
			    		log.info("定时发布 " + channelName + " 频道开始...");
						try{
							getGeneratorServiceable().generator(channel.getId());
						}catch(ReleaseException e){
							log.error("定时发布 " + channelName + " 频道发布异常");
		    			}
			    		log.info("定时发布 " + channelName + " 频道结束.");
			    	}
    			}
    		}
    	}
    }

    protected void alqcJobClear() {
        jobDetails = null;
    }

    protected EwcmsJobChannel getJobDetails() {
        EwcmsJobChannelFacable ewcmsSchedulingService = getEwcmsSchedulingFac();
        JobDataMap jobDataMap = jobContext.getTrigger().getJobDataMap();

        int jobId = jobDataMap.getInt(JOB_DATA_KEY_DETAILS_ID);
        EwcmsJobChannel jobChannel = ewcmsSchedulingService.getScheduledJobChannel(jobId);
        return jobChannel;
    }

    protected GeneratorServiceable getGeneratorServiceable(){
    	return (GeneratorServiceable) applicationContext.getBean(PUBLISH_CHANNEL_FACTORY);
    }
    
    protected EwcmsJobChannelFacable getEwcmsSchedulingFac() {
        return (EwcmsJobChannelFacable) applicationContext.getBean(SCHEDULER_FACTORY);
    }
}
