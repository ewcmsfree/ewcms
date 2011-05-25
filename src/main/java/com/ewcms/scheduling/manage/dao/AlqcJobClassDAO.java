/**
 * 创建日期 2009-12-13
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.scheduling.model.AlqcJobClass;

/**
 * 定时工作类DAO
 * 
 * @author 吴智俊
 */
@Repository("alqcJobClassDAO")
public class AlqcJobClassDAO extends JpaDAO<Integer, AlqcJobClass> {
	@SuppressWarnings("unchecked")
	public AlqcJobClass findByAlqcJobClassByClassEntity(String classEntity) {
		String hql = "From AlqcJobClass o Where o.classEntity=?";
		List<AlqcJobClass> list = this.getJpaTemplate().find(hql, classEntity);
		if (list.isEmpty())
			return new AlqcJobClass();
		return list.get(0);
	}
}
