/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.dao.IndustryCodeDAO;
import com.ewcms.content.particular.model.IndustryCode;

@Service
public class IndustryCodeService implements IndustryCodeServiceable {

	@Autowired
	private IndustryCodeDAO industryCodeDAO;
	
	@Override
	public Long addIndustryCode(IndustryCode industryCode) throws BaseException{
		String code = industryCode.getCode();
		IndustryCode entity = industryCodeDAO.findIndustryCodeByCode(code);
		if (entity != null) throw new BaseException("已存在相同的代码", "已存在相同的代码");
		industryCodeDAO.persist(industryCode);
		return industryCode.getId();
	}

	@Override
	public Long updIndustryCode(IndustryCode industryCode) {
		industryCodeDAO.merge(industryCode);
		return industryCode.getId();
	}

	@Override
	public void delIndustryCode(Long id) {
		industryCodeDAO.removeByPK(id);
	}

	@Override
	public IndustryCode findIndustryCodeById(Long id) {
		return industryCodeDAO.get(id);
	}

	@Override
	public List<IndustryCode> findIndustryCodeAll() {
		return industryCodeDAO.findIndustryCodeAll();
	}

	@Override
	public Boolean findIndustryCodeSelected(Long projectBasicId, String industryCodeCode) {
		return industryCodeDAO.findIndustryCodeSelected(projectBasicId, industryCodeCode);
	}

}
