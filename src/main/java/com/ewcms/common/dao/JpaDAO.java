/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wangwei
 * @author wuzhijun
 */
public abstract class JpaDAO<K, E> implements JpaDAOable<K,E> {

	@PersistenceContext
    private EntityManager entityManager;

    protected Class<E> entityClass;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    } 
    
   @SuppressWarnings("unchecked")
    public JpaDAO() {
        ParameterizedType genericSuperclass =
                (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass =
                (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(E entity) {
    	entityManager.persist(entity);
    }

    @Override
    public void remove(E entity) {
    	entityManager.remove(entity);
    }

    @Override
    public void removeByPK(K pk) {
        E entity = get(pk);
        if (entity != null) {
            remove(entity);
        }
    }
    
    @Override
    public E merge(E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void refresh(E entity){
    	entityManager.refresh(entity);
    }

    @Override
    public E get(K pk) {
        return entityManager.find(entityClass, pk);
    }

    @Override
    public E getRefresh(K pk){
        return entityManager.getReference(entityClass, pk);
    }

    @Override
    public E flush(E entity) {
        entityManager.flush();
        return entity;
    }

    @Override
    public List<E> findAll() {
    	String hql = String.format("Select h From %s h", entityClass.getName());
        return getEntityManager().createQuery(hql, entityClass).getResultList();
    }

    @Override
    public Integer removeAll() {
    	String hql = String.format("Delete From %s", entityClass.getName());
        return getEntityManager().createQuery(hql).executeUpdate();
    }
}
