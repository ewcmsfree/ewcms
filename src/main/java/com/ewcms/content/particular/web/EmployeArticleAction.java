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
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			organShow = "enable";
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
		EmployeArticle employeArticle = new EmployeArticle();
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_GCLY_ADMIN")){
			organShow = "enable";
		}else{
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				employeArticle.setOrgan(organ);
			}
		}
		return employeArticle;
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

	public void findMbAll(){
		List<EmployeBasic> pbs = particularFac.findEmployeBasicByPageAndRows(page, rows, name);
		Long total = particularFac.findEmployeBasicTotal(name);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", pbs);
		Struts2Util.renderJson(JSONUtil.toJSON(result));
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