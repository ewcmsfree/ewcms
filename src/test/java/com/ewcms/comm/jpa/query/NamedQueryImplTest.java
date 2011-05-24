/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ewcms.common.jpa.query.HqlQueryable;
import com.ewcms.common.jpa.query.NamedQuery;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/comm/jpa/dao/applicationContext.xml"}, inheritLocations = true)
public class NamedQueryImplTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void testSetParameter() {
        HqlQueryable<Certificate> query = new NamedQuery(
                entityManagerFactory.createEntityManager(),
                "Certificate.findById");
        query.setParameter("id", "72300125");

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void testSetParameterPosition() {
        HqlQueryable<Certificate> query = new NamedQuery(
                entityManagerFactory.createEntityManager(),
                "Certificate.findByIdPosition");
        query.setParameter(1, "72300125");

        List<Certificate> list = query.getResultList();
        Assert.assertTrue(list.size() == 1);
    }
}
