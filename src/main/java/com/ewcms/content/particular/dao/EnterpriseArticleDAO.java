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
import com.ewcms.content.particular.model.EnterpriseArticle;

@Repository
public class EnterpriseArticleDAO extends JpaDAO<Long, EnterpriseArticle> {
	public List<EnterpriseArticle> findEnterpriseArticleAll(){
		String hql = "From EnterpriseArticle As p Where p.release=true And p.organ is not null And p.published is not null";
		TypedQuery<EnterpriseArticle> query = this.getEntityManager().createQuery(hql, EnterpriseArticle.class);
		return query.getResultList();
	}
}
