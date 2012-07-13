/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.citizen.dao;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.citizen.model.Citizen;

/**
 *
 * @author 吴智俊
 */
@Repository
public class CitizenDAO extends JpaDAO<Integer, Citizen> {
	public Citizen findCitizenByCitizenName(String citizenName){
    	String hql = "FROM Citizen AS c WHERE c.name=:citizenName";
    	
    	TypedQuery<Citizen> query = this.getEntityManager().createQuery(hql, Citizen.class);
    	query.setParameter("citizenName", citizenName);
    	
    	Citizen citizen = null;
    	try{
    		citizen = (Citizen) query.getSingleResult();
    	}catch(NoResultException e){}
    	
    	return citizen;
	}
	
	public Citizen findCitizenByCitizenIdAndCitizenName(Integer citizenId, String citizenName){
    	String hql = "FROM Citizen AS c WHERE c.id!=:citizenId And c.name=:citizenName";
    	
    	TypedQuery<Citizen> query = this.getEntityManager().createQuery(hql, Citizen.class);
    	query.setParameter("citizenId", citizenId);
    	query.setParameter("citizenName", citizenName);
    	
    	Citizen citizen = null;
    	try{
    		citizen = (Citizen) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	
    	return citizen;
	}
}
