/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.scheduling.manage.service;

import java.lang.Class;
import java.util.List;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.manage.dao.JobClassDAO;
import com.ewcms.scheduling.model.JobClass;

/**
 * 
 * @author 吴智俊
 */
@Service("jobClassService")
public class JobClassService implements JobClassServiceable {

	@Autowired
	private JobClassDAO jobClassDAO;

	@Override
	public Integer saveJobClass(JobClass jobClass) throws BaseException {
		if (validator(jobClass)) {
			jobClassDAO.persist(jobClass);
			return jobClass.getId();
		}
		return null;
	}

	@Override
	public Integer updateJobClass(JobClass jobClass)
			throws BaseException {
		jobClassDAO.merge(jobClass);
		return jobClass.getId();
	}

	@Override
	public JobClass findJobClassById(Integer jobClassId) throws BaseException {
		return (JobClass) jobClassDAO.get(jobClassId);
	}

	@Override
	public List<JobClass> findJobClassAll() throws BaseException {
		return (List<JobClass>) jobClassDAO.findAll();
	}

	@Override
	public void deletedJobClass(Integer jobClassId) throws BaseException {
		jobClassDAO.removeByPK(jobClassId);
	}

	protected Boolean validator(JobClass jobClass) throws BaseException {
		String jobClassEntity = jobClass.getClassEntity().trim();
		if (jobClassEntity != null && jobClassEntity.length() > 0) {
			try {
				Class<?> classEntity = Class.forName(jobClassEntity);
				if (Job.class.isAssignableFrom(classEntity)) {
					return true;
				} else {
					throw new BaseException("不是一个有效的作业", "不是一个有效的作业");
				}
			} catch (ClassNotFoundException ex) {
				throw new BaseException("未找到执行的作业", "未找到执行的作业");
			}
		} else {
			throw new BaseException("未设置作业", "未设置作业");
		}

	}

	@Override
	public JobClass findJobClassByClassEntity(String classEntity) throws BaseException {
		return jobClassDAO.findJobClassByClassEntity(classEntity);
	}

}
