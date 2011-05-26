/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.query.jpa;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.cache.CacheResult;
import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.cache.ResultCacheable;
import com.ewcms.common.query.model.Certificate;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/common/query/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class HqlQueryTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private QueryInit entityQueryInit;
    
    @Before
    public void before()throws IOException{
        entityQueryInit.initDatabase();
    }
    
    @Test
    public void testSetParameter() {
        String where = "o.id =:id";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);
        
        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql, sumHql);
        query.setParameter("id", "72300125");

        Resultable result = query.queryResult();

        Assert.assertEquals(1,result.getCount());
        Assert.assertEquals(298,((Long)result.getExtList().get(0)).intValue());
        Assert.assertEquals(1,result.getResultList().size());
    }

    @Test
    public void testSetParameterPosition() {
        String where = "o.id =?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);

        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql, sumHql);
        query.setParameter(1, "72300125");

        Resultable result = query.queryResult();

        Assert.assertEquals(1,result.getCount());
        Assert.assertEquals(298,((Long)result.getExtList().get(0)).intValue());
        Assert.assertEquals(1,result.getResultList().size());
    }

    @Test
    public void testSetParameterDate() {
        String where = "o.brithdate > :date";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        Resultable result = query.setParameter(
                "date", new Date(calendar.getTimeInMillis()), TemporalType.DATE).queryResult();

        Assert.assertTrue(result.getCount() == 13);
    }

    @Test
    public void testSetParameterDatePosition() {
        String where = "o.brithdate > ?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        Resultable result = query.setParameter(
                1, new Date(calendar.getTimeInMillis()), TemporalType.DATE).queryResult();

        Assert.assertTrue(result.getCount() == 13);
    }
    
    @Test
    public void testNullCacheGetCacheReult(){
        String hql = "From Certificate o";
        String countHql = "Select count(o.id) From Certificate o";

        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        Resultable result = query
            .setRow(20)
            .setPage(3)
            .queryCacheResult("testCacheKey");
        
        Assert.assertEquals(20, result.getResultList().size());
    }
    
    @Test
    public void testMaxResultsIsNotLimit(){
        String hql = "From Certificate o";
        String countHql = "Select count(o.id) From Certificate o";
        HqlQuery query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        
        ResultCacheable cache = mock(ResultCacheable.class);
        when(cache.getMaxResult()).thenReturn(-1);
        when(cache.getNewsResult()).thenReturn(50);
        when(cache.getResultFromCache(any(String.class))).thenReturn(null);
        
        query.setCache(cache);
        
        Assert.assertEquals(160, query.getCacheCount(cache,160));
    }
    
    @Test
    public void testMaxResultsIsLimit(){
        String hql = "From Certificate o";
        String countHql = "Select count(o.id) From Certificate o";
        HqlQuery query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        ResultCacheable cache = mock(ResultCacheable.class);
        when(cache.getMaxResult()).thenReturn(100);
        when(cache.getNewsResult()).thenReturn(50);
        when(cache.getResultFromCache(any(String.class))).thenReturn(null);
        
        query.setCache(cache);
        
        Assert.assertEquals(100, query.getCacheCount(cache,160));
        Assert.assertEquals(99, query.getCacheCount(cache,99));
    }
    
    @Test
    public void testEnabledCacheGetCacheResultIsNull(){
        String hql = "From Certificate o";
        String countHql = "Select count(o.id) From Certificate o";
        HqlQuery query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        
        ResultCacheable cache = mock(ResultCacheable.class);
        when(cache.getMaxResult()).thenReturn(10000);
        when(cache.getNewsResult()).thenReturn(50);
        when(cache.getResultFromCache(any(String.class))).thenReturn(null);
        when(cache.putResultInCache(any(CacheResultable.class))).thenAnswer(new Answer<CacheResultable>(){
            @Override
            public CacheResultable answer(InvocationOnMock invocation)
                    throws Throwable {
                return (CacheResultable)invocation.getArguments()[0];
            }
        });
        
        Resultable result = query
            .setRow(20)
            .setPage(3)
            .setCache(cache)
            .queryCacheResult("testCacheKey");
        
        Assert.assertEquals(20, result.getResultList().size());
        Assert.assertEquals(160, result.getCount());
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
    public void testEnableCacheGetCacheResult(){
        String hql = "From Certificate o";
        String countHql = "Select count(o.id) From Certificate o";
        HqlQueryable query = new HqlQuery(entityManagerFactory.createEntityManager(), hql, countHql);
        
        ResultCacheable cache = mock(ResultCacheable.class);
        when(cache.getMaxResult()).thenReturn(10000);
        when(cache.getNewsResult()).thenReturn(50);
        CacheResult cacheResult = new CacheResult("testCacheKey",160,new ArrayList<Object>());
        initResultList(cacheResult,50);
        when(cache.getResultFromCache(any(String.class))).thenReturn(null);
        when(cache.putResultInCache(any(CacheResultable.class))).thenAnswer(new Answer<CacheResultable>(){
            @Override
            public CacheResultable answer(InvocationOnMock invocation)
                    throws Throwable {
                return (CacheResultable)invocation.getArguments()[0];
            }
        });
        
        Resultable result = query
            .setRow(30)
            .setPage(5)
            .setCache(cache)
            .queryCacheResult("testCacheKey");
    
        Assert.assertEquals(10, result.getResultList().size());
        Assert.assertEquals(160, result.getCount());
    }
}
