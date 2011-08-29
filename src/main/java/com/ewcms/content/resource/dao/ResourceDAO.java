/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author wangwei
 */
@Repository
public class ResourceDAO extends JpaDAO<Integer, Resource> {

    @Override
    public void remove(Resource resource) {
        resource.setDeleteFlag(true);
        this.persist(resource);
    }

    @Override
    public void removeByPK(Integer pk) {
        Resource resource = this.get(pk);
        remove(resource);
    }

    public void clear(Integer pk){
        Resource resource = this.get(pk);
        if(resource.isDeleteFlag()){
            this.getJpaTemplate().remove(resource);
        }
    }

    public void revert(Integer pk) {
        Resource resource = this.get(pk);
        resource.setDeleteFlag(false);
        this.persist(resource);
    }
    
    public List<Resource> findNotReleaseResources(final Integer siteId) {
        return getJpaTemplate().execute(new JpaCallback<List<Resource>>() {
            @Override
            public List<Resource> doInJpa(EntityManager em) throws PersistenceException {
                String hql = "From Resource o Where o.siteId= ? And o.release = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, siteId);
                query.setParameter(2, Boolean.FALSE);
                return query.getResultList();
            }
        });
    }

    public void publishResource(Integer id) {
         Resource resource = get(id);
         resource.setRelease(Boolean.TRUE);
         persist(resource);
    }

    public void updateNotRelease(final Integer siteId) {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Update Resource o Set o.release=? Where o.siteId= ? And o.release = ?";
                TypedQuery<Resource> query = em.createQuery(hql, Resource.class);
                query.setParameter(1, Boolean.FALSE);
                query.setParameter(2, siteId);
                query.setParameter(3, Boolean.TRUE);
                query.executeUpdate();
                return null;
            }
        });
    }
}
