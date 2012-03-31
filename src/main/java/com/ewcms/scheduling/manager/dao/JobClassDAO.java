/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.dao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.JobClass;

/**
 * 定时工作类DAO
 * 
 * @author 吴智俊
 */
@Repository
public class JobClassDAO extends JpaDAO<Long, JobClass> {

	public JobClass findByJobClassByClassEntity(final String classEntity) {
		String hql = "From JobClass o Where o.classEntity=:classEntity";
		
		TypedQuery<JobClass> query = this.getEntityManager().createQuery(hql, JobClass.class);
		query.setParameter("classEntity", classEntity);

		JobClass jobClass = null;
		try{
			jobClass = (JobClass) query.getSingleResult();
		}catch(NoResultException e){
		}
		
		return jobClass;
	}
}
