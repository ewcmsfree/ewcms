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
import com.ewcms.content.particular.model.ZoningCode;

@Repository
public class ZoningCodeDAO extends JpaDAO<Long, ZoningCode> {

	public List<ZoningCode> findZoningCodeAll(){
		String hql = "From ZoningCode As a Order By a.code";
		TypedQuery<ZoningCode> query = this.getEntityManager().createQuery(hql, ZoningCode.class);
		return query.getResultList();
	}
	
	public Boolean findZoningCodeSelected(final Long projectBasicId, final String zoningCodeCode){
    	String hql = "Select r From ProjectBasic As p Inner Join p.zoningCode As r Where p.id=:projectBasicId And r.code=:zoningCodeCode";

    	TypedQuery<ZoningCode> query = this.getEntityManager().createQuery(hql, ZoningCode.class);
    	query.setParameter("projectBasicId", projectBasicId);
    	query.setParameter("zoningCodeCode", zoningCodeCode);

    	List<ZoningCode> list = query.getResultList();
    	return list.isEmpty()? false : true;
    }

	public ZoningCode findZoningCodeByCode(final String code){
		String hql = "From ZoningCode As a Where a.code=:code";
		TypedQuery<ZoningCode> query = this.getEntityManager().createQuery(hql, ZoningCode.class);
		query.setParameter("code", code);
		ZoningCode zoningCode = null;
		try{
			zoningCode = (ZoningCode) query.getSingleResult();
		}catch(NoResultException e){
		}
		return zoningCode;
	}
}
