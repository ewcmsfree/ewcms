/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Certificate.class)
public abstract class LimitLog_ {
    public static volatile SingularAttribute<LimitLog, Integer> id;
    public static volatile SingularAttribute<LimitLog, Certificate> certificate;
    public static volatile SingularAttribute<Certificate, Integer> money;
    public static volatile SingularAttribute<Certificate, Date> date;
}
