/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;


public interface ResultCacheable {
    
    int getMaxResult();
    
    int getNewsResult();

    CacheResultable putResultInCache(CacheResultable result);
    
    CacheResultable getResultFromCache(String key);
    
    void removeResultFromCache(String key);

}
