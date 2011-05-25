/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.ewcms.common.query.Result;
import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.cache.ResultCacheable;

/**
 *
 * @author wangwei
 */
public class EntityQuery extends AbstractQuery implements EntityQueryable {
    
    private ResultCacheable cache;
    
    private EntityQueryTemplate listTemplate;
    private EntityQueryTemplate countTemplate;
    private EntityQueryTemplate extTemplate;
    
    private int row = DEFAULT_ROW ;
    private int page = DEFAULT_PAGE ;
    
    public EntityQuery(EntityManager em,Class<?> entityClass){
        this(em,entityClass,null);
    }
    
    public EntityQuery(EntityManager em,Class<?> entityClass,SelectCallback extSelectCallback){
        this(em,entityClass,null,extSelectCallback);
    }
    
    public EntityQuery(EntityManager em,Class<?> entityClass,SelectCallback countSelectCallback,SelectCallback extSelectCallback){
        listTemplate = new EntityQueryTemplate(em,entityClass);
        if(countSelectCallback != null){
            countTemplate = new EntityQueryTemplate(em,entityClass);
            countTemplate.select(countSelectCallback);
        }else{
            countTemplate = new EntityQueryTemplate(em,entityClass);
            countTemplate.select(new SelectCallback(){
                @Override
                public Selection<?> select(CriteriaBuilder builder, Root<?> root) {
                    return builder.count(root);
                }
            });
        }
        if(extSelectCallback != null){
            extTemplate = new EntityQueryTemplate(em,entityClass).select(extSelectCallback);
        }
    }
    
    @Override
    public Result queryResult(){
        return getResult(countTemplate,listTemplate,extTemplate,row,page);
    }

    @Override
    public CacheResultable queryCacheResult(String cacheKey) {
        return getCacheResult(cache,cacheKey,
                countTemplate,listTemplate,extTemplate,
                row,page);
    }

    @Override
    public EntityQueryable eq(String name, Object value) {
        listTemplate.eq(name, value);
        countTemplate.eq(name, value);
        if(extTemplate != null){
            extTemplate.eq(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable notEq(String name, Object value) {
        listTemplate.notEq(name, value);
        countTemplate.notEq(name, value);
        if(extTemplate != null){
            extTemplate.notEq(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable gt(String name, Number value) {
        listTemplate.gt(name, value);
        countTemplate.gt(name, value);
        if(extTemplate != null){
            extTemplate.gt(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable ge(String name, Number value) {
        listTemplate.ge(name, value);
        countTemplate.ge(name, value);
        if(extTemplate != null){
            extTemplate.ge(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable lt(String name, Number value) {
        listTemplate.lt(name, value);
        countTemplate.lt(name, value);
        if(extTemplate != null){
            extTemplate.lt(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable le(String name, Number value) {
        listTemplate.le(name, value);
        countTemplate.le(name, value);
        if(extTemplate != null){
            extTemplate.le(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable likeStart(String name, String value) {
        listTemplate.likeStart(name, value);
        countTemplate.likeStart(name, value);
        if(extTemplate != null){
            extTemplate.likeStart(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable likeAnywhere(String name, String value) {
        listTemplate.likeAnywhere(name, value);
        countTemplate.likeAnywhere(name, value);
        if(extTemplate != null){
            extTemplate.likeAnywhere(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable likeEnd(String name, String value) {
        listTemplate.likeEnd(name, value);
        countTemplate.likeEnd(name, value);
        if(extTemplate != null){
            extTemplate.likeEnd(name, value);
        }
        return this;
    }

    @Override
    public <Y extends Comparable<? super Y>> EntityQueryable between(String name, Y lo, Y hi) {
        listTemplate.between(name, lo,hi);
        countTemplate.between(name, lo,hi);
        if(extTemplate != null){
            extTemplate.between(name, lo,hi);
        }
        return this;
    }

    @Override
    public EntityQueryable in(String name, Collection<?> value) {
        listTemplate.in(name, value);
        countTemplate.in(name, value);
        if(extTemplate != null){
            extTemplate.in(name, value);
        }
        return this;
    }

    @Override
    public EntityQueryable in(String name, Object[] value) {
        return in(name,Arrays.asList(value));
    }

    @Override
    public EntityQueryable isNull(String name) {
        listTemplate.isNull(name);
        countTemplate.isNull(name);
        if(extTemplate != null){
            extTemplate.isNull(name);
        }
        return this;
    }

    @Override
    public EntityQueryable isNotNull(String name) {
        listTemplate.isNotNull(name);
        countTemplate.isNotNull(name);
        if(extTemplate != null){
            extTemplate.isNotNull(name);
        }
        return this;
    }

    @Override
    public EntityQueryable and() {
        listTemplate.and();
        countTemplate.and();
        if(extTemplate != null){
            extTemplate.and();
        }
        return this;
    }

    @Override
    public EntityQueryable and(Predicatesable x) {
        listTemplate.and(x);
        countTemplate.and(x);
        if(extTemplate != null){
            extTemplate.and(x);
        }
        return this;
    }

    @Override
    public EntityQueryable or() {
        listTemplate.or();
        countTemplate.or();
        if(extTemplate != null){
            extTemplate.or();
        }
        return this;
    }

    @Override
    public EntityQueryable or(Predicatesable x) {
        listTemplate.or(x);
        countTemplate.or(x);
        if(extTemplate != null){
            extTemplate.or(x);
        }
        return this;
    }

    @Override
    public EntityQueryable not(Predicatesable x) {
        listTemplate.not(x);
        countTemplate.not(x);
        if(extTemplate != null){
            extTemplate.not(x);
        }
        return null;
    }

    @Override
    public EntityQueryable orderAsc(String name) {
        listTemplate.orderAsc(name);
        return this;
    }

    @Override
    public EntityQueryable orderDesc(String name) {
        listTemplate.orderDesc(name);
        return this;
    }
    
    @Override
    public EntityQueryable setCache(ResultCacheable cache){
        this.cache = cache;
        return this;
    }
    
    @Override
    public EntityQueryable setRow(int row) {
        this.row = row;
        return this;
    }

    @Override
    public EntityQueryable setPage(int page) {
        this.page = page;
        return this;
    }

}
