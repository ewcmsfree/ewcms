/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */
package com.ewcms.comm.jpa.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ewcms.common.jpa.dao.JpaDAO;

/**
 *
 * @author wangwei
 */
@Repository
public class JpaDAOImpl extends JpaDAO<Integer,Model> {

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
}
