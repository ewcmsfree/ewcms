/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
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
