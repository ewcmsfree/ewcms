/**
 * 创建日期 2009-4-13
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
