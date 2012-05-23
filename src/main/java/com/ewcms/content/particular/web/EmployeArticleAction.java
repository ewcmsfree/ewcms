/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class EmployeArticleAction extends CrudBaseAction<EmployeArticle, Long> {

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

	public EmployeArticle getEmployeArticleVo() {
		return super.getVo();
	}

	public void setEmployeArticleVo(EmployeArticle employeArticle) {
		super.setVo(employeArticle);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(EmployeArticle vo) {
		return vo.getId();
	}

	@Override
	protected EmployeArticle getOperator(Long pk) {
		return particularFac.findEmployeArticleById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delEmployeArticle(pk);
	}

	@Override
	protected Long saveOperator(EmployeArticle vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updEmployeArticle(vo);
		} else {
			return particularFac.addEmployeArticle(vo);
		}
	}

	@Override
	protected EmployeArticle createEmptyVo() {
		return new EmployeArticle();
	}
	
	public void findMbAll(){
		List<EmployeBasic> pbs = particularFac.findEmployeBasicAll();
		Struts2Util.renderJson(JSONUtil.toJSON(pbs));
	}
}