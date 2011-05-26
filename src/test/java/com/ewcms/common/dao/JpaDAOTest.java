/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.common.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author wangwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com/ewcms/comm/jpa/dao/applicationContext.xml"}, inheritLocations = true)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class JpaDAOTest {

    @Autowired
    private JpaDAOImpl dao;

    @After
    public void after() {
        dao.removeAll();
    }

    @Test
    public void testSave() {
        Model model = new Model(1, "test");
        dao.persist(model);
        model = dao.get(1);
        Assert.assertNotNull(model);
    }

    @Test
    public void testUpdate() {
        Model model = new Model(1, "test");
        dao.persist(model);
        model.setTitle("test3");
        dao.merge(model);
        model = dao.get(1);
        Assert.assertEquals(model.getTitle(), "test3");
    }

    @Test
    public void testDelete() {
        Model model = new Model(1, "test");
        dao.persist(model);
        dao.remove(model);

        model = dao.get(1);
        Assert.assertNull(model);
    }

    @Test
    public void testFindAll() {
        Model model = new Model(1, "test");
        dao.persist(model);
        model = new Model(2, "test1");
        dao.persist(model);

        List<Model> list = dao.findAll();
        Assert.assertTrue(list.size() == 2);
    }

    @Test
    public void testRefresh() {
        Model model = new Model(1, "test");
        dao.persist(model);
        model.setTitle("test1");
        dao.flush(model);
        dao.refresh(model);

        Assert.assertEquals(model.getTitle(), "test1");
    }

    @Test
    public void testGetRefresh() {
        Model model = new Model(1, "test");
        dao.persist(model);

        Model refModel = dao.getRefresh(1);

        Assert.assertNotNull(refModel);
        Assert.assertEquals(refModel.getTitle(), "test");
    }
}
