/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.PublishingSector;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * @author 吴智俊
 */
public class PublishingSectorAction extends CrudBaseAction<PublishingSector, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	public PublishingSector getPublishingSectorVo() {
		return super.getVo();
	}

	public void setPublishingSectorVo(PublishingSector publishingSector) {
		super.setVo(publishingSector);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(PublishingSector vo) {
		return vo.getId();
	}

	@Override
	protected PublishingSector getOperator(Long pk) {
		return particularFac.findPublishingSectorById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delPublishingSector(pk);
	}

	@Override
	protected Long saveOperator(PublishingSector vo, boolean isUpdate) {
		if (isUpdate) {
			return particularFac.updPublishingSector(vo);
		} else {
			return particularFac.addPublishingSector(vo);
		}
	}

	@Override
	protected PublishingSector createEmptyVo() {
		return new PublishingSector();
	}

	private Long projectBasicId;

	public Long getProjectBasicId() {
		return projectBasicId;
	}

	public void setProjectBasicId(Long projectBasicId) {
		this.projectBasicId = projectBasicId;
	}

	public void findPsToPb() {
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getProjectBasicId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByPBId(getProjectBasicId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
	
	private Long projectArticleId;

	public Long getProjectArticleId() {
		return projectArticleId;
	}

	public void setProjectArticleId(Long projectArticleId) {
		this.projectArticleId = projectArticleId;
	}
	
	public void findPsToPa(){
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getProjectArticleId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByPAId(getProjectArticleId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
	
	private Long enterpriseBasicId;

	public Long getEnterpriseBasicId() {
		return enterpriseBasicId;
	}

	public void setEnterpriseBasicId(Long enterpriseBasicId) {
		this.enterpriseBasicId = enterpriseBasicId;
	}

	public void findPsToEb() {
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getEnterpriseBasicId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByEBId(getEnterpriseBasicId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
	
	private Long enterpriseArticleId;

	public Long getEnterpriseArticleId() {
		return enterpriseArticleId;
	}

	public void setEnterpriseArticleId(Long enterpriseArticleId) {
		this.enterpriseArticleId = enterpriseArticleId;
	}

	public void findPsToEa(){
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getEnterpriseArticleId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByEAId(getEnterpriseArticleId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}

	private Long employeBasicId;

	public Long getEmployeBasicId() {
		return employeBasicId;
	}

	public void setEmployeBasicId(Long employeBasicId) {
		this.employeBasicId = employeBasicId;
	}

	public void findPsToMb() {
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getEnterpriseBasicId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByMBId(getEmployeBasicId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
	
	private Long employeArticleId;

	public Long getEmployeArticleId() {
		return employeArticleId;
	}

	public void setEmployeArticleId(Long employeArticleId) {
		this.employeArticleId = employeArticleId;
	}

	public void findPsToMa(){
		List<PublishingSector> publishingSectors = particularFac.findPublishingSectorAll();
		if (publishingSectors != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (PublishingSector publishingSector : publishingSectors){
				comboBox = new ComboBoxString();
				comboBox.setId(publishingSector.getCode());
				comboBox.setText(publishingSector.getName());
				if (getEnterpriseArticleId() != null){
					Boolean isEntity = particularFac.findPublishingSectorSelectedByMAId(getEmployeArticleId(), publishingSector.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
}