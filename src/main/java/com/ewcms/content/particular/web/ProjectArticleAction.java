/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ProjectArticle;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
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

	private String organShow = "disable";
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ProjectArticle getProjectArticleVo() {
		return super.getVo();
	}

	public String getOrganShow() {
		return organShow;
	}

	public void setOrganShow(String organShow) {
		this.organShow = organShow;
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
		if (EwcmsContextUtil.getAutoritynames().contains("ROLE_ADMIN")){
			organShow = "enable";
		}
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
		ProjectArticle projectArticle = new ProjectArticle();
		if (EwcmsContextUtil.getAutoritynames().contains("ROLE_ADMIN")){
			organShow = "enable";
		}else{
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				projectArticle.setOrgan(organ);
			}
		}
		return projectArticle;
	}
	
	private int page; //当前页,名字必须为page  
	private int rows ; //每页大小,名字必须为rows  
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void findPbAll(){
		List<ProjectBasic> pbs = particularFac.findProjectBasicByPageAndRows(page, rows);
		Long total = particularFac.findProjectBasicTotal();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", pbs);
		Struts2Util.renderJson(JSONUtil.toJSON(result));
	}
	
	public void pub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.pubProjectArticle(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void unPub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.unPubProjectArticle(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

}