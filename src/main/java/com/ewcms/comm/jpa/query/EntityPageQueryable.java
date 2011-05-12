/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */

package com.ewcms.comm.jpa.query;

import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author wangwei
 */
public interface EntityPageQueryable<V,T> extends
        Criteriaable<EntityPageQueryable, V, T>, PageQueryable<EntityPageQueryable,V>{

    EntityPageQueryable setSumColumns(String... names);

    EntityPageQueryable setSumColumns(SingularAttribute... attrs);

    Object sum();
}