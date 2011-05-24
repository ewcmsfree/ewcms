package com.ewcms.common.query.cache;


public interface ResultCacheable {
    
    int getMaxResult();
    
    int getNewsResult();

    CacheResultable putResultInCache(CacheResultable result);
    
    CacheResultable getResultFromCache(String key);
    
    void removeResultFromCache(String key);

}
