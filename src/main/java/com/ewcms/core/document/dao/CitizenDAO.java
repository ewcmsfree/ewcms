/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.core.document.model.Citizen;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class CitizenDAO extends JpaDAO<Integer, Citizen> {
	@SuppressWarnings("unchecked")
	public Citizen findCitizenByCitizenName(String citizenName){
    	String hql = "FROM Citizen AS c WHERE c.name=?";
    	List<Citizen> list = this.getJpaTemplate().find(hql,citizenName);
    	if (list.isEmpty()) return null;
    	return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Citizen findCitizenByCitizenIdAndCitizenName(Integer citizenId, String citizenName){
    	String hql = "FROM Citizen AS c WHERE c.id!=? And c.name=?";
    	List<Citizen> list = this.getJpaTemplate().find(hql,citizenName);
    	if (list.isEmpty()) return null;
    	return list.get(0);
	}
}
