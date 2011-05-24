/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Order;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author wangwei
 */
abstract class AbstractCriteria<I extends Criteriaable, V, T> implements Criteriaable<I, V, T> {

    private List<Expression> exps = new ArrayList<Expression>();
    private List<Order> orders = new ArrayList<Order>();
    private List<Path> selects = new ArrayList<Path>();
    private EntityManager em;
    private CriteriaBuilder builder;
    private Root<T> root;
    private CriteriaQuery<Object> criteria;
    private boolean orOperator = false;

    public AbstractCriteria(final EntityManager em, final Class<T> entityClass) {
        this.em = em;
        this.builder = em.getCriteriaBuilder();
        this.criteria = builder.createQuery(Object.class);
        this.root = criteria.from(entityClass);
    }

    @Override
    public List<Path> getSelects() {
        return this.selects;
    }

    @Override
    public List<Expression> getExpressions() {
        return this.exps;
    }

    @Override
    public List<Order> getOrders() {
        return this.orders;
    }

    @Override
    public Root<T> getRoot() {
        return root;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return this.builder;
    }

    @Override
    public CriteriaQuery getCriteriaQuery() {
        return this.criteria;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public I select(final String... names) {
        for (String name : names) {
            selects.add(root.get(name));
        }
        return (I) this;
    }

    @Override
    public I select(final SingularAttribute... attrs) {
        for (SingularAttribute attr : attrs) {
            selects.add(root.get(attr));
        }
        return (I) this;
    }

    protected void pushExpression(final Expression exp) {
        exps.add(exp);
    }

    private Expression popExpression() {
        if (exps.isEmpty()) {
            return null;
        }
        Expression exp = exps.get(exps.size() - 1);
        exps.remove(exp);
        return exp;
    }

    @Override
    public I eq(final String name, final Object value) {
        pushExpression(builder.equal(root.get(name), value));
        return (I) this;
    }

    @Override
    public I eq(final SingularAttribute attr, final Object value) {
        pushExpression(builder.equal(root.get(attr), value));
        return (I) this;
    }

    @Override
    public I notEq(final String name, final Object value) {
        pushExpression(builder.notEqual(root.get(name), value));
        return (I) this;
    }

    @Override
    public I notEq(final SingularAttribute attr, final Object value) {
        pushExpression(builder.notEqual(root.get(attr), value));
        return (I) this;
    }

    @Override
    public I gt(final String name, final Number value) {
        Path<Number> path = root.get(name);
        pushExpression(builder.gt(path, value));
        return (I) this;
    }

    @Override
    public I gt(final SingularAttribute attr, final Number value) {
        pushExpression(builder.gt(root.get(attr), value));
        return (I) this;
    }

    @Override
    public I ge(final String name, final Number value) {
        Path<Number> path = root.get(name);
        pushExpression(builder.ge(path, value));
        return (I) this;
    }

    @Override
    public I ge(final SingularAttribute attr, final Number value) {
        pushExpression(builder.ge(root.get(attr), value));
        return (I) this;
    }

    @Override
    public I lt(final String name, final Number value) {
        Path<Number> path = root.get(name);
        pushExpression(builder.lt(path, value));
        return (I) this;
    }

    @Override
    public I lt(final SingularAttribute attr, final Number value) {
        pushExpression(builder.lt(root.get(attr), value));
        return (I) this;
    }

    @Override
    public I le(final String name, final Number value) {
        Path<Number> path = root.get(name);
        pushExpression(builder.le(path, value));
        return (I) this;
    }

    @Override
    public I le(final SingularAttribute attr, final Number value) {
        pushExpression(builder.le(root.get(attr), value));
        return (I) this;
    }

    private String likePatter(final String patter, final String value) {
        return String.format(patter, value);
    }

    @Override
    public I likeStart(final String name, final String value) {
        Path<String> path = root.get(name);
        String patter = likePatter("%s%%", value);
        pushExpression(builder.like(path, patter));
        return (I) this;
    }

    @Override
    public I likeStart(final SingularAttribute attr, final String value) {
        String patter = likePatter("%s%%", value);
        pushExpression(builder.like(root.get(attr), patter));
        return (I) this;
    }

    @Override
    public I likeAnywhere(final String name, final String value) {
        Path<String> path = root.get(name);
        String patter = likePatter("%%%s%%", value);
        pushExpression(builder.like(path, patter));
        return (I) this;
    }

    @Override
    public I likeAnywhere(final SingularAttribute attr, final String value) {
        String patter = likePatter("%%%s%%", value);
        pushExpression(builder.like(root.get(attr), patter));
        return (I) this;
    }

    @Override
    public I likeEnd(final String name, final String value) {
        Path<String> path = root.get(name);
        String patter = likePatter("%%%s", value);
        pushExpression(builder.like(path, patter));
        return (I) this;
    }

    @Override
    public I likeEnd(final SingularAttribute attr, final String value) {
        String patter = likePatter("%%%s", value);
        pushExpression(builder.like(root.get(attr), patter));
        return (I) this;
    }

    @Override
    public I between(final String name, final Comparable lo, final Comparable hi) {
        Path<Comparable> path = root.get(name);
        pushExpression(builder.between(path, lo, hi));
        return (I) this;
    }

    @Override
    public I between(final SingularAttribute attr, final Comparable lo, final Comparable hi) {
        pushExpression(builder.between(root.get(attr), lo, hi));
        return (I) this;
    }

    @Override
    public I in(final String name, final Collection<?> value) {
        pushExpression(builder.in(root.get(name)).value(value));
        return (I) this;
    }

    @Override
    public I in(final SingularAttribute attr, final Collection<?> value) {
        pushExpression(builder.in(root.get(attr)).value(value));
        return (I) this;
    }

    @Override
    public I in(final String name, final Object[] value) {
        return in(name, Arrays.asList(value));
    }

    @Override
    public I in(final SingularAttribute attr, final Object[] value) {
        return in(attr, Arrays.asList(value));
    }

    @Override
    public I isNull(final String name) {
        pushExpression(builder.isNull(root.get(name)));
        return (I) this;
    }

    @Override
    public I isNull(final SingularAttribute attr) {
        pushExpression(builder.isNull(root.get(attr)));
        return (I) this;
    }

    @Override
    public I isNotNull(final String name) {
        pushExpression(builder.isNotNull(root.get(name)));
        return (I) this;
    }

    @Override
    public I isNotNull(final SingularAttribute attr) {
        pushExpression(builder.isNotNull(root.get(attr)));
        return (I) this;
    }

    @Override
    public I and() {
        orOperator = false;
        return (I) this;
    }

    @Override
    public I and(final Criteriaable x) {
        Expression expX = popExpression();
        Expression expY = popExpression();

        if (expX != null && expY != null) {
            pushExpression(builder.and(expX, expY));
        }
        return (I) this;
    }

    @Override
    public I or() {
        orOperator = true;
        return (I) this;
    }

    @Override
    public I or(final Criteriaable x) {
        Expression expX = popExpression();
        Expression expY = popExpression();

        if (expX != null && expY != null) {
            pushExpression(builder.or(expX, expY));
        }

        return (I) this;
    }

    @Override
    public I not(final Criteriaable x) {
        Expression exp = popExpression();
        if (exp != null) {
            pushExpression(builder.not(exp));
        }
        return (I) this;
    }

    @Override
    public I orderAsc(final String name) {
        orders.add(builder.asc(root.get(name)));
        return (I) this;
    }

    @Override
    public I orderAsc(final SingularAttribute attr) {
        orders.add(builder.asc(root.get(attr)));
        return (I) this;
    }

    @Override
    public I orderDesc(final String name) {
        orders.add(builder.desc(root.get(name)));
        return (I) this;
    }

    @Override
    public I orderDesc(final SingularAttribute attr) {
        orders.add(builder.desc(root.get(attr)));
        return (I) this;
    }

    protected void builder() {
        builderSelect();
        builderWhere();
        builderOrder();
    }

    protected void builderSelect() {
        if (selects.isEmpty()) {
            criteria.select(root);
        } else {
            Path[] paths = new Path[selects.size()];
            selects.toArray(paths);
            criteria.select(builder.array(paths));
        }
    }

    protected void builderWhere() {

        if (exps.isEmpty()) {
            return;
        }
        while (exps.size() > 1) {
            if (orOperator) {
                or(this);
            } else {
                and(this);
            }
        }
        criteria.where(exps.get(0));
    }

    protected void builderOrder() {
        criteria.orderBy(orders);
    }

    @Override
    public List<V> getResultList() {
        builder();
        return (List<V>) em.createQuery(criteria).getResultList();
    }
}
