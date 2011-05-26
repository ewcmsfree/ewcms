/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class EhcacheResultCache implements ResultCacheable{
    
    private static final Logger logger = LoggerFactory.getLogger(EhcacheResultCache.class);
    
    private static final String CACHE_NAME = "queryCache";
    private static final int DEFAULT_MAX_RESULT = 10000;
    private static final int DEFAULT_NEWS_RESULT= 500;
    
    private CacheManager cacheManager;
    private String cacheName = CACHE_NAME;
    private GeneratorCacheKeyable generatorCacheKey = new DefaultGeneratorCacheKey();
    private int maxResult = DEFAULT_MAX_RESULT;
    private int newsResult = DEFAULT_NEWS_RESULT;
        
    private Cache getCache(){
        Cache cache = cacheManager.getCache(cacheName);
        Assert.notNull(cache,"cache is null");
        return cache;
    }
    
    @Override
    public int getMaxResult() {
        return maxResult;
    }

    @Override
    public int getNewsResult() {
       return newsResult;
    }

    @Override
    public CacheResultable putResultInCache(CacheResultable result) {
        Cache cache = getCache();
        
        if(result.getCacheKey()== null || result.getCacheKey().isEmpty()){
            String cacheKey = getCacheKey();
            result = new CacheResult(cacheKey,(CacheResult)result);
        }
        
        if(result.isModified()){
            Element element = new Element(result.getCacheKey(),result);
            cache.put(element);
            
            if(logger.isDebugEnabled()){
                logger.debug("Ehcache memory store size is {}",cache.calculateInMemorySize());
            }
        }
        return result;
    }
    
    private String getCacheKey(){
        Cache cache = getCache();
        String cacheKey;
        do{
            cacheKey = generatorCacheKey.generatorKey();
        }while(cache.isKeyInCache(cacheKey));
        return cacheKey;
    }

    @Override
    public CacheResultable getResultFromCache(String key) {
        Cache cache = getCache();
        Element element = cache.get(key);
        return element == null ? null : new CacheResult((CacheResult)element.getValue());
    }

    @Override
    public void removeResultFromCache(String key) {
        Cache cache = getCache();
        cache.remove(key);
    }
    
    public void setCacheManager(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }
    
    public void setCacheName(String cacheName){
        this.cacheName = cacheName;
    }
    
    public void setGeneratorCacheKey(GeneratorCacheKeyable generatorCacheKey){
        this.generatorCacheKey = generatorCacheKey;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public void setNewsResult(int newsResult) {
        this.newsResult = newsResult;
    }
}
