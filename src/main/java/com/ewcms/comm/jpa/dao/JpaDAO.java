/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.comm.jpa.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 *
 * @author wangwei
 */
public abstract class JpaDAO<K, E> extends JpaDaoSupport implements JpaDAOable<K,E> {

    protected Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public JpaDAO() {
        ParameterizedType genericSuperclass =
                (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass =
                (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(E entity) {
        this.getJpaTemplate().persist(entity);
    }

    @Override
    public void remove(E entity) {
        this.getJpaTemplate().remove(entity);
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
        this.getJpaTemplate().getEntityManager();
        return this.getJpaTemplate().merge(entity);
    }

    @Override
    public void refresh(E entity){
        this.getJpaTemplate().refresh(entity);
    }

    @Override
    public E get(K pk) {
        return this.getJpaTemplate().find(entityClass, pk);
    }

    @Override
    public E getRefresh(K pk){
        return this.getJpaTemplate().getReference(entityClass, pk);
    }

    @Override
    public E flush(E entity) {
        this.getJpaTemplate().flush();
        return entity;
    }

    @Override
    public List<E> findAll() {
        return getJpaTemplate().execute(new JpaCallback<List<E>>() {
            @Override
            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                String hql = String.format("Select h From %s h", entityClass.getName());
                return em.createQuery(hql, entityClass).getResultList();
            }
        });
    }

    @Override
    public Integer removeAll() {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {
            @Override
            public Integer doInJpa(EntityManager em) throws PersistenceException {
                String hql = String.format("Delete From %s", entityClass.getName());
                return em.createQuery(hql).executeUpdate();
            }
        });
    }

    @Override
    public EntityManager getEntityManager(){
        if(this.getJpaTemplate().getEntityManager() == null){
            return this.getJpaTemplate().getEntityManagerFactory().createEntityManager();
        }else{
            return this.getJpaTemplate().getEntityManager();
        }
    }
}
