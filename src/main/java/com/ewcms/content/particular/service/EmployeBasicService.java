/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.EmployeBasicDAO;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Organ;

@Service
public class EmployeBasicService implements EmployeBasicServiceable {

	@Autowired
	private EmployeBasicDAO employeBasicDAO;
	@Autowired
	private SiteFacable siteFac;

	@Override
	public Long addEmployeBasic(EmployeBasic employeBasic) {
		setOrgan(employeBasic);
		employeBasicDAO.persist(employeBasic);
		return employeBasic.getId();
	}

	@Override
	public Long updEmployeBasic(EmployeBasic employeBasic) {
		setOrgan(employeBasic);
		employeBasic.setRelease(false);
		employeBasicDAO.merge(employeBasic);
		return employeBasic.getId();
	}

	private void setOrgan(EmployeBasic employeBasic){
		Integer organId = employeBasic.getOrgan().getId();
		Organ organ = siteFac.getOrgan(organId);
		employeBasic.setOrgan(organ);
	}
	
	@Override
	public void delEmployeBasic(Long id) {
		employeBasicDAO.removeByPK(id);
	}

	@Override
	public EmployeBasic findEmployeBasicById(Long id) {
		return employeBasicDAO.get(id);
	}

	@Override
	public List<EmployeBasic> findEmployeBasicByPageAndRows(Integer page, Integer rows, String name) {
		return employeBasicDAO.findEmployeBasicByPageAndRows(page, rows, name);
	}
	
	@Override
	public Long findEmployeBasicTotal(String name){
		return employeBasicDAO.findEmployeBasicTotal(name);
	}
	
	@Override
	public void pubEmployeBasic(List<Long> employeBasicIds) {
		if (employeBasicIds.isEmpty()) return;
		for (Long employeBasicId : employeBasicIds){
			EmployeBasic employBasic = employeBasicDAO.get(employeBasicId);
			if (employBasic.getRelease() || employBasic.getOrgan() == null) continue;
			employBasic.setRelease(true);
			employeBasicDAO.merge(employBasic);
		}
	}
	
	@Override
	public void unPubEmployeBasic(List<Long> employeBasicIds){
		if (employeBasicIds.isEmpty()) return;
		for (Long employeBasicId : employeBasicIds){
			EmployeBasic employeBasic = employeBasicDAO.get(employeBasicId);
			if (!employeBasic.getRelease()) continue;
			employeBasic.setRelease(false);
			employeBasicDAO.merge(employeBasic);
		}
	}
}
