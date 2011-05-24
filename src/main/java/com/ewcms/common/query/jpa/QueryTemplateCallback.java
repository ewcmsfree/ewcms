package com.ewcms.common.query.jpa;

import javax.persistence.EntityManager;

public interface QueryTemplateCallback<T> {

    public T doInQuery(EntityManager em);
}
