/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import org.junit.Assert;
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
public class HqlPageQueryTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void testSetParameter() {
        String where = "o.id =:id";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql, countHql, sumHql);
        query.setParameter("id", "72300125");

        int count = query.count();
        int sum = ((Long) query.sum()).intValue();
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(count == 1);
        Assert.assertTrue(sum == 298);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testSetParameterPosition() {
        String where = "o.id =?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql, countHql, sumHql);
        query.setParameter(1, "72300125");

        int count = query.count();
        int sum = ((Long) query.sum()).intValue();
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(count == 1);
        Assert.assertTrue(sum == 298);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testSetParameterDate() {
        String where = "o.brithdate > :date";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql, countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter("date", new Date(calendar.getTimeInMillis()), TemporalType.DATE);

        Assert.assertTrue(query.count() == 5274);
    }

    @Test
    public void testSetParameterDatePosition() {
        String where = "o.brithdate > ?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql,countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter(1, new Date(calendar.getTimeInMillis()), TemporalType.DATE);

        Assert.assertTrue(query.count() == 5274);
    }

    @Test
    public void testSetParameterCalendar() {
        String where = "o.brithCalendar > :calendar";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql, countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter("calendar", calendar, TemporalType.DATE);

        Assert.assertTrue(query.count() == 5274);
    }

    @Test
    public void testSetParameterCalendarPosition() {
         String where = "o.brithCalendar > ?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql, countHql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter(1, calendar, TemporalType.DATE);

        Assert.assertTrue(query.count() == 5274);
    }

    @Test
    public void testPageFirst() {
        String where = "o.id =?1";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);

        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql, countHql, sumHql);
        query.setParameter(1, "72300125");

        int count = query.count();
        Assert.assertTrue(count == 1);
        int page = query.countPage();
        Assert.assertTrue(page == 1);
    }

    @Test
    public void testPageFull() {
        String where = "o.id in (?1)";
        String hql = String.format("From Certificate o Where %s", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);
        String[] ids = new String[]{
            "72300130", "72300131", "72300132", "72300133", "72300134",
            "72300135", "72300136", "72300137", "72300138", "72300139",
            "72300140", "72300141", "72300142", "72300143", "72300144"};

        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql, countHql, sumHql);

        query.setRows(15);
        List<String> paras = Arrays.asList(ids);
        query.setParameter(1, paras);

        int count = query.count();
        Assert.assertTrue(count == 15);
        int page = query.countPage();
        Assert.assertTrue(page == 1);
    }

    @Test
    public void testGetResultList() {
        String where = "o.id in (:ids)";
        String hql = String.format("From Certificate o Where %s Order By o.id", where);
        String countHql = String.format("Select count(o.id) From Certificate o Where %s", where);
        String sumHql = String.format("Select sum(o.limit) From Certificate o Where %s", where);
        String[] ids = new String[]{
            "72300130", "72300131", "72300132", "72300133", "72300134",
            "72300135", "72300136", "72300137", "72300138", "72300139",
            "72300140", "72300141", "72300142", "72300143", "72300144",
            "72300145"};


        HqlPageQueryable<Certificate> query = new HqlPageQuery(
                entityManagerFactory.createEntityManager(), hql, countHql, sumHql);

        query.setRows(15);
        query.setPage(1);
        List<String> paras = Arrays.asList(ids);
        query.setParameter("ids", paras);

        int count = query.count();
        Assert.assertTrue(count == 16);
        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
        Assert.assertEquals(list.get(0).getId(), "72300145");
    }
}
