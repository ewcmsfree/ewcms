/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.citizen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.plugin.citizen.service.CitizenServiceable;

/**
 * @author wuzhijun
 */
@Service
public class CitizenFac implements CitizenFacable {
    @Autowired
    private CitizenServiceable citizenService;

	@Override
	public Integer addCitizen(Citizen citizen) {
		return citizenService.addCitizen(citizen);
	}

	@Override
	public Integer updCitizen(Citizen citizen) {
		return citizenService.updCitizen(citizen);
	}

	@Override
	public Citizen getCitizen(Integer citizenId) {
		return citizenService.getCitizen(citizenId);
	}

	@Override
	public void delCitizen(Integer citizenId) {
		citizenService.delCitizen(citizenId);
	}

	@Override
	public List<Citizen> getAllCitizen() {
		return citizenService.getAllCitizen();
	}

}
