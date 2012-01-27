/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.externalds.manager.service.BaseDSServiceable;
import com.ewcms.plugin.externalds.model.BaseDS;

@Service
public class BaseDSFac implements BaseDSFacable {

	@Autowired
	private BaseDSServiceable baseDSService;
	
	@Override
	public Long saveOrUpdateBaseDS(BaseDS baseDS){
		return baseDSService.saveOrUpdateBaseDS(baseDS);
	}

	@Override
	public BaseDS findByBaseDS(Long baseDSId){
		return baseDSService.findByBaseDS(baseDSId);
	}

	@Override
	public List<BaseDS> findAllBaseDS(){
		return baseDSService.findAllBaseDS();
	}

	@Override
	public void deletedBaseDS(Long baseDSId){
		baseDSService.deletedBaseDS(baseDSId);
	}
}
