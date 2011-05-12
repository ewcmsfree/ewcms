/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */

package com.ewcms.comm.jpa.query;

/**
 *
 * @author wangwei
 */
public interface HqlPageQueryable<V> extends HqlQueryable<V>,PageQueryable<HqlPageQueryable,V> {

    Object sum() ;
}
