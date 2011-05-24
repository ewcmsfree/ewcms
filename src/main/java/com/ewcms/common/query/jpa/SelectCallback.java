package com.ewcms.common.query.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public interface SelectCallback {

    Selection<?> select(CriteriaBuilder builder,Root<?> root);
    
}
