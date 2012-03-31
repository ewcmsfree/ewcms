/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.resource.model.Resource;

/**
 * 实现资源操作数据接口
 * 
 * @author wangwei
 * @author wuzhijun
 */
@Repository
public class ResourceDAO extends JpaDAO<Integer, Resource> implements ResourceDAOable {

    /**
     * 只删除状态为DELETE的记录
     */
    @Override
    public void remove(final Resource resource){
        if(resource.getStatus() == Resource.Status.DELETE){
            super.remove(resource);
        }
    }

    @Override
    public List<Resource> findSoftDeleteResources(final Integer siteId) {
    	String hql = "From Resource o Where o.site.id= ?1 And o.status = ?2";
    	TypedQuery<Resource> query = this.getEntityManager().createQuery(hql,
    	Resource.class);
    	query.setParameter(1, siteId);
    	query.setParameter(2, Resource.Status.DELETE);
    	return query.getResultList();
    }
    
    @Override
    public List<Resource> findPublishResources(final Integer siteId,final Boolean forceAgain) {
    	String hql = "From Resource o Where o.site.id= ?1 And o.status In (?2)";
    	TypedQuery<Resource> query = this.getEntityManager().createQuery(hql, Resource.class);
    	query.setParameter(1, siteId);
    	List<Resource.Status> status = new ArrayList<Resource.Status>();
    	if (forceAgain) {
    		status.add(Resource.Status.RELEASED);
    	}
    	status.add(Resource.Status.NORMAL);
    	query.setParameter(2, status);
    	return query.getResultList();
    }

    @Override
    public Resource getResourceByUri(final Integer siteId,final String uri) {
    	String hql = "From Resource o Where o.site.id= ?1 And (o.uri = ?2 or o.thumbUri = ?3)";
    	TypedQuery<Resource> query = this.getEntityManager().createQuery(hql,Resource.class);

    	query.setParameter(1, siteId);
    	query.setParameter(2, uri);
    	query.setParameter(3, uri);

    	Resource resource = null;
    	try{
    		resource = (Resource) query.getSingleResult();
    	}catch(NoResultException e){
    	}
    	return resource;
    }
}
