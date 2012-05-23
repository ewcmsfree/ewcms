/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.ProjectBasic;

@Repository
public class ProjectBasicDAO extends JpaDAO<Long, ProjectBasic> {
	
	public List<ProjectBasic> findProjectBasicAll(){
		String hql = "From ProjectBasic As p Order By p.code";
		TypedQuery<ProjectBasic> query = this.getEntityManager().createQuery(hql, ProjectBasic.class);
		return query.getResultList();
	}
	
	public ProjectBasic findProjectBasicByCode(final String code){
		String hql = "From ProjectBasic As p Where p.code=:code";
		TypedQuery<ProjectBasic> query = this.getEntityManager().createQuery(hql, ProjectBasic.class);
		query.setParameter("code", code);
		ProjectBasic projectBasic = null;
		try{
			projectBasic = (ProjectBasic) query.getSingleResult();
		}catch(NoResultException e){
		}
		return projectBasic;
	}
}
