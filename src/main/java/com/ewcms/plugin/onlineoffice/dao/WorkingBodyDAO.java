/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.onlineoffice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.plugin.onlineoffice.model.Matter;
import com.ewcms.plugin.onlineoffice.model.WorkingBody;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class WorkingBodyDAO extends JpaDAO<Integer, WorkingBody> {
    @SuppressWarnings("unchecked")
	public Integer findWorkingBodyMaxSort(Integer channelId){
    	String hql = "Select Max(p.sort) From WorkingBody As p Where p.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }
    
    @SuppressWarnings("unchecked")
	public Integer findWorkingBodyMaxSortByWorkingBodyId(Integer workingBodyId, Integer channelId){
    	String hql = "Select Max(c.sort) From WorkingBody As p Right Join p.children As c Where p.id=? And p.channelId=? And c.channelId=?";
    	List<Object> list = this.getJpaTemplate().find(hql, workingBodyId, channelId, channelId);
    	if (list.isEmpty() || list.get(0) == null) return 0;
    	return (Integer)(list.get(0));
    }

    @SuppressWarnings("unchecked")
	public Matter findMatterByMatterIdAndWorkingBodyId(Integer matterId, Integer workingBodyId, Integer channelId){
    	String hql = "Select l From WorkingBody As p Left Join p.matters As l Where l.id=? And p.id=? And p.channelId=?";
    	List<Matter> list = this.getJpaTemplate().find(hql, matterId, workingBodyId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public WorkingBody findWorkingBodyBySort(Integer parentId, Integer sort, Integer channelId){
    	String hql = "Select p From WorkingBody As p Left Join p.parent As a Where a.id=? And p.sort=? And p.channelId=?";
    	List<WorkingBody> list = this.getJpaTemplate().find(hql, parentId, sort, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public WorkingBody findWorkingBodyParent(Integer channelId){
    	String hql = "From WorkingBody As p Where p.parent is null And p.channelId=? Order By p.sort";
    	List<WorkingBody> list = this.getJpaTemplate().find(hql, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<Matter> findMattersByWorkingBodyId(Integer workingBodyId, Integer channelId){
    	String hql = "Select l From WorkingBody As p Right Join p.matters As l Where p.id=? And p.channelId=? Order By l.sort";
    	List<Matter> list = this.getJpaTemplate().find(hql,workingBodyId, channelId);
    	if (list.isEmpty()) return null;
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public WorkingBody findWorkingBodyByWorkingBodyIdAndChannelId(Integer workingBodyId, Integer channelId){
    	String hql = "From WorkingBody As p Where p.id=? And p.channelId=?";
    	List<WorkingBody> list = this.getJpaTemplate().find(hql, workingBodyId, channelId);
    	if (list.isEmpty()) return null;
    	return list.get(0);
    }
}
