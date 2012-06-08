/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.crawler.manager.dao.GatherDAO;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.JobClassEntity;
import com.ewcms.scheduling.generate.job.crawler.dao.EwcmsJobCrawlerDAO;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.manager.SchedulingFacable;
import com.ewcms.scheduling.manager.dao.JobClassDAO;
import com.ewcms.scheduling.manager.dao.JobInfoDAO;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 采集器定时任务Service
 * 
 * @author 吴智俊
 */
@Service
public class EwcmsJobCrawlerService implements EwcmsJobCrawlerServiceable {
	@Autowired
	private EwcmsJobCrawlerDAO ewcmsJobCrawlerDAO;
	@Autowired
	private GatherDAO gatherDAO;
	@Autowired
	private JobInfoDAO jobInfoDAO;
	@Autowired
	private JobClassDAO jobClassDAO;
	@Autowired
	private SchedulingFacable schedulingFac;
	
	@Override
	public Long saveOrUpdateJobCrawler(Long gatherId, PageDisplayVO vo) throws BaseException{
		Gather gather = gatherDAO.get(gatherId);
		if (gather != null) {
			JobInfo jobInfo = new JobInfo();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				jobInfo = jobInfoDAO.get(vo.getJobId());
			}
			
			if (jobInfo == null) {
				throw new BaseException("定时任务已经被删除,请重新操作!","定时任务已经被删除,请重新操作!");
			}
			
			jobInfo = ConversionUtil.constructJobInfoVo(jobInfo,vo);

			EwcmsJobCrawler jobCrawler = new EwcmsJobCrawler();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0) {
				jobCrawler.setId(vo.getJobId());
				jobCrawler.setJobClass(jobInfo.getJobClass());
			}else{
				JobClass jobClass = jobClassDAO.findByJobClassByClassEntity(JobClassEntity.JOB_CRAWLER);
				if (jobClass == null) {
					jobClass = new JobClass();
					jobClass.setClassEntity(JobClassEntity.JOB_CRAWLER);
					jobClass.setClassName("采集定时器类");
					jobClass.setDescription("采集定时器类");
					jobClassDAO.persist(jobClass);
				}
				jobCrawler.setJobClass(jobClass);
			}

			jobCrawler.setDescription(jobInfo.getDescription());
			jobCrawler.setLabel(jobInfo.getLabel());
			jobCrawler.setNextFireTime(jobInfo.getNextFireTime());
			jobCrawler.setOutputLocale(jobInfo.getOutputLocale());
			jobCrawler.setPreviousFireTime(jobInfo.getPreviousFireTime());
			jobCrawler.setState(jobInfo.getState());
			jobCrawler.setTrigger(jobInfo.getTrigger());
			jobCrawler.setUserName(jobInfo.getUserName());
			jobCrawler.setVersion(jobInfo.getVersion());
			jobCrawler.setGather(gather);
			if (jobCrawler.getId() == null) {
				return schedulingFac.saveScheduleJob(jobCrawler);
			} else {
				return schedulingFac.updateScheduledJob(jobCrawler);
			}
		}
		return null;
	}

	@Override
	public EwcmsJobCrawler getScheduledJobCrawler(Long jobId) {
		return (EwcmsJobCrawler)ewcmsJobCrawlerDAO.get(jobId);
	}

	@Override
	public EwcmsJobCrawler findJobCrawlerByGatherId(Long gatherId) {
		return (EwcmsJobCrawler)ewcmsJobCrawlerDAO.findJobCrawlerByGatherId(gatherId);
	}
}
