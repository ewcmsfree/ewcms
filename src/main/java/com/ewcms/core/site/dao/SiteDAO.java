/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.core.site.model.Site;
/**
 * @author 周冬初
 *
 */
@Repository
public class SiteDAO extends JpaDAO<Integer, Site> {
	/**
	 * 获取机构集的站点集
	 * 
	 */
	public List<Site> getSiteListByOrgans(final Integer[] organs,final Boolean publicenable){	
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            		String hql;
            		if(organs == null || organs.length == 0){
            			hql = "From Site o Where  o.publicenable=?  Order By o.id";
                        Query query = em.createQuery(hql);
                        query.setParameter(1, publicenable);
                        return query.getResultList();
            		}
            		String sqlStr="?";
            		for(int i=1;i<organs.length;i++){
            			sqlStr+=",?";
            		}
                	hql = "From Site o Where  o.publicenable=? and o.organ.id in("+sqlStr+") Order By o.id";
                    Query query = em.createQuery(hql);
                    query.setParameter(1, publicenable);
                    for(int i=0;i<organs.length;i++){
                    	query.setParameter(2+i, organs[i]);
            		}
                    return query.getResultList();            		
            }
        });
		return (List<Site>) res;		
	}
	
	/**
	 * 获取机构的子站点集
	 * 
	 */	
	public List<Site> getSiteChildren(final Integer parentId,final Integer organId){
		Object res = this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql;
            	if(parentId==null){
            		if(organId == null){
                    	hql = "From Site o Where o.parent.id is null Order By o.id";
                        Query query = em.createQuery(hql);
                        return query.getResultList();             			
            		}else{
                    	hql = "From Site o Where o.parent.id is null and o.organ.id=? Order By o.id";
                        Query query = em.createQuery(hql);
                        query.setParameter(1,organId);
                        return query.getResultList();             			
            		}           		
            	}else{
            		if(organId == null){
                    	hql = "From Site o Where o.parent.id=? Order By o.id";
                        Query query = em.createQuery(hql);
                        query.setParameter(1,parentId);
                        return query.getResultList();               			
            		}else{
                    	hql = "From Site o Where o.parent.id=? and o.organ.id=? Order By o.id";
                        Query query = em.createQuery(hql);
                        query.setParameter(1,parentId);
                        query.setParameter(2, organId);
                        return query.getResultList();               			
            		}         		
            	}
            }
        });
		return (List<Site>) res;
	}

	@SuppressWarnings("unchecked")
	public void updSiteParent(final Integer organId,final Integer parentId,final Integer newParentId){
        this.getJpaTemplate().execute(new JpaCallback() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	String hql = "update Site o set o.parent.id=? Where  o.parent.id=? and o.organ.id !=?";
                Query query = em.createQuery(hql);
                query.setParameter(1, newParentId);
                query.setParameter(2, parentId);
                query.setParameter(3,organId);
                query.executeUpdate();	
                return null;
            }
        });	
	}	
}
