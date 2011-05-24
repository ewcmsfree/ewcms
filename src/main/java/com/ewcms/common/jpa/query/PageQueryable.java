/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */

package com.ewcms.common.jpa.query;

/**
 *
 * @author wangwei
 */
public interface PageQueryable<I,V> extends Queryable<V>{

    I setRows(int rows);

    int getRows();

    I setPage(int page);

    int getPage();

    int getFirstRow();

    int countPage();

    int count();
}
