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
import com.ewcms.plugin.datasource.model.BeanDS;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 * 
 */
public class BeanDSAction extends CrudBaseAction<BeanDS, Long> {

	private static final long serialVersionUID = -538592880660461507L;

	@Autowired
	private BaseDSFacable baseDSFac;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public BeanDS getBeanDSVo() {
		return super.getVo();
	}

	public void setBeanDSVo(BeanDS beanDS) {
		super.setVo(beanDS);
	}

	@Override
	protected Long getPK(BeanDS vo) {
		return vo.getId();
	}

	@Override
	protected BeanDS getOperator(Long pk) {
		BaseDS baseDS = baseDSFac.findByBaseDS(pk);
		if (baseDS instanceof BeanDS) {
			return (BeanDS) baseDS;
		}
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		baseDSFac.deletedBaseDS(pk);
	}

	@Override
	protected Long saveOperator(BeanDS vo, boolean isUpdate) {
		return baseDSFac.saveOrUpdateBaseDS(vo);
	}

	@Override
	protected BeanDS createEmptyVo() {
		return new BeanDS();
	}
}
