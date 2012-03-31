/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Organ;

/**
 * @author 周冬初
 * @author wuzhijun
 */
@Repository
public class OrganDAO extends JpaDAO<Integer,Organ> {
	/**
	 * 获取子节点模板
	 * 
	 */
	public List<Organ> getOrganChildren(final Integer parentId){
		String hql;
		if(parentId==null){
			hql = "From Organ o Where o.parent is null Order By o.id";
		}else{
			hql = "From Organ o Where o.parent.id = " + parentId + " Order By o.id";
		}
		return this.getEntityManager().createQuery(hql, Organ.class).getResultList();
	}	
}
