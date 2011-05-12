/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.onlineoffice.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.plugin.onlineoffice.model.Matter;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class MatterDAO extends JpaDAO<Integer, Matter> {
    @SuppressWarnings("unchecked")
	public Integer findMatterMaxSort(){
    	String hql = "SELECT MAX(a.sort) FROM Matter AS a";
    	List<Object> list = this.getJpaTemplate().find(hql);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }
    
    @SuppressWarnings("unchecked")
    public Matter findMatterBySort(Integer sort){
    	String hql = "FROM Matter AS a WHERE a.sort=?";
    	List<Matter> list = this.getJpaTemplate().find(hql,sort);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Matter> findMatterAllOrderBySort(){
    	String hql = "FROM Matter AS a ORDER BY a.sort";
    	List<Matter> list = this.getJpaTemplate().find(hql);
    	if (list.isEmpty()) return new ArrayList<Matter>();
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public Matter findMatterByMatterName(String matterName){
    	String hql = "From Matter As l Where l.name=?";
    	List<Matter> list = this.getJpaTemplate().find(hql, matterName);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public Matter findMatterByMatterIdAndMatterName(Integer matterId, String matterName){
    	String hql = "From Matter As l Where l.id!=? And l.name=?";
    	List<Matter> list = this.getJpaTemplate().find(hql, matterId, matterName);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
