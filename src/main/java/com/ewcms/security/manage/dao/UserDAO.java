/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.security.manage.model.User;

/**
 * 用户数据操作
 * 
 * @author wangwei
 * @author wuzhijun
 */
@Repository
public class UserDAO extends JpaDAO<String,User> implements UserDAOable {

    @Override
    public void updatePassword(final String username,final String password) {
    	String hql = "Update User o Set o.password=:password Where username=:username";

    	Query query = this.getEntityManager().createQuery(hql);
    	query.setParameter("username", username);
    	query.setParameter("password", password);

    	query.executeUpdate();
    }
}
