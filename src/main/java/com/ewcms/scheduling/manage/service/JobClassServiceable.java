/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.service;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.model.JobClass;

/**
 * 定时器定时工作的执行类Service
 * 
 * @author 吴智俊
 */
public interface JobClassServiceable {
	/**
	 * 新增执行类
	 * 
	 * @param jobClass 执行类
	 * @return Integer
	 * @throws BaseException
	 */
    public Integer saveJobClass(JobClass jobClass) throws BaseException;

    /**
     * 修改执行类
     * 
     * @param jobClass 执行类
     * @return Integer
     * @throws BaseException
     */
    public Integer updateJobClass(JobClass jobClass) throws BaseException;

    /**
     * 根据jobClassId条件查询执行类
     * 
     * @param jobClassId 执行类编号
     * @return JobClass
     * @throws BaseException
     */
    public JobClass findJobClassById(Integer jobClassId) throws BaseException;

    /**
     * 查询所有执行类
     * 
     * @return List
     * @throws BaseException
     */
    public List<JobClass> findJobClassAll() throws BaseException;

    /**
     * 删除执行类
     * 
     * @param jobClassId 执行类编号
     * @throws BaseException
     */
    public void deletedJobClass(Integer jobClassId) throws BaseException;

    /**
     * 根据classEntity条件查询执行类
     * 
     * @param classEntity 执行类实体名
     * @return JobClass
     * @throws BaseException
     */
    public JobClass findJobClassByClassEntity(String classEntity) throws BaseException;
}
