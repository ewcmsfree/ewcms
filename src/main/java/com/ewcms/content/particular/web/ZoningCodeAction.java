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
import com.ewcms.content.particular.model.ZoningCode;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBoxString;

/**
 * @author 吴智俊
 */
public class ZoningCodeAction extends CrudBaseAction<ZoningCode, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	public ZoningCode getZoningCodeVo() {
		return super.getVo();
	}

	public void setZoningCodeVo(ZoningCode zoningCode) {
		super.setVo(zoningCode);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ZoningCode vo) {
		return vo.getId();
	}

	@Override
	protected ZoningCode getOperator(Long pk) {
		return particularFac.findZoningCodeById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delZoningCode(pk);
	}

	@Override
	protected Long saveOperator(ZoningCode vo, boolean isUpdate) {
		if (isUpdate) {
			return particularFac.updZoningCode(vo);
		} else {
			try{
				return particularFac.addZoningCode(vo);
			} catch (BaseException e) {
				addActionMessage(e.getPageMessage());
				return null;
			}
		}
	}

	@Override
	protected ZoningCode createEmptyVo() {
		return new ZoningCode();
	}

	private Long projectBasicId;

	public Long getProjectBasicId() {
		return projectBasicId;
	}

	public void setProjectBasicId(Long projectBasicId) {
		this.projectBasicId = projectBasicId;
	}

	public void findZoningCodeAll() {
		List<ZoningCode> zoningCodes = particularFac.findZoningCodeAll();
		if (zoningCodes != null){
			List<ComboBoxString> comboBoxStrings = new ArrayList<ComboBoxString>();
			ComboBoxString comboBox = null;
			for (ZoningCode zoningCode : zoningCodes){
				comboBox = new ComboBoxString();
				comboBox.setId(zoningCode.getCode());
				comboBox.setText(zoningCode.getName());
				if (getProjectBasicId() != null){
					Boolean isEntity = particularFac.findZoningCodeSelected(getProjectBasicId(), zoningCode.getCode());
					if (isEntity) comboBox.setSelected(true);
				}
				comboBoxStrings.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxStrings.toArray(new ComboBoxString[0])));
		}
	}
}