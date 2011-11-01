/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.resource.model.Resource;

/**
 * 实现资源操作数据接口
 * 
 * @author wangwei
 */
@Repository
public class ResourceDAO extends JpaDAO<Integer, Resource> implements ResourceDAOable {

    /**
     * 只删除状态为DELETE的记录
     */
    @Override
    public void remove(Resource resource){
        if(resource.getState() == Resource.State.DELETE){
            super.remove(resource);
        }
    }

    @Override
    public List<Resource> findSoftDeleteResources(final Integer siteId) {
        return getJpaTemplate().execute(new JpaCallback<List<Resource>>() {
            @Override
            public List<Resource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Resource o Where o.site.id= ? And o.state = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, siteId);
                query.setParameter(2, Resource.State.DELETE);
                return query.getResultList();
            }
        });
    }
    
    public List<Resource> findNotReleaseResources(final Integer siteId) {
        return getJpaTemplate().execute(new JpaCallback<List<Resource>>() {
            @Override
            public List<Resource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Resource o Where o.site.id= ? And o.state = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, siteId);
                query.setParameter(2, Resource.State.NORMAL);
                return query.getResultList();
            }
        });
    }

    public void updateNotRelease(final Integer siteId) {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Update Resource o Set o.state=? Where o.site.id= ? And o.state = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, Resource.State.NORMAL);
                query.setParameter(2, siteId);
                query.setParameter(3, Resource.State.RELEASED);
                query.executeUpdate();
                return null;
            }
        });
    }

    @Override
    public Resource getResourceByUri(final Integer siteId,final String uri) {
        
        List<Resource> list = getJpaTemplate().execute(new JpaCallback<List<Resource>>() {
            @Override
            public List<Resource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Resource o Where o.site.id= ? And (o.uri = ? or o.thumbUri = ?)";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, siteId);
                query.setParameter(2, uri);
                query.setParameter(3, uri);
                return query.getResultList();
            }
        });
        
       return list.isEmpty() ? null : list.get(0);
    }
}
