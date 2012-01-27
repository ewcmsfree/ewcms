/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.externalds.manager.BaseDSFacable;
import com.ewcms.plugin.externalds.model.BaseDS;
import com.ewcms.plugin.externalds.model.JdbcDS;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 * 
 */
public class JdbcDSAction extends CrudBaseAction<JdbcDS, Long> {

	private static final long serialVersionUID = -538592880660461507L;

	@Autowired
	private BaseDSFacable baseDSFac;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public JdbcDS getJdbcDSVo() {
		return super.getVo();
	}

	public void setJdbcDSVo(JdbcDS jdbcDS) {
		super.setVo(jdbcDS);
	}

	@Override
	protected Long getPK(JdbcDS vo) {
		return vo.getId();
	}

	@Override
	protected JdbcDS getOperator(Long pk) {
		BaseDS baseDS = baseDSFac.findByBaseDS(pk);
		if (baseDS instanceof JdbcDS) {
			return (JdbcDS) baseDS;
		}
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		baseDSFac.deletedBaseDS(pk);
	}

	@Override
	protected Long saveOperator(JdbcDS vo, boolean isUpdate) {
		return baseDSFac.saveOrUpdateBaseDS(vo);
	}

	@Override
	protected JdbcDS createEmptyVo() {
		return new JdbcDS();
	}

}
