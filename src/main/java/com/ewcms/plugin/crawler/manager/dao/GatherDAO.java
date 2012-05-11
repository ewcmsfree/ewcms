/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.manager.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.plugin.crawler.model.FilterBlock;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.model.MatchBlock;

/**
 * 
 * @author wu_zhijun
 *
 */
@Repository
public class GatherDAO extends JpaDAO<Long, Gather> {
	
	public MatchBlock findMatchBlockById(final Long matchBlockId){
		String hql = "From MatchBlock As m Where m.id=:matchBlockId";
		
		TypedQuery<MatchBlock> query = this.getEntityManager().createQuery(hql, MatchBlock.class);
		query.setParameter("matchBlockId", matchBlockId);
		
		MatchBlock matchBlock = null;
		try{
			matchBlock = (MatchBlock) query.getSingleResult();
		}catch(NoResultException e){
		}
		return matchBlock;
	}
	
	public List<MatchBlock> findParentMatchBlockByGatherId(final Long gatherId){
		String hql = "Select m From Gather As g Right Join g.matchBlocks As m Where m.parent Is Null And g.id=:gatherId Order By m.sort";
		
		TypedQuery<MatchBlock> query = this.getEntityManager().createQuery(hql, MatchBlock.class);
		query.setParameter("gatherId", gatherId);
		
		return query.getResultList();
	}
	
	public List<MatchBlock> findChildMatchBlockByParentId(final Long gatherId, final Long parentId){
		String hql = "Select m From Gather As g Right Join g.matchBlocks As m Where g.id=:gatherId And m.parent.id=:parentId Order By m.sort";
		
		TypedQuery<MatchBlock> query = this.getEntityManager().createQuery(hql, MatchBlock.class);
		query.setParameter("gatherId", gatherId);
		query.setParameter("parentId", parentId);
		
		return query.getResultList();
	}
	
	public void updMatchBlockByIdSetSort(final Long matchBlockId, final Long sort){
    	String hql = "Update MatchBlock As m Set m.sort=:sort Where m.id=:matchBlockId";
    	
    	Query query = this.getEntityManager().createQuery(hql);
    	query.setParameter("sort", sort);
    	query.setParameter("matchBlockId", matchBlockId);
    	
    	query.executeUpdate();
	}
	
	public Long findMaxMatchBlock(final Long gatherId, final Long parentId){
		String hql = "Select Max(m.sort) From Gather As g Left Join g.matchBlocks As m Where g.id=:gatherId ";
		
		TypedQuery<Long> query;
		if (parentId != null){
			hql = hql + " And m.parent.id=:parentId ";
			
			query = this.getEntityManager().createQuery(hql, Long.class);
			
			query.setParameter("gatherId", gatherId);
			query.setParameter("parentId", parentId);
		}else{
			query = this.getEntityManager().createQuery(hql, Long.class);
			
			query.setParameter("gatherId", gatherId);
		}
		
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
	
	public FilterBlock findFilterBlockById(final Long filterBlockId){
		String hql = "From FilterBlock As f Where f.id=:filterBlockId";
		
		TypedQuery<FilterBlock> query = this.getEntityManager().createQuery(hql, FilterBlock.class);
		query.setParameter("filterBlockId", filterBlockId);
		
		FilterBlock filterBlock = null;
		try{
			filterBlock = (FilterBlock) query.getSingleResult();
		}catch(NoResultException e){
		}
		return filterBlock;
	}
	
	public List<FilterBlock> findParentFilterBlockByGatherId(final Long gatherId){
		String hql = "Select f From Gather As g Right Join g.filterBlocks As f Where f.parent Is Null And g.id=:gatherId Order By f.sort";
		
		TypedQuery<FilterBlock> query = this.getEntityManager().createQuery(hql, FilterBlock.class);
		query.setParameter("gatherId", gatherId);
		
		return query.getResultList();
	}
	
	public List<FilterBlock> findChildFilterBlockByParentId(final Long gatherId, final Long parentId){
		String hql = "Select f From Gather As g Right Join g.filterBlocks As f Where g.id=:gatherId And f.parent.id=:parentId Order By f.sort";
		
		TypedQuery<FilterBlock> query = this.getEntityManager().createQuery(hql, FilterBlock.class);
		query.setParameter("gatherId", gatherId);
		query.setParameter("parentId", parentId);
		
		return query.getResultList();
	}
	
	public void updFilterBlockByIdSetSort(final Long filterBlockId, final Long sort){
		String hql = "Update FilterBlock As m Set m.sort=:sort Where m.id=:filterBlockId";
		
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("sort", sort);
		query.setParameter("filterBlockId", filterBlockId);
		
		query.executeUpdate();
	}
	
	public Long findMaxFilterBlock(final Long gatherId, final Long parentId){
		String hql = "Select Max(f.sort) From Gather As g Left Join g.filterBlocks As f Where g.id=:gatherId ";
		
		TypedQuery<Long> query;
		if (parentId != null){
			hql = hql + " And f.parent.id=:parentId ";
			
			query = this.getEntityManager().createQuery(hql, Long.class);
			query.setParameter("gatherId", gatherId);
			query.setParameter("parentid", parentId);
		}else{
			query = this.getEntityManager().createQuery(hql, Long.class);
			query.setParameter("gatherId", gatherId);
		}
		
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
}
