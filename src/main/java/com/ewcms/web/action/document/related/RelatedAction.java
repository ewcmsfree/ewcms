/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.related;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class RelatedAction extends ActionSupport {

	private static final long serialVersionUID = -7033704041589731104L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer articleRmcId;
	private Integer selectIds[];

	public Integer getArticleRmcId() {
		return articleRmcId;
	}

	public void setArticleRmcId(Integer articleRmcId) {
		this.articleRmcId = articleRmcId;
	}
	
	public Integer[] getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(Integer[] selectIds) {
		this.selectIds = selectIds;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String save(){
		documentFac.saveRelated(getArticleRmcId(), getSelectIds());
		return NONE;
	}
	
	public String delete(){
		documentFac.deleteRelated(getArticleRmcId(), getSelectIds());
		return NONE;
	}
	
	public void up(){
		try{
			if (getArticleRmcId() != null && getSelectIds() != null){
				documentFac.upRelated(getArticleRmcId(), getSelectIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void down(){
		try{
			if (getArticleRmcId() != null && getSelectIds() != null){
				documentFac.downRelated(getArticleRmcId(), getSelectIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}

	}
}
