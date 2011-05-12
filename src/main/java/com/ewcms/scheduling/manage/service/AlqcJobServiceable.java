/**
 * 创建日期 2009-4-13
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.manage.service;

import java.util.List;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.model.AlqcJob;

/**
 * @author 吴智俊
 */
public interface AlqcJobServiceable {
	/**
	 * 新增调度工作
	 * 
	 * @param alqcJob 
	 * @throws BaseException
	 */
	public AlqcJob saveJob(AlqcJob alqcJob) ;
	
	/**
	 * 修改调度工作
	 * 
	 * @param alqcJob
	 * @throws BaseException
	 */
	public AlqcJob updateJob(AlqcJob alqcJob) ;
	
	/**
	 * 查询调度工作
	 * 
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	public AlqcJob findByJob(Integer id) ;
	
	/**
	 * 查询所有的调度
	 * 
	 * @param context
	 * @return
	 */
	public List<AlqcJob> findByAllJob();
	
	/**
	 * 删除调度工作
	 * 
	 * @param id
	 * @throws BaseException
	 */
	public void deletedJob(Integer id);
}
