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
import com.ewcms.content.particular.model.EnterpriseArticle;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
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
	
	private String organShow = "disable";
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getOrganShow() {
		return organShow;
	}

	public void setOrganShow(String organShow) {
		this.organShow = organShow;
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
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			organShow = "enable";
		}
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
		EnterpriseArticle enterpriseArticle = new EnterpriseArticle();
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			organShow = "enable";
		}else{
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				enterpriseArticle.setOrgan(organ);
			}
		}
		return enterpriseArticle;
	}
	
	private int page; //当前页,名字必须为page  
	private int rows ; //每页大小,名字必须为rows  
	private String name;
	
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void findEbAll(){
		List<EnterpriseBasic> pbs = particularFac.findEnterpriseBasicByPageAndRows(page, rows, name);
		Long total = particularFac.findEnterpriseBasicTotal(name);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", pbs);
		Struts2Util.renderJson(JSONUtil.toJSON(result));
	}
	
	public void pub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.pubEnterpriseArticle(getChannelId(), getSelections());
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
				particularFac.unPubEnterpriseArticle(getChannelId(), getSelections());
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