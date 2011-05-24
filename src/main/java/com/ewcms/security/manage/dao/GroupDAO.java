/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.dao;

import com.ewcms.common.jpa.dao.JpaDAO;
import com.ewcms.security.manage.model.Group;
import org.springframework.stereotype.Repository;

/**
 * 用户组数据操作
 *
 * @author wangwei
 * 
 */
@Repository
public class GroupDAO extends JpaDAO<String,Group> implements GroupDAOable {

}
