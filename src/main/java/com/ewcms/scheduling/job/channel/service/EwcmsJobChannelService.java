/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.channel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.JobClassEntity;
import com.ewcms.scheduling.job.channel.dao.EwcmsJobChannelDAO;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manage.dao.AlqcJobClassDAO;
import com.ewcms.scheduling.manage.dao.AlqcJobDAO;
import com.ewcms.scheduling.manage.fac.AlqcSchedulingFacable;
import com.ewcms.scheduling.manage.util.ConversionUtil;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.ewcms.scheduling.model.AlqcJob;
import com.ewcms.scheduling.model.AlqcJobClass;

/**
 * 频道定时任务Service
 * 
 * @author 吴智俊
 */
@Service()
public class EwcmsJobChannelService implements EwcmsJobChannelServiceable {
	@Autowired
	private EwcmsJobChannelDAO ewcmsJobChannelDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private AlqcJobDAO alqcJobDAO;
	@Autowired
	private AlqcJobClassDAO alqcJobClassDAO;
	@Autowired
	private AlqcSchedulingFacable alqcSchedulingFac;
	
	@Override
	public Integer saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException{
		Channel channel = channelDAO.get(channelId);
		if (channel != null) {
			AlqcJob alqcJob = new AlqcJob();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				alqcJob = alqcJobDAO.get(vo.getJobId());
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
				AlqcJobClass alqcJobClass = null;
				if (channelId != 309){
					alqcJobClass = alqcJobClassDAO.findByAlqcJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
					if (alqcJobClass.getId() == null) {
						alqcJobClass.setClassEntity(JobClassEntity.JOB_CHANNEL);
						alqcJobClass.setClassName("频道定时器类");
						alqcJobClass.setDescription("频道定时器类");
						alqcJobClassDAO.persist(alqcJobClass);
					}
				}else{//TODO 浔阳中使用,在平台中将被删除
					alqcJobClass = alqcJobClassDAO.findByAlqcJobClassByClassEntity(JobClassEntity.JOB_LEADINGWINDOW);
					if (alqcJobClass.getId() == null) {
						alqcJobClass.setClassEntity(JobClassEntity.JOB_LEADINGWINDOW);
						alqcJobClass.setClassName("领导之窗定时器类");
						alqcJobClass.setDescription("领导之窗定时器类");
						alqcJobClassDAO.persist(alqcJobClass);
					}
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
				return alqcSchedulingFac.saveScheduleJob(jobChannel);
			} else {
				return alqcSchedulingFac.updateScheduledJob(jobChannel);
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
	public List<Channel> findChildenChannelByParentChannelId(Integer parentChannelId){
		return ewcmsJobChannelDAO.getChannelChildren(parentChannelId);
	}
	
	@Override
	public Channel findChannelByChannelId(Integer channelId) {
		return channelDAO.get(channelId);
	}

}
