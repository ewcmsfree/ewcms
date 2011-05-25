/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class HqlQueryTemplate implements Parameterable,QueryTemplateable{
    private TypedQuery<Object>  query;
    private EntityManager em;

    public HqlQueryTemplate(final EntityManager entityManager,final String hql) {
        this.em = entityManager;
        query= entityManager.createQuery(hql,Object.class);
    }
    
    @Override
    public HqlQueryTemplate setParameter(String name, Object value) {
        query.setParameter(name, value);
        return this;
    }

    @Override
    public HqlQueryTemplate setParameter(int position, Object value) {
        query.setParameter(position, value);
        return this;
    }

    @Override
    public HqlQueryTemplate setParameter(String name, Date date, TemporalType type) {
        query.setParameter(name, date,type);
        return this;
    }

    @Override
    public HqlQueryTemplate setParameter(int position, Date date, TemporalType type) {
        query.setParameter(position, date, type);
        return this;
    }

    @Override
    public HqlQueryTemplate setParameter(String name, Calendar calendar,TemporalType type) {
        query.setParameter(name, calendar,type);
        return this;
    }

    @Override
    public HqlQueryTemplate setParameter(int position, Calendar calendar,TemporalType type) {
        query.setParameter(position, calendar, type);
        return this;
    }

    @Override
    public HqlQueryTemplate setFirstResult(int startPosition){
        query.setFirstResult(startPosition);
        return this;
    }
    
    @Override
    public HqlQueryTemplate setMaxResults(int maxResults){
        query.setMaxResults(maxResults);
        return this;
    }
    
    @Override
    public Object getResultSingle(){
        return query.getSingleResult();
    }
    
    @Override
    public List<Object> getResultList(){
       return query.getResultList();
    }
        
    @Override
    public <T> T execute(QueryTemplateCallback<T> callback){
        return callback.doInQuery(em);
    }
}
