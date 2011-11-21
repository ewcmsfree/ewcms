/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.dao;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 定时任务DAO
 * 
 * @author 吴智俊
 */
@Repository("jobInfoDAO")
public class JobInfoDAO extends JpaDAO<Integer, JobInfo>{
}
