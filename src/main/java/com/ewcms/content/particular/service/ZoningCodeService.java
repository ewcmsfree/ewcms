/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.ZoningCodeDAO;
import com.ewcms.content.particular.model.ZoningCode;

@Service
public class ZoningCodeService implements ZoningCodeServiceable {

	@Autowired
	private ZoningCodeDAO zoningCodeDAO;
	
	@Override
	public Long addZoningCode(ZoningCode zoningCode) {
		zoningCodeDAO.persist(zoningCode);
		return zoningCode.getId();
	}

	@Override
	public Long updZoningCode(ZoningCode zoningCode) {
		zoningCodeDAO.merge(zoningCode);
		return zoningCode.getId();
	}

	@Override
	public void delZoningCode(Long id) {
		zoningCodeDAO.removeByPK(id);
	}

	@Override
	public ZoningCode findZoningCodeById(Long id) {
		return zoningCodeDAO.get(id);
	}

	@Override
	public List<ZoningCode> findZoningCodeAll() {
		return zoningCodeDAO.findZoningCodeAll();
	}

	@Override
	public Boolean findZoningCodeSelected(Long projectBasicId,
			String zoningCodeCode) {
		return zoningCodeDAO.findZoningCodeSelected(projectBasicId, zoningCodeCode);
	}

}
