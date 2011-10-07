/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.crawler.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.crawler.dao.GatherDAO;
import com.ewcms.crawler.model.Gather;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.JobClassEntity;
import com.ewcms.scheduling.job.crawler.dao.EwcmsJobCrawlerDAO;
import com.ewcms.scheduling.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.manage.dao.JobClassDAO;
import com.ewcms.scheduling.manage.dao.JobInfoDAO;
import com.ewcms.scheduling.manage.fac.SchedulingFacable;
import com.ewcms.scheduling.manage.util.ConversionUtil;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobClass;

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
	public Integer saveOrUpdateJobCrawler(Long gatherId, PageDisplayVO vo) throws BaseException{
		Gather gather = gatherDAO.get(gatherId);
		if (gather != null) {
			JobInfo alqcJob = new JobInfo();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
				alqcJob = jobInfoDAO.get(vo.getJobId());
			}
			
			if (alqcJob == null) {
				throw new BaseException("定时任务已经被删除,请重新操作!","定时任务已经被删除,请重新操作!");
			}
			
			alqcJob = ConversionUtil.constructAlqcJobVo(alqcJob,vo);

			EwcmsJobCrawler jobCrawler = new EwcmsJobCrawler();
			if (vo.getJobId() != null && vo.getJobId().intValue() > 0) {
				jobCrawler.setId(vo.getJobId());
				jobCrawler.setJobClass(alqcJob.getJobClass());
			}else{
				JobClass alqcJobClass = null;
				alqcJobClass = jobClassDAO.findByJobClassByClassEntity(JobClassEntity.JOB_CRAWLER);
				if (alqcJobClass.getId() == null) {
					alqcJobClass.setClassEntity(JobClassEntity.JOB_CRAWLER);
					alqcJobClass.setClassName("采集定时器类");
					alqcJobClass.setDescription("采集定时器类");
					jobClassDAO.persist(alqcJobClass);
				}
				jobCrawler.setJobClass(alqcJobClass);
			}

			jobCrawler.setDescription(alqcJob.getDescription());
			jobCrawler.setLabel(alqcJob.getLabel());
			jobCrawler.setNextFireTime(alqcJob.getNextFireTime());
			jobCrawler.setOutputLocale(alqcJob.getOutputLocale());
			jobCrawler.setPreviousFireTime(alqcJob.getPreviousFireTime());
			jobCrawler.setState(alqcJob.getState());
			jobCrawler.setTrigger(alqcJob.getTrigger());
			jobCrawler.setUserName(alqcJob.getUserName());
			jobCrawler.setVersion(alqcJob.getVersion());
			jobCrawler.setBasicInfo(gather);
			if (jobCrawler.getId() == null) {
				return schedulingFac.saveScheduleJob(jobCrawler);
			} else {
				return schedulingFac.updateScheduledJob(jobCrawler);
			}
		}
		return null;
	}

	@Override
	public EwcmsJobCrawler getScheduledJobCrawler(Integer jobId) {
		return (EwcmsJobCrawler)ewcmsJobCrawlerDAO.get(jobId);
	}
}
