package com.ewcms.crawler.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.crawler.model.Domain;

@Repository
public class GatherDAO extends JpaDAO<Long, Gather> {
	
	@SuppressWarnings("unchecked")
	public Domain findDomainById(Long domainId){
		String hql = "From Domain As d Where d.id=?";
		List<Domain> list = this.getJpaTemplate().find(hql, domainId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Long findMaxDomain(Long gatherId){
		String hql = "Select Max(u.level) From Gather As g Left Join g.domains As u Where g.id=?";
    	List<Long> list = this.getJpaTemplate().find(hql, gatherId);
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public MatchBlock findMatchBlockById(Long matchBlockId){
		String hql = "From MatchBlock As m Where m.id=?";
		List<MatchBlock> list = this.getJpaTemplate().find(hql, matchBlockId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<MatchBlock> findParentMatchBlockByGatherId(Long gatherId){
		String hql = "Select m From Gather As g Right Join g.matchBlocks As m Where m.parent Is Null And g.id=? Order By m.sort";
		List<MatchBlock> list = this.getJpaTemplate().find(hql, gatherId);
		if (list.isEmpty()) return new ArrayList<MatchBlock>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MatchBlock> findChildMatchBlockByParentId(Long gatherId, Long parentId){
		String hql = "Select m From Gather As g Right Join g.matchBlocks As m Where g.id=? And m.parent.id=? Order By m.sort";
		List<MatchBlock> list = this.getJpaTemplate().find(hql, gatherId, parentId);
		if (list.isEmpty()) return new ArrayList<MatchBlock>();
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updMatchBlockByIdSetSort(final Long matchBlockId, final Long sort){
        this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql = "Update MatchBlock As m Set m.sort=? Where m.id=?";
                Query query = em.createQuery(hql);
                query.setParameter(1, sort);
                query.setParameter(2, matchBlockId);
                query.executeUpdate();	
                return null;
            }
        });	
	}
	
	@SuppressWarnings("unchecked")
	public Long findMaxMatchBlock(Long gatherId, Long parentId){
		String hql = "Select Max(m.sort) From Gather As g Left Join g.matchBlocks As m Where g.id=? ";
		List<Long> list;
		if (parentId != null){
			hql = hql + " And m.parent.id=?";
	    	list = this.getJpaTemplate().find(hql, gatherId, parentId);
		}else{
	    	list = this.getJpaTemplate().find(hql, gatherId);
		}
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public FilterBlock findFilterBlockById(Long filterBlockId){
		String hql = "From FilterBlock As f Where f.id=?";
		List<FilterBlock> list = this.getJpaTemplate().find(hql, filterBlockId);
		if (list.isEmpty()) return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<FilterBlock> findParentFilterBlockByGatherId(Long gatherId){
		String hql = "Select f From Gather As g Right Join g.filterBlocks As f Where f.parent Is Null And g.id=? Order By f.sort";
		List<FilterBlock> list = this.getJpaTemplate().find(hql, gatherId);
		if (list.isEmpty()) return new ArrayList<FilterBlock>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<FilterBlock> findChildFilterBlockByParentId(Long gatherId, Long parentId){
		String hql = "Select f From Gather As g Right Join g.filterBlocks As f Where g.id=? And f.parent.id=? Order By f.sort";
		List<FilterBlock> list = this.getJpaTemplate().find(hql, gatherId, parentId);
		if (list.isEmpty()) return new ArrayList<FilterBlock>();
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updFilterBlockByIdSetSort(final Long filterBlockId, final Long sort){
        this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql = "Update FilterBlock As m Set m.sort=? Where m.id=?";
                Query query = em.createQuery(hql);
                query.setParameter(1, sort);
                query.setParameter(2, filterBlockId);
                query.executeUpdate();	
                return null;
            }
        });	
	}
	
	@SuppressWarnings("unchecked")
	public Long findMaxFilterBlock(Long gatherId, Long parentId){
		String hql = "Select Max(f.sort) From Gather As g Left Join g.filterBlocks As f Where g.id=? ";
		List<Long> list;
		if (parentId != null){
			hql = hql + " And f.parent.id=?";
	    	list = this.getJpaTemplate().find(hql, gatherId, parentId);
		}else{
	    	list = this.getJpaTemplate().find(hql, gatherId);
		}
    	if (list.isEmpty()) return 0L;
    	return list.get(0) == null ? 0L : list.get(0);
	}
}
