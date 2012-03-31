/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.security.web.authentication.rememberme.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.security.web.authentication.rememberme.model.RememberMeToken;

/**
 *
 * @author wangwei
 * @author wuzhijun
 */
@Repository
public class RememberMeTokenDAO extends JpaDAO<String, RememberMeToken> {

    public void removeUserTokens(final String username) {
    	String hql = "Delete From RememberMeToken o Where o.username=:username ";

    	Query query = this.getEntityManager().createQuery(hql);
    	query.setParameter("username", username);

    	query.executeUpdate();
    }
}
