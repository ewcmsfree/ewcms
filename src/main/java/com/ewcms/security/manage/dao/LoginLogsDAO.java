/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.security.manage.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.security.manage.model.LoginLogs;

@Repository
public class LoginLogsDAO  extends JpaDAO<String, LoginLogs> implements LoginLogsDAOable{

}
