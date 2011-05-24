/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ewcms.common.jpa.query.AbstractCriteria;
import com.ewcms.common.jpa.query.Criteriaable;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/comm/jpa/dao/applicationContext.xml"}, inheritLocations = true)
public class AbstractCriteriaTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    private Criteriaable query;

    @Before
    public void before() {
        query = new CeriteriaImpl(
                entityManagerFactory.createEntityManager(),
                Certificate.class);
    }

    @Test
    public void testEqStaticMeta() {
        query.eq(Certificate_.id, "72300125");

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(list.get(0).getName(), "王伟");
    }

    @Test
    public void testEq() {
        query.eq("id", "72300125");
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(list.get(0).getName(), "王伟");
    }

    @Test
    public void testNotEqStaticMeta() {
        query.notEq(Certificate_.id, "72300125");
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 44078);

    }

    @Test
    public void testNotEq() {
        query.notEq("id", "72300125");
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 44078);
    }

    @Test
    public void testGtStaticMeta() {
        query.gt(Certificate_.limit, 3000);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 5803);
    }

    @Test
    public void testGt() {
        query.gt("limit", 3000);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 5803);
    }

    @Test
    public void testGeStaticMeta() {
        query.ge(Certificate_.limit, 3000);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 6499);
    }

    @Test
    public void testGe() {
        query.ge("limit", 3000);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 6499);
    }

    @Test
    public void testLtStaticMeta() {
        query.lt(Certificate_.limit, 3000);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 37580);
    }

    @Test
    public void testLt() {
        query.lt("limit", 3000);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 37580);
    }

    @Test
    public void testLeStaticMeta() {
        query.le(Certificate_.limit, 3000);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 38276);
    }

    @Test
    public void testLe() {
        query.le("limit", 3000);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 38276);
    }

    @Test
    public void testLikeStartStaticMeta() {
        query.likeStart(Certificate_.name, "王");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 2374);
    }

    @Test
    public void testLikeStart() {
        query.likeStart("name", "王");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 2374);
    }

    @Test
    public void testLikeEndStaticMeta() {
        query.likeEnd(Certificate_.name, "伟");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 348);
    }

    @Test
    public void testLikeEnd() {
        query.likeEnd("name", "伟");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 348);
    }

    @Test
    public void testLikeAnyStaticMeta() {
        query.likeAnywhere(Certificate_.name, "伟");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 515);
    }

    @Test
    public void testLikeAny() {
        query.likeAnywhere("name", "伟");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 515);
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return new Date(calendar.getTimeInMillis());
    }

    @Test
    public void testBetweenStaticMeta() {
        Date start = getDate(1970, 0, 1);
        Date end = getDate(1980, 0, 1);

        query.between(Certificate_.brithdate, start, end);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 6589);
    }

    @Test
    public void testBetween() {
        Date start = getDate(1970, 0, 1);
        Date end = getDate(1980, 0, 1);

        query.between("brithdate", start, end);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 6589);
    }

    @Test
    public void testInStaticMeta() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");

        query.in(Certificate_.name, names);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 8);
    }

    @Test
    public void testIn() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");

        query.in("name", names);

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 8);
    }

    @Test
    public void testIsNullStaticMeta() {
        query.isNull(Certificate_.name);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testIsNull() {
        query.isNull("name");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testIsNotNullStaticMeta() {
        query.isNotNull(Certificate_.name);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 44079);
    }

    @Test
    public void testIsNotNull() {
        query.isNotNull("name");
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 44079);
    }

    @Test
    public void testAnd() {
        query.eq(Certificate_.name, "王伟").and(query.eq(Certificate_.id, "72300125"));
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testOr() {
        query.eq(Certificate_.name, "王伟").or(query.eq(Certificate_.name, "周冬初"));

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 8);
    }

    @Test
    public void testComplex() {
        query.eq(Certificate_.name, "周冬初").or(query.eq(Certificate_.name, "王伟").and(query.eq(Certificate_.id, "723001251")));
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testNot() {
        query.not(query.likeEnd(Certificate_.name, "伟"));
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 43731);
    }

    @Test
    public void testCompoundAnd() {
        query.eq(Certificate_.id, "72300125").eq(Certificate_.name, "王伟");

        List<Certificate> list = query.getResultList();
        Assert.assertEquals(list.size(), 1);
    }

    @Test
    public void testCompoundOr(){
        query.eq(Certificate_.id, "72300125").eq(Certificate_.name, "王伟").or();
        List<Certificate> list = query.getResultList();
        Assert.assertEquals(list.size(), 6);
    }

    @Test
    public void testOrderAscStaticMeta() {
        query.orderAsc(Certificate_.id);
        List<Certificate> list = query.getResultList();

        int first_id = Integer.valueOf(list.get(0).getId());
        int second_id = Integer.valueOf(list.get(2).getId());
        int end_id = Integer.valueOf(list.get(list.size() - 1).getId());

        Assert.assertTrue(end_id > second_id);
        Assert.assertTrue(second_id > first_id);
    }

    @Test
    public void testOrderAsc() {
        query.orderAsc("id");
        List<Certificate> list = query.getResultList();

        int first_id = Integer.valueOf(list.get(0).getId());
        int second_id = Integer.valueOf(list.get(2).getId());
        int end_id = Integer.valueOf(list.get(list.size() - 1).getId());

        Assert.assertTrue(end_id > second_id);
        Assert.assertTrue(second_id > first_id);
    }

    @Test
    public void testOrderDescStaticMeta() {
        query.orderDesc(Certificate_.id);
        List<Certificate> list = query.getResultList();

        int first_id = Integer.valueOf(list.get(0).getId());
        int second_id = Integer.valueOf(list.get(2).getId());
        int end_id = Integer.valueOf(list.get(list.size() - 1).getId());

        Assert.assertTrue(first_id > second_id);
        Assert.assertTrue(second_id > end_id);
    }

    @Test
    public void testOrderDesc() {
        query.orderDesc("id");
        List<Certificate> list = query.getResultList();

        int first_id = Integer.valueOf(list.get(0).getId());
        int second_id = Integer.valueOf(list.get(2).getId());
        int end_id = Integer.valueOf(list.get(list.size() - 1).getId());

        Assert.assertTrue(first_id > second_id);
        Assert.assertTrue(second_id > end_id);
    }

    @Test
    public void testSelect() {
        query.select("id", "name", "limit");
        query.eq("id", "72300125");

        List<Object[]> list = query.getResultList();

        Assert.assertTrue(list.size() == 1);
        Object[] values = list.get(0);
        Assert.assertTrue(values.length == 3);
    }

    @Test
    public void testSelectStaticMeta() {
        query.select(Certificate_.id, Certificate_.name, Certificate_.limit);
        query.eq(Certificate_.id, "72300125");

        List<Object[]> list = query.getResultList();

        Assert.assertTrue(list.size() == 1);
        Object[] values = list.get(0);
        Assert.assertTrue(values.length == 3);
    }

    class CeriteriaImpl<T> extends AbstractCriteria<Criteriaable, T, T> {

        public CeriteriaImpl(EntityManager em, Class<T> entityClass) {
            super(em, entityClass);
        }
    }
}
