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
