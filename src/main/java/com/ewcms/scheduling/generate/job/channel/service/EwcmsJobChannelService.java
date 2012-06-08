/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.JobClassEntity;
import com.ewcms.scheduling.generate.job.channel.dao.EwcmsJobChannelDAO;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manager.SchedulingFacable;
import com.ewcms.scheduling.manager.dao.JobClassDAO;
import com.ewcms.scheduling.manager.dao.JobInfoDAO;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 频道定时任务Service
 * 
 * @author 吴智俊
 */
@Service
public class EwcmsJobChannelService implements EwcmsJobChannelServiceable {
	@Autowired
	private EwcmsJobChannelDAO ewcmsJobChannelDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private JobInfoDAO jobInfoDAO;
	@Autowired
	private JobClassDAO jobClassDAO;
	@Autowired
	private SchedulingFacable schedulingFac;
	
	@Override
	public Long saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException{
		Channel channel = channelDAO.get(channelId);
		if (channel != null) {
			JobInfo jobInfo = new JobInfo();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				jobInfo = jobInfoDAO.get(vo.getJobId());
			}
			
			if (jobInfo == null) {
				throw new BaseException("定时任务已经被删除,请重新操作!","定时任务已经被删除,请重新操作!");
			}
			
			jobInfo = ConversionUtil.constructJobInfoVo(jobInfo,vo);

			EwcmsJobChannel jobChannel = new EwcmsJobChannel();
			jobChannel.setSubChannel(isAppChildenChannel);
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0) {
				jobChannel.setId(vo.getJobId());
				jobChannel.setJobClass(jobInfo.getJobClass());
			}else{
				JobClass jobClass = jobClassDAO.findByJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
				if (jobClass == null) {
					jobClass = new JobClass();
					jobClass.setClassEntity(JobClassEntity.JOB_CHANNEL);
					jobClass.setClassName("频道定时器类");
					jobClass.setDescription("频道定时器类");
					jobClassDAO.persist(jobClass);
				}
				jobChannel.setJobClass(jobClass);
			}

			jobChannel.setDescription(jobInfo.getDescription());
			jobChannel.setLabel(jobInfo.getLabel());
			jobChannel.setNextFireTime(jobInfo.getNextFireTime());
			jobChannel.setOutputLocale(jobInfo.getOutputLocale());
			jobChannel.setPreviousFireTime(jobInfo.getPreviousFireTime());
			jobChannel.setState(jobInfo.getState());
			jobChannel.setTrigger(jobInfo.getTrigger());
			jobChannel.setUserName(jobInfo.getUserName());
			jobChannel.setVersion(jobInfo.getVersion());
			jobChannel.setChannel(channel);
			if (jobChannel.getId() == null) {
				return schedulingFac.saveScheduleJob(jobChannel);
			} else {
				return schedulingFac.updateScheduledJob(jobChannel);
			}
		}
		return null;
	}

	@Override
	public EwcmsJobChannel getScheduledJobChannel(Long jobId) {
		return (EwcmsJobChannel)ewcmsJobChannelDAO.get(jobId);
	}

	@Override
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId) {
		return ewcmsJobChannelDAO.findJobChannelByChannelId(channelId);
	}
	
	@Override
	public Channel findChannelByChannelId(Integer channelId) {
		return channelDAO.get(channelId);
	}

}
