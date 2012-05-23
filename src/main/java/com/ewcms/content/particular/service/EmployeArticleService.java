package com.ewcms.content.particular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.particular.dao.EmployeArticleDAO;
import com.ewcms.content.particular.dao.EmployeBasicDAO;
import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.content.particular.model.PublishingSector;

@Service
public class EmployeArticleService implements EmployeArticleServiceable {

	@Autowired
	private EmployeBasicDAO employeBasicDAO;
	@Autowired
	private EmployeArticleDAO employeArticleDAO;
	@Autowired
	private PublishingSectorDAO publishingSectorDAO;
	
	@Override
	public Long addEmployeArticle(EmployeArticle employeArticle) {
		setEmployeBasic(employeArticle);
		setPublishingSector(employeArticle);
		employeArticleDAO.persist(employeArticle);
		return employeArticle.getId();
	}

	@Override
	public Long updEmployeArticle(EmployeArticle employeArticle) {
		setEmployeBasic(employeArticle);
		setPublishingSector(employeArticle);
		employeArticleDAO.merge(employeArticle);
		return employeArticle.getId();
	}

	private void setEmployeBasic(EmployeArticle employeArticle){
		String employeBasic_cardCode = employeArticle.getEmployeBasic().getCardCode();
		Assert.notNull(employeBasic_cardCode);
		EmployeBasic employeBasic = employeBasicDAO.findEmployeBasicByCardCode(employeBasic_cardCode);
		Assert.notNull(employeBasic);
		employeArticle.setEmployeBasic(employeBasic);
	}
	
	private void setPublishingSector(EmployeArticle employeArticle){
		String publishingSector_code = employeArticle.getPublishingSector().getCode();
		PublishingSector publishingSector = publishingSectorDAO.findPublishingSectorByCode(publishingSector_code);
		employeArticle.setPublishingSector(publishingSector);
	}
	
	@Override
	public void delEmployeArticle(Long id) {
		employeArticleDAO.removeByPK(id);
	}

	@Override
	public EmployeArticle findEmployeArticleById(Long id) {
		return employeArticleDAO.get(id);
	}

}
