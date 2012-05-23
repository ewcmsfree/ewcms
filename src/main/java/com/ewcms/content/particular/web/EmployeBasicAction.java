/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.web.CrudBaseAction;

/**
 * @author 吴智俊
 */
public class EmployeBasicAction extends CrudBaseAction<EmployeBasic, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public EmployeBasic getEmployeBasicVo() {
		return super.getVo();
	}

	public void setEmployeBasicVo(EmployeBasic employeBasic) {
		super.setVo(employeBasic);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(EmployeBasic vo) {
		return vo.getId();
	}

	@Override
	protected EmployeBasic getOperator(Long pk) {
		return particularFac.findEmployeBasicById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delEmployeBasic(pk);
	}

	@Override
	protected Long saveOperator(EmployeBasic vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updEmployeBasic(vo);
		} else {
			return particularFac.addEmployeBasic(vo);
		}
	}

	@Override
	protected EmployeBasic createEmptyVo() {
		return new EmployeBasic();
	}
}