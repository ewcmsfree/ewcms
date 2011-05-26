/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import java.util.ArrayList;

import net.sf.ehcache.CacheManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/common/query/applicationContext.xml"})
public class EhcacheResultCacheTest {

    @Autowired
    private CacheManager cacheManager;
    
    private EhcacheResultCache cache ;
    
    @Before
    public void defore(){
        cache = new EhcacheResultCache();
        cache.setCacheManager(cacheManager);
    }
    
    @Test
    public void testPutResultInCacheCacheKeyIsNull(){
        CacheResult result = new CacheResult(null,100,new ArrayList<Object>());
        result = (CacheResult)cache.putResultInCache(result);
        Assert.assertNotNull(result.getCacheKey());
    }
    
    @Test
    public void testPutResultInCacheAndGetResultFromCache(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        cache.putResultInCache(result);
        Assert.assertNotNull(cache.getResultFromCache("testCacheKey"));
    }
    
    @Test
    public void testRemoveResultFromCache(){
        cache.removeResultFromCache("testCacheKey");
        Assert.assertNull(cache.getResultFromCache("testCacheKey"));
    }
}
