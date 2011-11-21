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
	public Integer saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException{
		Channel channel = channelDAO.get(channelId);
		if (channel != null) {
			JobInfo alqcJob = new JobInfo();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				alqcJob = jobInfoDAO.get(vo.getJobId());
			}
			
			if (alqcJob == null) {
				throw new BaseException("定时任务已经被删除,请重新操作!","定时任务已经被删除,请重新操作!");
			}
			
			alqcJob = ConversionUtil.constructAlqcJobVo(alqcJob,vo);

			EwcmsJobChannel jobChannel = new EwcmsJobChannel();
			jobChannel.setSubChannel(isAppChildenChannel);
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0) {
				jobChannel.setId(vo.getJobId());
				jobChannel.setJobClass(alqcJob.getJobClass());
			}else{
				JobClass alqcJobClass = null;
				alqcJobClass = jobClassDAO.findByJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
				if (alqcJobClass.getId() == null) {
					alqcJobClass.setClassEntity(JobClassEntity.JOB_CHANNEL);
					alqcJobClass.setClassName("频道定时器类");
					alqcJobClass.setDescription("频道定时器类");
					jobClassDAO.persist(alqcJobClass);
				}
				jobChannel.setJobClass(alqcJobClass);
			}

			jobChannel.setDescription(alqcJob.getDescription());
			jobChannel.setLabel(alqcJob.getLabel());
			jobChannel.setNextFireTime(alqcJob.getNextFireTime());
			jobChannel.setOutputLocale(alqcJob.getOutputLocale());
			jobChannel.setPreviousFireTime(alqcJob.getPreviousFireTime());
			jobChannel.setState(alqcJob.getState());
			jobChannel.setTrigger(alqcJob.getTrigger());
			jobChannel.setUserName(alqcJob.getUserName());
			jobChannel.setVersion(alqcJob.getVersion());
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
	public EwcmsJobChannel getScheduledJobChannel(Integer jobId) {
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
