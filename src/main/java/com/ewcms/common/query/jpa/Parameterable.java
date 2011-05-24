/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.query.jpa;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

/**
 * 
 * @author wangwei
 */
public interface Parameterable {

    Parameterable setParameter(String name, Object value);

    Parameterable setParameter(int position, Object value);

    Parameterable setParameter(String name, Date date, TemporalType type);

    Parameterable setParameter(int position, Date date, TemporalType type);

    Parameterable setParameter(String name, Calendar calendar, TemporalType type);

    Parameterable setParameter(int position, Calendar calendar, TemporalType type);
}
