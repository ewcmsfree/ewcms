package com.ewcms.content.particular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.particular.dao.EmployeBasicDAO;
import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.content.particular.model.PublishingSector;

@Service
public class EmployeBasicService implements EmployeBasicServiceable {

	@Autowired
	private EmployeBasicDAO employeBasicDAO;
	@Autowired
	private PublishingSectorDAO publishingSectorDAO;

	@Override
	public Long addEmployeBasic(EmployeBasic employeBasic) {
		setPublishingSector(employeBasic);
		employeBasicDAO.persist(employeBasic);
		return employeBasic.getId();
	}

	@Override
	public Long updEmployeBasic(EmployeBasic employeBasic) {
		setPublishingSector(employeBasic);
		employeBasicDAO.merge(employeBasic);
		return employeBasic.getId();
	}

	private void setPublishingSector(EmployeBasic employeBasic){
		String publishingSector_code = employeBasic.getPublishingSector().getCode();
		PublishingSector publishingSector = publishingSectorDAO.findPublishingSectorByCode(publishingSector_code);
		employeBasic.setPublishingSector(publishingSector);
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
	public List<EmployeBasic> findEmployeBasicAll() {
		return employeBasicDAO.findProjectBasicAll();
	}
}
