/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.ewcms.common.query.cache.ResultCacheable;

public class QueryFactory {

    private EntityManagerFactory entityManagerFactory;
    
    private ResultCacheable cache;
    
    private EntityManager createEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    
    public EntityQueryable createEntityQuery(Class<?> entityClass){
        
       return new EntityQuery(createEntityManager(),entityClass)
           .setCache(cache);
    }

    public EntityQueryable createEntityQuery(Class<?> entityClass,
            SelectCallback extSelectCallback){
        
        return new EntityQuery(createEntityManager(),entityClass,extSelectCallback)
            .setCache(cache);
    }
    
    public EntityQueryable createEntityQuery(Class<?> entityClass,
            SelectCallback countSelectCallback,SelectCallback extSelectCallback){
        
        return new EntityQuery(createEntityManager(),entityClass,countSelectCallback,extSelectCallback)
            .setCache(cache);
    }
   
    public HqlQueryable createHqlQuery(String hql,String countHql) {
        
        return new HqlQuery(createEntityManager(),hql,countHql)
            .setCache(cache);
    }

    public  HqlQueryable createHqlQuery(String hql,String countHql,String extHql) {
        
        return new HqlQuery(createEntityManager(),hql,countHql,extHql)
            .setCache(cache);
    }
    
    public void setEntityManagerFactory(EntityManagerFactory factory){
        this.entityManagerFactory = factory;
    }
    
    public void setCache(ResultCacheable cache){
        this.cache = cache;
    }
}
