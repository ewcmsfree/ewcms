/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.online.model.Matter;

/**
 *
 * @author 吴智俊
 */
@Repository
public class MatterDAO extends JpaDAO<Integer, Matter> {
	
	public Long findMatterMaxSort(){
    	String hql = "SELECT MAX(a.sort) FROM Matter AS a";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
 
    	Long maxSort = 0L;
		try{
			maxSort = (Long) query.getSingleResult();
		}catch(NoResultException e){
		}
		if (maxSort == null){
			maxSort = 0L;
		}
		return maxSort;
    }
    
    public Matter findMatterBySort(Long sort){
    	String hql = "FROM Matter AS a WHERE a.sort=:sort";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	query.setParameter("sort", sort);
    	
    	Matter matter = null;
    	try{
    		matter = (Matter) query.getSingleResult();
    	}catch(NoResultException e){}

    	return matter;
    }
    
	public List<Matter> findMatterAllOrderBySort(){
    	String hql = "FROM Matter AS a ORDER BY a.sort";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	
    	return query.getResultList();
    }
    
	public Matter findMatterByMatterName(String matterName){
    	String hql = "From Matter As l Where l.name=:matterName";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	query.setParameter("matterName", matterName);
    	
    	Matter matter = null;
    	try{
    		matter = (Matter) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	
    	return matter;
    }
    
	public Matter findMatterByMatterIdAndMatterName(Integer matterId, String matterName){
    	String hql = "From Matter As l Where l.id!=:matterId And l.name=:matterName";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	query.setParameter("matterId", matterId);
    	query.setParameter("matterName", matterName);
    	
    	Matter matter = null;
    	try{
    		matter = (Matter) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	
    	return matter;
    }
}
