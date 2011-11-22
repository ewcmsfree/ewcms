/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.datasource.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.datasource.manager.BaseDSFacable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.datasource.model.CustomDS;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 * 
 */
public class CustomDSAction extends CrudBaseAction<CustomDS, Long> {

	private static final long serialVersionUID = -538592880660461507L;

	@Autowired
	private BaseDSFacable baseDSFac;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public CustomDS getCustomDSVo() {
		return super.getVo();
	}

	public void setCustomDSVo(CustomDS customDS) {
		super.setVo(customDS);
	}

	@Override
	protected Long getPK(CustomDS vo) {
		return vo.getId();
	}

	@Override
	protected CustomDS getOperator(Long pk) {
		BaseDS baseDS = baseDSFac.findByBaseDS(pk);
		if (baseDS instanceof CustomDS) {
			return (CustomDS) baseDS;
		}
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		baseDSFac.deletedBaseDS(pk);
	}

	@Override
	protected Long saveOperator(CustomDS vo, boolean isUpdate) {
		return baseDSFac.saveOrUpdateBaseDS(vo);

	}

	@Override
	protected CustomDS createEmptyVo() {
		return new CustomDS();
	}

}
