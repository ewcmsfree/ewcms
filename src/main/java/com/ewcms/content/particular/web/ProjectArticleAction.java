/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class ProjectArticleAction extends CrudBaseAction<ProjectArticle, Long> {

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

	public ProjectArticle getProjectArticleVo() {
		return super.getVo();
	}

	public void setProjectArticleVo(ProjectArticle projectArticle) {
		super.setVo(projectArticle);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ProjectArticle vo) {
		return vo.getId();
	}

	@Override
	protected ProjectArticle getOperator(Long pk) {
		return particularFac.findProjectArticleById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delProjectArticle(pk);
	}

	@Override
	protected Long saveOperator(ProjectArticle vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updProjectArticle(vo);
		} else {
			return particularFac.addProjectArticle(vo);
		}
	}

	@Override
	protected ProjectArticle createEmptyVo() {
		return new ProjectArticle();
	}
	
	public void findPbAll(){
		List<ProjectBasic> pbs = particularFac.findProjectBasicAll();
		Struts2Util.renderJson(JSONUtil.toJSON(pbs));
	}
}