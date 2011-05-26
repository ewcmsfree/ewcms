package com.ewcms.common.query.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Certificate.class)
public abstract class Sex_ {
    
    public static volatile SingularAttribute<Sex, Integer> id;
    public static volatile SingularAttribute<Sex, String> name;
    
}
