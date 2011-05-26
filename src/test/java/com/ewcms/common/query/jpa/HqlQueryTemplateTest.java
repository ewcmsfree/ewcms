/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.query.jpa;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/common/query/applicationContext.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class HqlQueryTemplateTest {

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
        String hql = "From Certificate o Where o.id =:id";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        template.setParameter("id", "72300125");
        
        List<Object> list = template.getResultList();
        
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testSetParameterPosition() {
        String hql = "From Certificate o Where o.id =?1";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        template.setParameter(1, "72300125");
        
        List<Object> list = template.getResultList();
        
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testSetParameterDate() {
        String hql = "From Certificate o Where o.brithdate > :date";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        template.setParameter("date", new Date(calendar.getTimeInMillis()), TemporalType.DATE);
        
        List<Object> list = template.getResultList();
        
        Assert.assertEquals(13, list.size());
    }

    @Test
    public void testSetParameterDatePosition() {
        String hql = "From Certificate o Where o.brithdate > ?1";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        template.setParameter(1, new Date(calendar.getTimeInMillis()), TemporalType.DATE);
        
        List<Object> list = template.getResultList();
        
        Assert.assertEquals(13, list.size());
    }

    @Test
    public void testStartPosition(){
       String hql = "From Certificate o";
       HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
       template.setFirstResult(100);
       List<Object> list = template.getResultList();
       Assert.assertTrue(list.size() == 60);
    }
    
    @Test
    public void testMaxResults(){
        String hql = "From Certificate o";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        template.setMaxResults(20);
        List<Object> list = template.getResultList();
        Assert.assertEquals(20, list.size());
    }
    
    @Test
    public void testStartPositionAndMaxResults(){
        String hql = "From Certificate o";
        HqlQueryTemplate template = new HqlQueryTemplate(entityManagerFactory.createEntityManager(),hql);
        List<Object> list = template
            .setFirstResult(150)
            .setMaxResults(20)
            .getResultList();
        
        Assert.assertTrue(list.size() == 10);
    }
}
