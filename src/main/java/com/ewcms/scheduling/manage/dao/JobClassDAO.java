/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.JobClass;

/**
 * 定时器定时工作的执行类DAO
 * 
 * @author 吴智俊
 */
@Repository("jobClassDAO")
public class JobClassDAO extends JpaDAO<Integer, JobClass> {
	@SuppressWarnings("unchecked")
	public JobClass findJobClassByClassEntity(String classEntity) {
		String hql = "From JobClass o Where o.classEntity=?";
		List<JobClass> list = this.getJpaTemplate().find(hql, classEntity);
		if (list.isEmpty())
			return new JobClass();
		return list.get(0);
	}
}
