/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.TemporalType;

/**
 * Hibernate查询接口
 * 
 * @author wangwei
 */
public interface HqlQueryable<V> extends Queryable<V> {

    HqlQueryable setParameter(String param, Object value);

    HqlQueryable setParameter(int position, Object value);

    HqlQueryable setParameter(String name, Date date, TemporalType type);

    HqlQueryable setParameter(int position, Date date, TemporalType type);

    HqlQueryable setParameter(String name, Calendar calendar, TemporalType type);

    HqlQueryable setParameter(int position, Calendar calendar, TemporalType type);
}
