/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ewcms.common.jpa.query.HqlQuery;
import com.ewcms.common.jpa.query.HqlQueryable;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/comm/jpa/dao/applicationContext.xml"}, inheritLocations = true)
public class HqlQueryTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void testSetParamter() {
        String hql = "From Certificate o Where o.id=:id";
        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        query.setParameter("id", "72300125");
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testSetParameterPosition() {
        String hql = "From Certificate o Where o.id = ?";

        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        query.setParameter(1, "72300125");
        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testSetParameterDate() {
        String hql = "From Certificate o Where o.brithdate > :date";

        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter("date", new Date(calendar.getTimeInMillis()), TemporalType.DATE);

        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 5274);
    }

    @Test
    public void testSetParameterDatePosition() {
        String hql = "From Certificate o Where o.brithdate > ?1";

        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter(1, new Date(calendar.getTimeInMillis()), TemporalType.DATE);

        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 5274);
    }

    @Test
    public void testSetParameterCalendar() {
        String hql = "From Certificate o Where o.brithCalendar > :calendar";

        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter("calendar", calendar, TemporalType.DATE);

        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 5274);
    }

    @Test
    public void testSetParameterCalendarPosition() {
        String hql = "From Certificate o Where o.brithCalendar > ?1";

        HqlQueryable<Certificate> query = new HqlQuery<Certificate>(
                entityManagerFactory.createEntityManager(), hql);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1975, 0, 1);
        query.setParameter(1, calendar, TemporalType.DATE);

        List<Certificate> list = query.getResultList();

        Assert.assertTrue(list.size() == 5274);
    }
}
