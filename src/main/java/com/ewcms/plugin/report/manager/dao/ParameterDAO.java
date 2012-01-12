/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.dao;

import java.util.List;

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
    @SuppressWarnings("unchecked")
	public Boolean findSessionIsEntityByParameterIdAndUserName(Long parameterId, String userName){
    	String hql = "Select p From Parameter As p Where p.id=? And p.defaultValue=?";
    	List<Parameter> list = this.getJpaTemplate().find(hql, parameterId, userName);
    	if (list.isEmpty()) return false;
    	return true;
    }

}
