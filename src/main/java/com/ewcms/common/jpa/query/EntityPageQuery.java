/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author wangwei
 */
class EntityPageQuery<V, T> extends AbstractCriteria<EntityPageQueryable, V, T> implements EntityPageQueryable<V, T> {

    private int rows = 15;
    private int page = 0;
    private int count = -1;
    private CountQueryImpl countQuery;
    private SumQueryImpl sumQuery;

    public EntityPageQuery(final EntityManager em, final Class<T> entityClass) {
        super(em, entityClass);
        countQuery = new CountQueryImpl(em, entityClass);
        sumQuery = new SumQueryImpl(em, entityClass);
    }

    @Override
    public EntityPageQueryable setRows(final int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public EntityPageQueryable setPage(final int page) {
        this.page = page;
        return this;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getFirstRow() {
        return page * rows;
    }

    @Override
    public int countPage() {
        return PageUtil.pageCount(count(), rows);
    }

    @Override
    public EntityPageQueryable eq(final String name, final Object value) {
        super.eq(name, value);
        countQuery.eq(name, value);
        sumQuery.eq(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable eq(final SingularAttribute attr, final Object value) {
        super.eq(attr, value);
        countQuery.eq(attr, value);
        sumQuery.eq(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable notEq(final String name, final Object value) {
        super.notEq(name, value);
        countQuery.notEq(name, value);
        sumQuery.notEq(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable notEq(final SingularAttribute attr, final Object value) {
        super.notEq(attr, value);
        countQuery.notEq(attr, value);
        sumQuery.notEq(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable gt(final String name, final Number value) {
        super.gt(name, value);
        countQuery.gt(name, value);
        sumQuery.gt(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable gt(final SingularAttribute attr, final Number value) {
        super.gt(attr, value);
        countQuery.gt(attr, value);
        sumQuery.gt(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable ge(final String name, final Number value) {
        super.ge(name, value);
        countQuery.ge(name, value);
        sumQuery.ge(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable ge(final SingularAttribute attr, final Number value) {
        super.ge(attr, value);
        countQuery.ge(attr, value);
        sumQuery.ge(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable lt(final String name, final Number value) {
        super.lt(name, value);
        countQuery.lt(name, value);
        sumQuery.lt(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable lt(final SingularAttribute attr, final Number value) {
        super.lt(attr, value);
        countQuery.lt(attr, value);
        sumQuery.lt(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable le(final String name, final Number value) {
        super.le(name, value);
        countQuery.le(name, value);
        sumQuery.le(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable le(final SingularAttribute attr, final Number value) {
        super.le(attr, value);
        countQuery.le(attr, value);
        sumQuery.le(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeStart(final String name, final String value) {
        super.likeStart(name, value);
        countQuery.likeStart(name, value);
        sumQuery.likeStart(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeStart(final SingularAttribute attr, final String value) {
        super.likeStart(attr, value);
        countQuery.likeStart(attr, value);
        sumQuery.likeStart(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeAnywhere(final String name, final String value) {
        super.likeAnywhere(name, value);
        countQuery.likeAnywhere(name, value);
        sumQuery.likeAnywhere(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeAnywhere(final SingularAttribute attr, final String value) {
        super.likeAnywhere(attr, value);
        countQuery.likeAnywhere(attr, value);
        sumQuery.likeAnywhere(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeEnd(final String name, final String value) {
        super.likeEnd(name, value);
        countQuery.likeEnd(name, value);
        sumQuery.likeEnd(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable likeEnd(final SingularAttribute attr, final String value) {
        super.likeEnd(attr, value);
        countQuery.likeEnd(attr, value);
        sumQuery.likeEnd(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable between(final String name, final Comparable lo, final Comparable hi) {
        super.between(name, lo, hi);
        countQuery.between(name, lo, hi);
        sumQuery.between(name, lo, hi);
        return this;
    }

    @Override
    public EntityPageQueryable between(final SingularAttribute attr, final Comparable lo, final Comparable hi) {
        super.between(attr, lo, hi);
        countQuery.between(attr, lo, hi);
        sumQuery.between(attr, lo, hi);
        return this;
    }

    @Override
    public EntityPageQueryable in(final String name, final Collection<?> value) {
        super.in(name, value);
        countQuery.in(name, value);
        sumQuery.in(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable in(final SingularAttribute attr, final Collection<?> value) {
        super.in(attr, value);
        countQuery.in(attr, value);
        sumQuery.in(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable in(final String name, final Object[] value) {
        super.in(name, value);
        countQuery.in(name, value);
        sumQuery.in(name, value);
        return this;
    }

    @Override
    public EntityPageQueryable in(final SingularAttribute attr, final Object[] value) {
        super.in(attr, value);
        countQuery.in(attr, value);
        sumQuery.in(attr, value);
        return this;
    }

    @Override
    public EntityPageQueryable isNull(final String name) {
        super.isNull(name);
        countQuery.isNull(name);
        sumQuery.isNull(name);
        return this;
    }

    @Override
    public EntityPageQueryable isNull(final SingularAttribute attr) {
        super.isNull(attr);
        countQuery.isNull(attr);
        sumQuery.isNull(attr);
        return this;
    }

    @Override
    public EntityPageQueryable isNotNull(final String name) {
        super.isNotNull(name);
        countQuery.isNotNull(name);
        sumQuery.isNotNull(name);
        return this;
    }

    @Override
    public EntityPageQueryable isNotNull(final SingularAttribute attr) {
        super.isNotNull(attr);
        countQuery.isNotNull(attr);
        sumQuery.isNotNull(attr);
        return this;
    }

    @Override
    public EntityPageQueryable and(final Criteriaable x) {
        super.and(x);
        countQuery.and(x);
        sumQuery.and(x);
        return this;
    }

    @Override
    public EntityPageQueryable or(final Criteriaable x) {
        super.or(x);
        countQuery.or(x);
        sumQuery.or(x);
        return this;
    }

    @Override
    public EntityPageQueryable not(final Criteriaable x) {
        super.not(x);
        countQuery.not(x);
        sumQuery.not(x);
        return this;
    }

    @Override
    public List<V> getResultList() {
        builder();
        CriteriaQuery<T> criteria = this.getCriteriaQuery();
        return (List<V>) getEntityManager().
                createQuery(criteria).
                setFirstResult(getFirstRow()).
                setMaxResults(rows).
                getResultList();
    }

    @Override
    public int count() {
        return count == -1 ? (count = countQuery.count()) : count;
    }

    class CountQueryImpl<T> extends AbstractCriteria<Criteriaable, Long, T> {

        public CountQueryImpl(final EntityManager em, final Class<T> entityClass) {
            super(em, entityClass);
        }

        @Override
        protected void builderSelect() {
            getCriteriaQuery().select(getCriteriaBuilder().count(getRoot()));
        }

        public int count() {
            builder();
            Long countRows = (Long) getEntityManager().
                    createQuery(getCriteriaQuery()).
                    getSingleResult();
            return countRows.intValue();
        }
    }
    private boolean isSum = false;

    @Override
    public EntityPageQueryable setSumColumns(String... names) {
        sumQuery.select(names);
        isSum = true;
        return this;
    }

    @Override
    public EntityPageQueryable setSumColumns(SingularAttribute... attrs) {
        sumQuery.select(attrs);
        isSum = true;
        return this;
    }

    @Override
    public Object sum() {
        return isSum ? sumQuery.sum() : null;
    }

    class SumQueryImpl<T> extends AbstractCriteria<Criteriaable, Object, T> {

        public SumQueryImpl(final EntityManager em, final Class<T> entityClass) {
            super(em, entityClass);
        }

        @Override
        protected void builderSelect() {
            Expression[] exps = new Expression[this.getSelects().size()];
            for (int i = 0; i < exps.length; ++i) {
                exps[i] = getCriteriaBuilder().sum(this.getSelects().get(i));
            }
            this.getCriteriaQuery().multiselect(exps);
        }

        public Object sum() {
            builder();
            Object value = getEntityManager().createQuery(getCriteriaQuery()).getSingleResult();
            return PageUtil.defaultZero(value);
        }
    }
}
