/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.common.query.model.Certificate;

public class CacheResultTest {
    
    @Test
    public void testGetPageCount(){
        CacheResult result = new CacheResult("testCacheKey",20,new ArrayList<Object>()).setRow(20);
        
        int pageCount = result.getPageCount();
        Assert.assertEquals(1, pageCount);
        
        result = new CacheResult("testCacheKey",21,new ArrayList<Object>()).setRow(20);
        pageCount = result.getPageCount();
        Assert.assertEquals(2, pageCount);
    }
    
    @Test
    public void testThanCountResultList(){
        CacheResult result = new CacheResult("testCacheKey",10,new ArrayList<Object>())
            .setRow(20)
            .setPage(1);
        
        initResultList(result,10);
        Assert.assertEquals(0, result.getResultList().size());
    }
    
    @Test
    public void testCacheGetResultList(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>())
            .setRow(30)
            .setPage(1);
        
        initResultList(result,100);
        Assert.assertEquals(30, result.getResultList().size());
        
        result.setPage(3);
        Assert.assertEquals(10, result.getResultList().size());
    }
    
    @Test
    public void testIsLoaded(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>())
            .setRow(20);
        
        initResultList(result,50);
        result.setPage(3);
        Assert.assertFalse(result.isLoaded());
        
        result.setPage(2);
        Assert.assertFalse(result.isLoaded());
        
        result.setPage(1);
        Assert.assertTrue(result.isLoaded());
    }
    
    @Test
    public void testThanCountIsLoaded(){
        CacheResult result = new CacheResult("testCacheKey",10,new ArrayList<Object>())
        .setRow(20);
        
        result.setPage(6);
        Assert.assertTrue(result.isLoaded());
    }
    
    private void initResultList(CacheResult result,int size){
        List<Object> list = new ArrayList<Object>();
        for(int i = 0 ; i < size ; i++){
            Certificate c = new Certificate();
            c.setId(String.valueOf(i));
            list.add(c);
        }
        result.appendResultList(list);
    }
    
    @Test
    public void testAddResultList(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        
        initResultList(result,50);
        result.setPage(0).setRow(50);
        Assert.assertEquals(50, result.getResultList().size());
        
        initResultList(result,60);
        result.setPage(1).setRow(50);
        Assert.assertEquals(50, result.getResultList().size());
        result.setPage(2).setRow(50);
        Assert.assertEquals(0, result.getResultList().size());
    }
    
    @Test
    public void testEqualsIsTrue(){
        List<Object> extList = new ArrayList<Object>();
        CacheResult a = new CacheResult("cacheKey",10,extList);
        initResultList(a,10);
        CacheResult b = new CacheResult("cacheKey",10,extList);
        initResultList(b,10);
        
        Assert.assertTrue(a.equals(b));
    }
    
    @Test
    public void testCreateNewsModifiedIsTrue(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        Assert.assertTrue(result.isModified());
    }
    
    @Test
    public void testCloneModifiedIsFalse(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        CacheResult newResult = new CacheResult(result);
        Assert.assertFalse(newResult.isModified());
    }
    
    @Test
    public void testChangeCacheKeyModifiedIsTrue(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        CacheResult newResult = new CacheResult("111111",result);
        Assert.assertTrue(newResult.isModified());
    }
    
    @Test
    public void testAddResultListModifiedIsTrue(){
        CacheResult result = new CacheResult("testCacheKey",100,new ArrayList<Object>());
        CacheResult newResult = new CacheResult(result);
        Assert.assertFalse(newResult.isModified());
        initResultList(newResult,50);
        Assert.assertTrue(newResult.isModified());
    }
}
