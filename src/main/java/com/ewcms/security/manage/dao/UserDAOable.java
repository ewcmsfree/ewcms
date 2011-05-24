/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.dao;

import com.ewcms.common.jpa.dao.JpaDAOable;
import com.ewcms.security.manage.model.User;

public interface UserDAOable extends JpaDAOable<String,User>{

    /**
     * 更新用户密码
     * 
     * @param username 用户名
     * @param password 用户密码
     */
    void updatePassword(String username,String password);
}
