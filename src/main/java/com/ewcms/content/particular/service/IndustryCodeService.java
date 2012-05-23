package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.IndustryCodeDAO;
import com.ewcms.content.particular.model.IndustryCode;

@Service
public class IndustryCodeService implements IndustryCodeServiceable {

	@Autowired
	private IndustryCodeDAO industryCodeDAO;
	
	@Override
	public Long addIndustryCode(IndustryCode industryCode) {
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
