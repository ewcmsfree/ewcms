/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.JobTrigger;

/**
 * 定时器定时DAO
 * 
 * @author 吴智俊
 */
@Repository("jobTriggerDAO")
public class JobTriggerDAO extends JpaDAO<Integer, JobTrigger>{

}
