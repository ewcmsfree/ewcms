/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.AlqcJobTrigger;

/**
 * 定时器DAO
 * 
 * @author 吴智俊
 */
@Repository("alqcJobTriggerDAO")
public class AlqcJobTriggerDAO extends JpaDAO<Integer, AlqcJobTrigger>{

}
