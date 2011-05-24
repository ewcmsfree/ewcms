/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author wangwei
 */
class HqlPageQuery<V> implements HqlPageQueryable<V> {

    private int rows = 15;
    private int page = 0;
    private int count = -1;
    private Query query;
    private Query countQuery;
    private Query sumQuery;

    public HqlPageQuery(final EntityManager entityManager,final String hql,final String countHql) {
        this(entityManager, hql, countHql, null);
    }

    public HqlPageQuery(final EntityManager entityManager,final String hql,final String countHql,final String sumHql) {
        query = entityManager.createQuery(hql);
        countQuery = entityManager.createQuery(countHql);
        sumQuery = (sumHql == null ? null : entityManager.createQuery(sumHql));
    }

    @Override
    public HqlQueryable setParameter(final String param,final Object value) {
        query.setParameter(param, value);
        countQuery.setParameter(param, value);
        if (sumQuery != null) {
            sumQuery.setParameter(param, value);
        }
        return this;
    }

    @Override
    public HqlQueryable setParameter(final int position,final Object value) {
        query.setParameter(position, value);
        countQuery.setParameter(position, value);
        if (sumQuery != null) {
            sumQuery.setParameter(position, value);
        }
        return this;
    }

    @Override
    public HqlQueryable setParameter(final String name,final Date date,final TemporalType type) {
        query.setParameter(name, date, type);
        countQuery.setParameter(name, date, type);
        if (sumQuery != null) {
            sumQuery.setParameter(name, date, type);
        }
        return this;
    }

    @Override
    public HqlQueryable setParameter(final int position,final Date date,final TemporalType type) {
        query.setParameter(position, date, type);
        countQuery.setParameter(position, date, type);
        if (sumQuery != null) {
            sumQuery.setParameter(position, date, type);
        }
        return this;
    }

     @Override
    public HqlQueryable setParameter(final String name,final Calendar calendar,final TemporalType type) {
        query.setParameter(name, calendar, type);
        countQuery.setParameter(name, calendar, type);
        if (sumQuery != null) {
            sumQuery.setParameter(name, calendar, type);
        }
        return this;
    }

    @Override
    public HqlQueryable setParameter(final int position,final Calendar calendar,final TemporalType type) {
        query.setParameter(position, calendar, type);
        countQuery.setParameter(position, calendar, type);
        if (sumQuery != null) {
            sumQuery.setParameter(position, calendar, type);
        }
        return this;
    }

    @Override
    public List<V> getResultList() {
        return (List<V>) query.setFirstResult(getFirstRow()).setMaxResults(rows).getResultList();
    }

    @Override
    public HqlPageQueryable setRows(final int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public HqlPageQueryable setPage(final int page) {
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
        return PageUtil.pageCount(count(),rows);
    }

    @Override
    public int count() {
        if (count == -1) {
            count = ((Long) countQuery.getSingleResult()).intValue();
        }
        return count;
    }

    @Override
    public Object sum() {
        if (sumQuery == null) {
            return null;
        } else {
            return PageUtil.defaultZero(sumQuery.getSingleResult());
        }
    }
}
