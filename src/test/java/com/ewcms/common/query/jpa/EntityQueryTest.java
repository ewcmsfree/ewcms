/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/common/query/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EntityQueryTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private QueryInit entityQueryInit;
    
    private EntityQuery query;
    
    @Before
    public void before()throws IOException{
        query = new EntityQuery(entityManagerFactory.createEntityManager(),Certificate.class);
        entityQueryInit.initDatabase();
    }
    
    @Test
    public void testEq() {
        query.eq("id", "72300125");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 1);
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(((Certificate)list.get(0)).getName(), "王伟");
    }

    @Test
    public void testNotEq() {
        query.notEq("id", "72300125");
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 159);
        Assert.assertEquals(159,list.size());
    }

    @Test
    public void testGt() {
        query.gt("limit", 3000);
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 13);
        Assert.assertTrue(list.size() == 13);
    }
    
    @Test
    public void testGe() {
        query.ge("limit", 3000);
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 18);
        Assert.assertTrue(list.size() == 18);
    }

    @Test
    public void testLt() {
        query.lt("limit", 3000);
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 142);
        Assert.assertTrue(list.size() == 142);
    }
    
    @Test
    public void testLe() {
        query.le("limit", 3000);
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 147);
        Assert.assertTrue(list.size() == 147);
    }

    @Test
    public void testLikeStart() {
        query.likeStart("name", "王");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 6);
        Assert.assertTrue(list.size() == 6);
    }
    
    @Test
    public void testLikeEnd() {
        query.likeEnd("name", "伟");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 4);
        Assert.assertTrue(list.size() == 4);
    }

    @Test
    public void testLikeAny() {
        query.likeAnywhere("name", "伟");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 4);
        Assert.assertTrue(list.size() == 4);
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return new Date(calendar.getTimeInMillis());
    }

    @Test
    public void testBetween() {
        Date start = getDate(1969, 11, 31);
        Date end = getDate(1980, 0, 1);

        query.between("brithdate", start, end);
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 31);
        Assert.assertTrue(list.size() == 31);
    }

    @Test
    public void testIn() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");

        query.in("name", names);
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 2);
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testIsNull() {
        query.isNull("name");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 0);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testIsNotNull() {
        query.isNotNull("name");
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(list.size() == 160);
    }

    @Test
    public void testAnd() {
        query.eq("name", "王伟").and(query.eq("id", "72300125"));
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 1);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testOr() {
        query.eq("name", "王伟").or(query.eq("name", "周冬初"));
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 2);
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testComplex() {
        query.eq("name", "周冬初").or(query.eq("name", "王伟").and(query.eq("id", "72300125")));
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 2);
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testNot() {
        query.not(query.likeEnd("name", "伟"));
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 156);
        Assert.assertTrue(list.size() == 156);
    }

    @Test
    public void testCompoundAnd() {
        query.eq("id", "72300125").eq("name", "王伟");
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 1);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testCompoundOr(){
        query.eq("id", "72300126").eq("name", "王伟").or();
        Resultable result = query.queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 2);
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testOrderAsc() {
        query.orderAsc("id");
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(list.size() == 160);

        int first_id = Integer.valueOf(((Certificate)list.get(0)).getId());
        int second_id = Integer.valueOf(((Certificate)list.get(2)).getId());
        int end_id = Integer.valueOf(((Certificate)list.get(list.size() - 1)).getId());

        Assert.assertTrue(end_id > second_id);
        Assert.assertTrue(second_id > first_id);
    }

    @Test
    public void testOrderDesc() {
        query.orderDesc("id");
        Resultable result = query.setRow(160).queryResult();
        List<Object> list = result.getResultList();
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(list.size() == 160);

        int first_id = Integer.valueOf(((Certificate)list.get(0)).getId());
        int second_id = Integer.valueOf(((Certificate)list.get(2)).getId());
        int end_id = Integer.valueOf(((Certificate)list.get(list.size() - 1)).getId());
        
        Assert.assertTrue(first_id > second_id);
        Assert.assertTrue(second_id > end_id);
    }
       
    @Test
    public void testPagination(){
         Resultable result = query.setRow(30).setPage(5).queryResult();
        
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(result.getResultList().size() == 10);
        Assert.assertTrue(result.getPageCount() == 6);
    }
    
    @Test
    public void testCountSelectionQuery(){
        Resultable result = new EntityQuery(entityManagerFactory.createEntityManager(),Certificate.class,new SelectCallback(){
            @Override
            public Selection<?> select(CriteriaBuilder builder, Root<?> root) {
                return builder.count(root.get("id"));
            }
        },null).setRow(160).queryResult();
        
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(result.getResultList().size() == 160);
    }
    
    @Test
    public void testExtSelectionQuery(){
         Resultable result = new EntityQuery(entityManagerFactory.createEntityManager(),Certificate.class,new SelectCallback(){
            @Override
            public Selection<?> select(CriteriaBuilder builder, Root<?> root) {
                Path<Number> path = root.get("limit");
                return builder.sum(path);
            }
        }).setRow(160).queryResult();
        
        Assert.assertTrue(result.getCount() == 160);
        Assert.assertTrue(result.getResultList().size() == 160);
        Assert.assertTrue(result.getExtList().size() == 1);
        int sumMoneyLimit = ((Integer)result.getExtList().get(0));
        Assert.assertTrue(sumMoneyLimit == 222873);
    }
    
    @Test
    public void testNullCacheGetCacheReult(){
        Resultable result = query
            .setRow(20)
            .setPage(3)
            .queryCacheResult("testCacheKey");
        
        Assert.assertEquals(20, result.getResultList().size());
    }
    
    @Test
    public void testMaxResultsIsNotLimit(){
        ResultCacheable cache = mock(ResultCacheable.class);
        when(cache.getMaxResult()).thenReturn(-1);
        when(cache.getNewsResult()).thenReturn(50);
        when(cache.getResultFromCache(any(String.class))).thenReturn(null);
        
        query.setCache(cache);
        
        Assert.assertEquals(160, query.getCacheCount(cache,160));
    }
    
    @Test
    public void testMaxResultsIsLimit(){
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
