/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.EntityManager;

/**
 *
 * @author wangwei
 */
class EntityQuery<V,T> extends AbstractCriteria<EntityQueryable,V,T> implements EntityQueryable<V,T> {

    public EntityQuery(final EntityManager em,final Class<T> entityClass) {
        super(em,entityClass);
    }   
}
