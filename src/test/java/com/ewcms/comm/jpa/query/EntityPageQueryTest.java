/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/comm/jpa/dao/applicationContext.xml"}, inheritLocations = true)
public class EntityPageQueryTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    private EntityPageQueryable<Certificate, Certificate> query;

    @Before
    public void before() {
        query = new EntityPageQuery<Certificate, Certificate>(
                entityManagerFactory.createEntityManager(),
                Certificate.class);
    }

    private void testCount(int y) {
        Assert.assertTrue(query.count() == y);
    }

    private void testSum(int y) {
        query.setSumColumns("limit");
        Assert.assertTrue((Integer) query.sum() == y);
    }

    private void testSumStaticMeta(int y) {
        query.setSumColumns(Certificate_.limit);
        Assert.assertTrue((Integer) query.sum() == y);
    }

    @Test
    public void testEqStaticMeta() {
        query.eq(Certificate_.id, "72300125");
        testCount(1);
        testSumStaticMeta(298);
    }

    @Test
    public void testEq() {
        query.eq("id", "72300125");
        testSum(298);
    }

    @Test
    public void testNotEqStaticMeta() {
        query.notEq(Certificate_.id, "72300125");
        testCount(44078);
        testSumStaticMeta(96428046);
    }

    @Test
    public void testNotEq() {
        query.notEq("id", "72300125");
        testCount(44078);
        testSum(96428046);
    }

    @Test
    public void testGtStaticMeta() {
        query.gt(Certificate_.limit, 3000);
        testCount(5803);
        testSumStaticMeta(54442521);
    }

    @Test
    public void testGt() {
        query.gt("limit", 3000);
        testCount(5803);
        testSum(54442521);
    }

    @Test
    public void testGeStaticMeta() {
        query.ge(Certificate_.limit, 3000);
        testCount(6499);
        testSumStaticMeta(56530521);
    }

    @Test
    public void testGe() {
        query.ge("limit", 3000);
        testCount(6499);
        testSum(56530521);
    }

    @Test
    public void testLtStaticMeta() {
        query.lt(Certificate_.limit, 3000);
        testCount(37580);
        testSumStaticMeta(39897823);
    }

    @Test
    public void testLt() {
        query.lt("limit", 3000);
        testCount(37580);
        testSum(39897823);
    }

    @Test
    public void testLeStaticMeta() {
        query.le(Certificate_.limit, 3000);
        testCount(38276);
        testSumStaticMeta(41985823);
    }

    @Test
    public void testLe() {
        query.le("limit", 3000);
        testCount(38276);
        testSum(41985823);
    }

    @Test
    public void testLikeStartStaticMeta() {
        query.likeStart(Certificate_.name, "王");
        testCount(2374);
        testSumStaticMeta(5757883);
    }

    @Test
    public void testLikeStart() {
        query.likeStart("name", "王");
        testCount(2374);
        testSum(5757883);
    }

    @Test
    public void testLikeEndStaticMeta() {
        query.likeEnd(Certificate_.name, "伟");
        testCount(348);
        testSumStaticMeta(389982);
    }

    @Test
    public void testLikeEnd() {
        query.likeEnd("name", "伟");
        testCount(348);
        testSum(389982);
    }

    @Test
    public void testLikeAnyStaticMeta() {
        query.likeAnywhere(Certificate_.name, "伟");
        testCount(515);
        testSumStaticMeta(573517);
    }

    @Test
    public void testLikeAny() {
        query.likeAnywhere("name", "伟");
        testCount(515);
        testSum(573517);
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
        testCount(6589);
        testSumStaticMeta(4049133);
    }

    @Test
    public void testBetween() {
        Date start = getDate(1970, 0, 1);
        Date end = getDate(1980, 0, 1);
        query.between("brithdate", start, end);
        testCount(6589);
        testSum(4049133);
    }

    @Test
    public void testInStaticMeta() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");
        query.in(Certificate_.name, names);
        testCount(8);
        testSumStaticMeta(8803);
    }

    @Test
    public void testIn() {
        List<String> names = new ArrayList<String>();
        names.add("王伟");
        names.add("周冬初");
        query.in("name", names);
        testCount(8);
        testSum(8803);
    }

    @Test
    public void testIsNullStaticMeta() {
        query.isNull(Certificate_.name);
        testCount(0);
        testSumStaticMeta(0);
    }

    @Test
    public void testIsNull() {
        query.isNull("name");
        testCount(0);
        testSum(0);
    }

    @Test
    public void testIsNotNullStaticMeta() {
        query.isNotNull(Certificate_.name);
        testCount(44079);
        testSumStaticMeta(96428344);
    }

    @Test
    public void testIsNotNull() {
        query.isNotNull("name");
        testCount(44079);
        testSum(96428344);
    }

    @Test
    public void testAnd() {
        query.eq(Certificate_.name, "王伟").and(query.eq(Certificate_.id, "72300125"));
        testCount(1);
        testSum(298);
    }

    @Test
    public void testOr() {
        query.eq(Certificate_.name, "王伟").or(query.eq(Certificate_.name, "周冬初"));
        testCount(8);
        testSum(8803);
    }

    @Test
    public void testComplex() {
        query.eq(Certificate_.name, "周冬初").or(query.eq(Certificate_.name, "王伟").and(query.eq(Certificate_.id, "723001251")));
        testCount(2);
        testSum(1939);
    }

    @Test
    public void testNot() {
        query.not(query.likeEnd(Certificate_.name, "伟"));
        testCount(43731);
        testSum(96038362);
    }

    @Test
    public void testPageCountZero(){
        query.isNull(Certificate_.id);

        testCount(0);
        int page = query.countPage();
        Assert.assertTrue(page == 0);
    }

    @Test
    public void testPageFirst(){
        query.eq(Certificate_.id, "72300125");

        testCount(1);
        int page = query.countPage();
        Assert.assertTrue(page == 1);
    }

    @Test
    public void testPageFull(){
        String[] ids = new String[]{
        "72300130","72300131","72300132","72300133","72300134",
        "72300135","72300136","72300137","72300138","72300139",
        "72300140","72300141","72300142","72300143","72300144"};

        query.setRows(15);
        query.in(Certificate_.id, ids);

        testCount(15);
        int page = query.countPage();
        Assert.assertTrue(page == 1);
    }

    @Test
    public void testGetResultList(){
        String[] ids = new String[]{
        "72300130","72300131","72300132","72300133","72300134",
        "72300135","72300136","72300137","72300138","72300139",
        "72300140","72300141","72300142","72300143","72300144",
        "72300145"};

        query.setRows(15);
        query.setPage(1);
        query.in(Certificate_.id, ids);
        query.orderAsc(Certificate_.id);

        testCount(16);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(list.get(0).getId(), "72300145");
    }

    @Test
    public void testMultiSum(){

        query.setSumColumns(Certificate_.limit,Certificate_.limit);
        Object[] values = (Object[])query.sum();

        Assert.assertTrue(values.length == 2);
        Assert.assertTrue((Long)values[0] == 96428344);
        Assert.assertTrue((Long)values[1] == 96428344);
    }

    @Test
    public void testNoneSum(){
         Object values = query.sum();
         Assert.assertNull(values);
    }
}
