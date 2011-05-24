/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wangwei
 */
@Service
public class QueryFactory  {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public <V,T> EntityQueryable<V,T> createEntityQuery(final Class<T> entityClass){
        return new EntityQuery<V,T>(
                entityManagerFactory.createEntityManager(),
                entityClass);
    }

    public <V,T> EntityPageQueryable<V,T> createEntityPageQuery(final Class<T> entityClass){
        return new EntityPageQuery<V,T>(
                entityManagerFactory.createEntityManager(),
                entityClass);
    }

    public <V> HqlQueryable<V> createHqlQuery(final String hql){
        return new HqlQuery<V>(
                entityManagerFactory.createEntityManager(),hql);
    }

    public <V> HqlPageQueryable<V> createHqlPageQuery(final String hql,final String countHql){
        return new HqlPageQuery<V>(
                entityManagerFactory.createEntityManager(),
                hql,countHql);
    }

    public <V> HqlPageQueryable<V> createHqlPageQuery(final String hql,
            final String countHql,
            final String sumHql){
        
        return new HqlPageQuery<V>(
                entityManagerFactory.createEntityManager(),
                hql,countHql,sumHql);
    }

    public <V> HqlQueryable<V> createNamedQuery(final String name){
        return new NamedQuery<V>(
                entityManagerFactory.createEntityManager(),
                name);
    }
}
