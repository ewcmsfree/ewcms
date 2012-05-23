/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EnterpriseArticle;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class EnterpriseArticleAction extends CrudBaseAction<EnterpriseArticle, Long> {

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

	public EnterpriseArticle getEnterpriseArticleVo() {
		return super.getVo();
	}

	public void setEnterpriseArticleVo(EnterpriseArticle enterpriseArticle) {
		super.setVo(enterpriseArticle);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(EnterpriseArticle vo) {
		return vo.getId();
	}

	@Override
	protected EnterpriseArticle getOperator(Long pk) {
		return particularFac.findEnterpriseArticleById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delEnterpriseArticle(pk);
	}

	@Override
	protected Long saveOperator(EnterpriseArticle vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updEnterpriseArticle(vo);
		} else {
			return particularFac.addEnterpriseArticle(vo);
		}
	}

	@Override
	protected EnterpriseArticle createEmptyVo() {
		return new EnterpriseArticle();
	}
	
	public void findEbAll(){
		List<EnterpriseBasic> pbs = particularFac.findEnterpriseBasicAll();
		Struts2Util.renderJson(JSONUtil.toJSON(pbs));
	}
}