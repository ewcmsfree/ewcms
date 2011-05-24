/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.List;

/**
 *
 * @author wangwei
 */
public interface Queryable<V> {

    List<V> getResultList();
}
