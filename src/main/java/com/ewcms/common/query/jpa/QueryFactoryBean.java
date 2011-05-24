package com.ewcms.common.query.jpa;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.ewcms.common.query.cache.ResultCacheable;

public class QueryFactoryBean implements FactoryBean<QueryFactory>,InitializingBean {

    private EntityManagerFactory entityManagerFactory;
    private ResultCacheable cache;
    
    private QueryFactory queryFactory;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if(entityManagerFactory == null){
            
        }
        queryFactory = new QueryFactory();
        queryFactory.setCache(cache);
        queryFactory.setEntityManagerFactory(entityManagerFactory);
    }

    @Override
    public QueryFactory getObject() throws Exception {
        return queryFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return QueryFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setEntityManagerFactory(EntityManagerFactory factory){
        this.entityManagerFactory = factory;
    }
    
    public void setCache(ResultCacheable cache){
        this.cache = cache;
    }
}
