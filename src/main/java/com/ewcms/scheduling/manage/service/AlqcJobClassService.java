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
import com.ewcms.scheduling.manage.dao.AlqcJobClassDAO;
import com.ewcms.scheduling.model.AlqcJobClass;

/**
 * 
 * @author wzj
 */
@Service("alqcJobClassService")
public class AlqcJobClassService implements AlqcJobClassServiceable {

	@Autowired
	private AlqcJobClassDAO alqcJobClassDAO;

	@Override
	public Integer saveJobClass(AlqcJobClass alqcJobClass) throws BaseException {
		if (validator(alqcJobClass)) {
			alqcJobClassDAO.persist(alqcJobClass);
			return alqcJobClass.getId();
		}
		return null;
	}

	@Override
	public Integer updateJobClass(AlqcJobClass alqcJobClass)
			throws BaseException {
		alqcJobClassDAO.merge(alqcJobClass);
		return alqcJobClass.getId();
	}

	@Override
	public AlqcJobClass findByJobClass(Integer id) throws BaseException {
		return (AlqcJobClass) alqcJobClassDAO.get(id);
	}

	@Override
	public List<AlqcJobClass> findByAllJobClass() throws BaseException {
		return (List<AlqcJobClass>) alqcJobClassDAO.findAll();
	}

	@Override
	public void deletedJobClass(Integer id) throws BaseException {
		alqcJobClassDAO.removeByPK(id);
	}

	protected Boolean validator(AlqcJobClass alqcJobClass) throws BaseException {
		String jobClassEntity = alqcJobClass.getClassEntity().trim();
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
	public AlqcJobClass findByAlqcJobClassByClassEntity(String classEntity) throws BaseException {
		return alqcJobClassDAO.findByAlqcJobClassByClassEntity(classEntity);
	}

}
