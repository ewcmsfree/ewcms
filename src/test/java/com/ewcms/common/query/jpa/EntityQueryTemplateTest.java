/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.jpa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ewcms.common.query.model.Certificate;
import com.ewcms.common.query.model.LimitLog;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/common/query/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class EntityQueryTemplateTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private QueryInit entityQueryInit;
    
    private EntityQueryTemplate template;

    @Before
    public void before()throws IOException{
        template = new EntityQueryTemplate(entityManagerFactory.createEntityManager(),Certificate.class);
        entityQueryInit.initDatabase();
    }
    
    @Test
    public void testGet() {
        Path<?> path = template.get("limitLogs.date");
        Assert.assertNotNull(path);
    }
    
    @Test
    public void testEq() {
        template.eq("id", "72300125");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(((Certificate)list.get(0)).getName(), "王伟");
    }

    @Test
    public void testNotEq() {
        template.notEq("id", "72300125");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 159);
    }

    @Test
    public void testGt() {
        template.gt("limit", 3000);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 13);
    }
    
    @Test
    public void testGe() {
        template.ge("limit", 3000);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 18);
    }

    @Test
    public void testLt() {
        template.lt("limit", 3000);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 142);
    }
    
    @Test
    public void testLe() {
        template.le("limit", 3000);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 147);
    }

    @Test
    public void testLikeStart() {
        template.likeStart("name", "王");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 6);
    }
    
    @Test
    public void testLikeEnd() {
        template.likeEnd("name", "伟");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 4);
    }

    @Test
    public void testLikeAny() {
        template.likeAnywhere("name", "伟");
        List<Object> list = template.getResultList();
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

        template.between("brithdate", start, end);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 31);
    }

    @Test
    public void testIn() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");

        template.in("name", names);
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testIsNull() {
        template.isNull("name");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testIsNotNull() {
        template.isNotNull("name");
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 160);
    }

    @Test
    public void testAnd() {
        template.eq("name", "王伟").and(template.eq("id", "72300125"));
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testOr() {
        template.eq("name", "王伟").or(template.eq("name", "周冬初"));
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testComplex() {
        template.eq("name", "周冬初").or(template.eq("name", "王伟").and(template.eq("id", "72300125")));
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testNot() {
        template.not(template.likeEnd("name", "伟"));
        List<Object> list = template.getResultList();
        Assert.assertTrue(list.size() == 156);
    }

    @Test
    public void testCompoundAnd() {
        template.eq("id", "72300125").eq("name", "王伟");
        List<Object> list = template.getResultList();
        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void testCompoundOr(){
        template.eq("id", "72300126").eq("name", "王伟").or();
        List<Object> list = template.getResultList();
        Assert.assertEquals(list.size(), 2);
    }

    @Test
    public void testOrderAsc() {
        template.orderAsc("id");
        List<Object> list = template.getResultList();

        int first_id = Integer.valueOf(((Certificate)list.get(0)).getId());
        int second_id = Integer.valueOf(((Certificate)list.get(2)).getId());
        int end_id = Integer.valueOf(((Certificate)list.get(list.size() - 1)).getId());

        Assert.assertTrue(end_id > second_id);
        Assert.assertTrue(second_id > first_id);
    }

    @Test
    public void testOrderDesc() {
        template.orderDesc("id");
        List<Object> list = template.getResultList();

        int first_id = Integer.valueOf(((Certificate)list.get(0)).getId());
        int second_id = Integer.valueOf(((Certificate)list.get(2)).getId());
        int end_id = Integer.valueOf(((Certificate)list.get(list.size() - 1)).getId());
        
        Assert.assertTrue(first_id > second_id);
        Assert.assertTrue(second_id > end_id);
    }
    
    @Test
    public void testStartPosition(){
       template.setFirstResult(100);
       List<Object> list = template.getResultList();
       Assert.assertTrue(list.size() == 60);
    }
    
    @Test
    public void testMaxResults(){
        template.setMaxResults(20);
        List<Object> list = template.getResultList();
        Assert.assertEquals(20, list.size());
    }
    
    @Test
    public void testStartPositionAndMaxResults(){
        List<Object> list = template
            .setFirstResult(150)
            .setMaxResults(20)
            .getResultList();
        
        Assert.assertTrue(list.size() == 10);
    }
    
    @Test
    public void testLimitLogs(){
        Date start = getDate(2007, 0, 1);
        Date end = getDate(2007, 0, 10);
        EntityQueryTemplate joinTemplate = 
            new EntityQueryTemplate(entityManagerFactory.createEntityManager(),LimitLog.class);
        joinTemplate.eq("certificate.name", "李沐华");
        joinTemplate.between("date", start,end);
        List<Object> list = joinTemplate.getResultList();
        Assert.assertTrue(list.size()==3);
    }
    
    @Test
    public void testSelectCallback(){
        Long count =(Long)template.select(new SelectCallback(){
            @Override
            public Selection<?> select(CriteriaBuilder builder, Root<?> root) {
                 return builder.count(root.get("id"));
            }
        }).getResultSingle();
        Assert.assertTrue(count == 160);
    }
    
    @Test
    public void testExecute(){
        
        List<Object[]> list = template.execute(new QueryTemplateCallback<List<Object[]>>(){

            @Override
            public List<Object[]> doInQuery(EntityManager em) {
                Class<?> entityClass= Certificate.class;
                CriteriaBuilder builder =em.getCriteriaBuilder();
                CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
                Root<?> root = criteriaQuery.from(entityClass);
                criteriaQuery.select(builder.array(new Selection[]{root.get("id"),root.get("name")}));
                criteriaQuery.where(builder.equal(root.get("id"), "72300125"));
                
                return em.createQuery(criteriaQuery).getResultList();
            }
        });
        
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).length == 2);
    }
}

