/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.BaseException;
import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.IndustryCode;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * @author 吴智俊
 */
public class IndustryCodeAction extends CrudBaseAction<IndustryCode, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	public IndustryCode getIndustryCodeVo() {
		return super.getVo();
	}

	public void setIndustryCodeVo(IndustryCode industryCode) {
		super.setVo(industryCode);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(IndustryCode vo) {
		return vo.getId();
	}

	@Override
	protected IndustryCode getOperator(Long pk) {
		return particularFac.findIndustryCodeById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delIndustryCode(pk);
	}

	@Override
	protected Long saveOperator(IndustryCode vo, boolean isUpdate) {
		if (isUpdate) {
			return particularFac.updIndustryCode(vo);
		} else {
			try{
				return particularFac.addIndustryCode(vo);
			} catch (BaseException e) {
				addActionMessage(e.getPageMessage());
				return null;
			}
		}
	}

	@Override
	protected IndustryCode createEmptyVo() {
		return new IndustryCode();
	}

	private Long projectBasicId;

	public Long getProjectBasicId() {
		return projectBasicId;
	}

	public void setProjectBasicId(Long projectBasicId) {
		this.projectBasicId = projectBasicId;
	}

	public void findIndustryCodeAll() {
		List<IndustryCode> industryCodes = particularFac.findIndustryCodeAll();
		if (industryCodes != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (IndustryCode industryCode : industryCodes){
				comboBox = new ComboBoxString();
				comboBox.setId(industryCode.getCode());
				comboBox.setText(industryCode.getName());
				if (getProjectBasicId() != null){
					Boolean isEntity = particularFac.findIndustryCodeSelected(getProjectBasicId(), industryCode.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
}