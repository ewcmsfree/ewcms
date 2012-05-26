/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.online.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.plugin.online.model.WorkingBody;

/**
 *
 * @author 吴智俊
 */
@Repository()
public class WorkingBodyDAO extends JpaDAO<Integer, WorkingBody> {
	public Long findWorkingBodyMaxSort(Integer channelId){
    	String hql = "Select Max(p.sort) From WorkingBody As p Where p.channelId=:channelId";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	
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
    
	public Long findWorkingBodyMaxSortByWorkingBodyId(Integer workingBodyId, Integer channelId){
    	String hql = "Select Max(c.sort) From WorkingBody As p Right Join p.children As c Where p.id=:workingBodyId And p.channelId=:channelId And c.channelId=:channelId";
    	
    	TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
    	query.setParameter("channelId", channelId);
    	query.setParameter("workingBodyId", workingBodyId);
    	
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

	public Matter findMatterByMatterIdAndWorkingBodyId(Integer matterId, Integer workingBodyId, Integer channelId){
    	String hql = "Select l From WorkingBody As p Left Join p.matters As l Where l.id=:matterId And p.id=:workingBodyId And p.channelId=:channelId";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	query.setParameter("matterId", matterId);
    	query.setParameter("workingBodyId", workingBodyId);
    	query.setParameter("channelId", channelId);
    	
    	Matter matter = null;
    	try{
    		matter = (Matter) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return matter;
    }
    
	public WorkingBody findWorkingBodyBySort(Integer parentId, Long sort, Integer channelId){
    	String hql = "Select p From WorkingBody As p Left Join p.parent As a Where a.id=:parentId And p.sort=:sort And p.channelId=:channelId";
    	
    	TypedQuery<WorkingBody> query = this.getEntityManager().createQuery(hql, WorkingBody.class);
    	query.setParameter("parentId", parentId);
    	query.setParameter("sort", sort);
    	query.setParameter("channelId", channelId);
    	
    	WorkingBody workingBody = null;
    	try{
    		workingBody = (WorkingBody) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return workingBody;
    }
    
	public WorkingBody findWorkingBodyParent(Integer channelId){
    	String hql = "From WorkingBody As p Where p.parent is null And p.channelId=:channelId Order By p.sort";
    	
    	TypedQuery<WorkingBody> query = this.getEntityManager().createQuery(hql, WorkingBody.class);
    	query.setParameter("channelId", channelId);

    	WorkingBody workingBody = null;
    	try{
    		workingBody = (WorkingBody) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return workingBody;
    }
    
	public List<Matter> findMattersByWorkingBodyId(Integer workingBodyId, Integer channelId){
    	String hql = "Select l From WorkingBody As p Right Join p.matters As l Where p.id=:workingBodyId And p.channelId=:channelId Order By l.sort";
    	
    	TypedQuery<Matter> query = this.getEntityManager().createQuery(hql, Matter.class);
    	query.setParameter("workingBodyId", workingBodyId);
    	query.setParameter("channelId", channelId);
    	
    	return query.getResultList();
    }
    
	public WorkingBody findWorkingBodyByWorkingBodyIdAndChannelId(Integer workingBodyId, Integer channelId){
    	String hql = "From WorkingBody As p Where p.id=:workingBodyId And p.channelId=:channelId";
    	
    	TypedQuery<WorkingBody> query = this.getEntityManager().createQuery(hql, WorkingBody.class);
    	query.setParameter("workingBodyId", workingBodyId);
    	query.setParameter("channelId", channelId);
    	
    	WorkingBody workingBody = null;
    	try{
    		workingBody = (WorkingBody) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	
    	return workingBody;
    }
}
