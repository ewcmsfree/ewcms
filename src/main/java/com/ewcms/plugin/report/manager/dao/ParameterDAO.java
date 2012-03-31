/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.report.model.Parameter;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class ParameterDAO extends JpaDAO<Long, Parameter> {
	
	public Boolean findSessionIsEntityByParameterIdAndUserName(final Long parameterId, final String userName){
    	String hql = "Select p From Parameter As p Where p.id=:parameterId And p.defaultValue=:userName";
    	
    	TypedQuery<Parameter> query = this.getEntityManager().createQuery(hql, Parameter.class);
    	query.setParameter("parameterId", parameterId);
    	query.setParameter("userName", userName);
    	
    	List<Parameter> list = query.getResultList();
    	return list.isEmpty() ? false : true;
    }
}
