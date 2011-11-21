/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.datasource.manager.dao.BaseDSDAO;
import com.ewcms.plugin.datasource.model.BaseDS;

@Service
public class BaseDSService implements BaseDSServiceable {
	
	@Autowired
	private BaseDSDAO baseDSDAO;
	
	@Override
	public Long saveOrUpdateBaseDS(BaseDS baseDS) {
		baseDSDAO.merge(baseDS);
		return baseDS.getId();
	}

	@Override
	public BaseDS findByBaseDS(Long baseDSId) {
		return baseDSDAO.get(baseDSId);
	}

	@Override
	public List<BaseDS> findAllBaseDS() {
		return (List<BaseDS>)baseDSDAO.findAll();
	}

	@Override
	public void deletedBaseDS(Long baseDSId) {
		baseDSDAO.removeByPK(baseDSId);
	}
}
