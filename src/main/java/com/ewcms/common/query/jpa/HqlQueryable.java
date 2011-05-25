/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.TemporalType;

import com.ewcms.common.query.Queryable;

public interface HqlQueryable extends Parameterable,Queryable{

    @Override
    HqlQueryable setRow(int row);
    
    @Override
    HqlQueryable setPage(int page);
    
    @Override
    HqlQueryable setParameter(String param, Object value);

    @Override
    HqlQueryable setParameter(int position, Object value);

    @Override
    HqlQueryable setParameter(String name, Date date, TemporalType type);

    @Override
    HqlQueryable setParameter(int position, Date date, TemporalType type);

    @Override
    HqlQueryable setParameter(String name, Calendar calendar, TemporalType type);

    @Override
    HqlQueryable setParameter(int position, Calendar calendar, TemporalType type);

}
