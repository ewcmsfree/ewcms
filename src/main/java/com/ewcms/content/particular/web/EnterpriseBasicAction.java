/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.web.CrudBaseAction;

/**
 * @author 吴智俊
 */
public class EnterpriseBasicAction extends CrudBaseAction<EnterpriseBasic, Long> {

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

	public EnterpriseBasic getEnterpriseBasicVo() {
		return super.getVo();
	}

	public void setEnterpriseBasicVo(EnterpriseBasic enterpriseBasic) {
		super.setVo(enterpriseBasic);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(EnterpriseBasic vo) {
		return vo.getId();
	}

	@Override
	protected EnterpriseBasic getOperator(Long pk) {
		return particularFac.findEnterpriseBasicById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delEnterpriseBasic(pk);
	}

	@Override
	protected Long saveOperator(EnterpriseBasic vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updEnterpriseBasic(vo);
		} else {
			return particularFac.addEnterpriseBasic(vo);
		}
	}

	@Override
	protected EnterpriseBasic createEmptyVo() {
		return new EnterpriseBasic();
	}
}