/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EmployeArticle;
import com.ewcms.content.particular.model.EmployeBasic;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
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
	
	private String organShow;
	
	private Integer organId;

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

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
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
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}else{
			organShow = "disable";
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				organId = organ.getId();
			}
		}
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
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}else{
			organShow = "disable";
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				organId = organ.getId();
			}
		}
		return new EmployeArticle();
	}
	
	public void findMbAll(){
		List<EmployeBasic> pbs = particularFac.findEmployeBasicAll();
		Struts2Util.renderJson(JSONUtil.toJSON(pbs));
	}
	
	public void pub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.pubEmployeArticle(getChannelId(), getSelections());
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
				particularFac.unPubEmployeArticle(getChannelId(), getSelections());
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