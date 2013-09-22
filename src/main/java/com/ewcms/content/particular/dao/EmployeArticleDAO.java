/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.EmployeArticle;

@Repository
public class EmployeArticleDAO extends JpaDAO<Long, EmployeArticle> {
	public List<EmployeArticle> findEmployeArticleAll(){
		String hql = "From EmployeArticle As p Where p.release=true And p.organ is not null And p.published is not null";
		TypedQuery<EmployeArticle> query = this.getEntityManager().createQuery(hql, EmployeArticle.class);
		return query.getResultList();
	}
}
