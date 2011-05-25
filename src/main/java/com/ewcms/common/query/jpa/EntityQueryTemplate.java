/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class EntityQueryTemplate implements Predicatesable,QueryTemplateable{
    private static final int NO_SETTING = -1;
    
    private EntityManager em;
    private CriteriaBuilder builder;
    private Root<?> root;
    private CriteriaQuery<Object> criteria;
    private List<Predicate> predicates = new ArrayList<Predicate>();
    private List<Selection<?>> selections = new ArrayList<Selection<?>>();
    private List<Order> orders = new ArrayList<Order>();
    
    private int startPosition = NO_SETTING;
    private int maxResults = NO_SETTING;
    
    public EntityQueryTemplate(EntityManager em,Class<?> entityClass) {
        this.em = em;
        this.builder = em.getCriteriaBuilder();
        this.criteria = builder.createQuery();
        this.root = criteria.from(entityClass);
    }
    
    void pushPredicate(final Predicate predicate) {
        predicates.add(predicate);
    }

    Predicate popPredicate() {
        if (predicates.isEmpty()) {
            return null;
        }
        Predicate predicate = predicates.get(predicates.size() - 1);
        predicates.remove(predicate);
        return predicate;
    }
    
    <E> Path<E> get(final String name){
        if(name.indexOf(".") > -1){
            String attrs[] = name.split("\\.");
            From<?,?> join = root;
            for(int i = 0 ; i < attrs.length - 1; i++){
                join = join.join(attrs[i]);
            }
            return join.get(attrs[attrs.length-1]);
        }else{
            return root.get(name);
        }
    }
    
    public EntityQueryTemplate select(String name){
        selections.add(root.get(name));
        return this;
    }
    
    public EntityQueryTemplate select(SelectCallback callback){
        selections.add(callback.select(builder, root));
        return this;
    }
    
    public EntityQueryTemplate multiselect(String... names){
        for(String name : names){
            selections.add(root.get(name));
        }
        return this;
    }
    
    @Override
    public EntityQueryTemplate eq(final String name, final Object value) {
        pushPredicate(builder.equal(get(name), value));
        return this;
    }

    @Override
    public EntityQueryTemplate notEq(final String name, final Object value) {
        pushPredicate(builder.notEqual(get(name), value));
        return this;
    }

    @Override
    public EntityQueryTemplate gt(final String name, final Number value) {
        Path<Number> path = get(name);
        pushPredicate(builder.gt(path, value));
        return this;
    }

    @Override
    public EntityQueryTemplate ge(final String name, final Number value) {
        Path<Number> path = get(name);
        pushPredicate(builder.ge(path, value));
        return this;
    }

    @Override
    public EntityQueryTemplate lt(final String name, final Number value) {
        Path<Number> path = get(name);
        pushPredicate(builder.lt(path, value));
        return this;
    }

    @Override
    public EntityQueryTemplate le(final String name, final Number value) {
        Path<Number> path = get(name);
        pushPredicate(builder.le(path, value));
        return this;
    }

    private String likePatter(final String patter, final String value) {
        return String.format(patter, value);
    }

    @Override
    public EntityQueryTemplate likeStart(final String name, final String value) {
        Path<String> path = get(name);
        String patter = likePatter("%s%%", value);
        pushPredicate(builder.like(path, patter));
        return this;
    }

    @Override
    public EntityQueryTemplate likeAnywhere(final String name, final String value) {
        Path<String> path = get(name);
        String patter = likePatter("%%%s%%", value);
        pushPredicate(builder.like(path, patter));
        return this;
    }

    @Override
    public EntityQueryTemplate likeEnd(final String name, final String value) {
        Path<String> path = get(name);
        String patter = likePatter("%%%s", value);
        pushPredicate(builder.like(path, patter));
        return this;
    }

    @Override
    public <Y extends Comparable<? super Y>> EntityQueryTemplate between(final String name, final Y lo, final Y hi) {
        Path<Y> path = get(name);
        pushPredicate(builder.between(path, lo, hi));
        return this;
    }

    @Override
    public EntityQueryTemplate in(final String name, final Collection<?> value) {
        pushPredicate(builder.in(get(name)).value(value));
        return this;
    }

    @Override
    public EntityQueryTemplate in(final String name, final Object[] value) {
        return in(name, Arrays.asList(value));
    }

    @Override
    public EntityQueryTemplate isNull(final String name) {
        pushPredicate(builder.isNull(get(name)));
        return this;
    }

    @Override
    public EntityQueryTemplate isNotNull(final String name) {
        pushPredicate(builder.isNotNull(get(name)));
        return this;
    }

    @Override
    public EntityQueryTemplate and() {
        for ( ; predicates.size()>1 ; ) {
            and(this);
        }
        return this;
    }

    @Override
    public EntityQueryTemplate and(final Predicatesable query) {
        Predicate x = popPredicate();
        Predicate y = popPredicate();

        if (x != null && y != null) {
            pushPredicate(builder.and(x, y));
        }
        return this;
    }

    @Override
    public EntityQueryTemplate or() {
        for ( ; predicates.size()>1 ; ) {
            or(this);
        }
        return this;
    }

    @Override
    public EntityQueryTemplate or(final Predicatesable query) {
        Predicate x = popPredicate();
        Predicate y = popPredicate();

        if (x != null && y != null) {
            pushPredicate(builder.or(x, y));
        }

        return this;
    }

    @Override
    public EntityQueryTemplate not(final Predicatesable query) {
        Predicate x = popPredicate();
        if (x != null) {
            pushPredicate(builder.not(x));
        }
        return this;
    }
    
    public EntityQueryTemplate orderAsc(final String name) {
        orders.add(builder.asc(root.get(name)));
        return this;
    }
    
    public EntityQueryTemplate orderDesc(final String name) {
        orders.add(builder.desc(root.get(name)));
        return this;
    }
    
    @Override
    public EntityQueryTemplate setFirstResult(int startPosition){
        this.startPosition = startPosition;
        return this;
    }
    
    @Override
    public EntityQueryTemplate setMaxResults(int maxResults){
        this.maxResults = maxResults;
        return this;
    }
    
    protected void buildSelect(CriteriaQuery<Object> c){
        if(selections.isEmpty()){
            c.select(root);
        }else{
            if(selections.size() == 1){
                c.select(selections.get(0));
            }else{
                c.select(builder.array(selections.toArray(new Selection<?>[selections.size()])));
            }
        }
    }
    
    protected void buildWhere(CriteriaQuery<Object> c){
        if(!predicates.isEmpty()){
            and();
            c.where(predicates.get(0));    
        }
    }
    
    protected void buildOrder(CriteriaQuery<Object> c){
            c.orderBy(orders);
    }
    
    @Override
    public Object getResultSingle(){
        buildSelect(criteria);
        buildWhere(criteria);
        buildOrder(criteria);
        
        return em.createQuery(criteria).getSingleResult();
    }
    
    @Override
    public List<Object> getResultList(){
        buildSelect(criteria);
        buildWhere(criteria);
        buildOrder(criteria);
        
        TypedQuery<Object> query =  em.createQuery(criteria);
        
        if(startPosition != NO_SETTING){
            query.setFirstResult(startPosition);
        }
        if(maxResults != NO_SETTING){
            query.setMaxResults(maxResults);
        }
        
        return query.getResultList();
    }
        
    @Override
    public <T> T execute(QueryTemplateCallback<T> callback){
        return callback.doInQuery(em);
    }
}
