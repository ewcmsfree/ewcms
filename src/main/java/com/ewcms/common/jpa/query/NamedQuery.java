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
class NamedQuery<V> implements HqlQueryable<V> {

    private Query query;
    
    public NamedQuery(final EntityManager entityManager,final String name) {
        query = entityManager.createNamedQuery(name);
    }
    @Override
    public HqlQueryable setParameter(final String param,final Object value) {
        query.setParameter(param, value);
        return this;
    }

    @Override
    public HqlQueryable setParameter(final int position,final Object value) {
        query.setParameter(position, value);
        return this;
    }

    @Override
    public HqlQueryable setParameter(final String name,final Date date,final TemporalType type) {
        query.setParameter(name, date, type);
        return this;
    }

    @Override
    public HqlQueryable setParameter(final int postiton,final Date date,final TemporalType type) {
        query.setParameter(postiton, date, type);
        return this;
    }

    @Override
    public HqlQueryable setParameter(String name, Calendar calendar, TemporalType type) {
        query.setParameter(name,calendar, type);
        return this;
    }

    @Override
    public HqlQueryable setParameter(int position, Calendar calendar, TemporalType type) {
        query.setParameter(position, calendar, type);
        return this;
    }

    @Override
    public List<V> getResultList() {
        return (List<V>)query.getResultList();
    }

}
