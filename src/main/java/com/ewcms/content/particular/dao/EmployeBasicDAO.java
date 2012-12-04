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
import com.ewcms.content.particular.model.EmployeBasic;

@Repository
public class EmployeBasicDAO extends JpaDAO<Long, EmployeBasic> {
	public List<EmployeBasic> findEmployeBasicByPageAndRows(final Integer page, final Integer rows, final String name){
		String hql = "From EmployeBasic As e Where e.release=true And e.organ!=null And e.name Like :name Order By e.published Desc";
		TypedQuery<EmployeBasic> query = this.getEntityManager().createQuery(hql, EmployeBasic.class);
		query.setParameter("name", "%" + name + "%");
		query.setFirstResult(rows * (page - 1));
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public Long findEmployeBasicTotal(final String name){
		String hql = "Select Count(e.id) From EmployeBasic As e Where e.release=true And e.organ!=null And e.name Like :name";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("name", "%" + name + "%");
		return query.getSingleResult();
	}
	
	public EmployeBasic findEmployeBasicByCardCode(final String cardCode){
		String hql = "From EmployeBasic As p Where p.cardCode=:cardCode";
		TypedQuery<EmployeBasic> query = this.getEntityManager().createQuery(hql, EmployeBasic.class);
		query.setParameter("cardCode", cardCode);
		EmployeBasic employeBasic = null;
		try{
			employeBasic = (EmployeBasic) query.getSingleResult();
		}catch(NoResultException e){
		}
		return employeBasic;
	}
}
