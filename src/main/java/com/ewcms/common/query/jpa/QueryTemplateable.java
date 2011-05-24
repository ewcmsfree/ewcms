package com.ewcms.common.query.jpa;

import java.util.List;

public interface QueryTemplateable {

    QueryTemplateable setFirstResult(int startPosition);
    
    QueryTemplateable setMaxResults(int maxResults);
    
    Object getResultSingle();
    
    List<Object> getResultList();
    
    public <T> T execute(QueryTemplateCallback<T> callback);
}
