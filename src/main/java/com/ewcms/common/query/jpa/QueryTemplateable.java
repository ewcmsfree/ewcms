/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.util.List;

public interface QueryTemplateable {

    QueryTemplateable setFirstResult(int startPosition);
    
    QueryTemplateable setMaxResults(int maxResults);
    
    Object getResultSingle();
    
    List<Object> getResultList();
    
    public <T> T execute(QueryTemplateCallback<T> callback);
}
