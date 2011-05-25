/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.security.manage.model.User;

/**
 * 用户数据操作
 * 
 * @author wangwei
 * 
 */
@Repository
public class UserDAO extends JpaDAO<String,User> implements UserDAOable {

    @Override
    public void updatePassword(final String username,final String password) {
        this.getJpaTemplate().execute(new JpaCallback<Object>(){
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                String hql = "Update User o Set o.password = :password Where username = :username";
                em.createQuery(hql)
                .setParameter("username", username)
                .setParameter("password", password)
                .executeUpdate();
                return null;
            }
        });
    }
}
