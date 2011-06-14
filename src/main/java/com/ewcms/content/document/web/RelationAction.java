/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class RelationAction extends ActionSupport {

	private static final long serialVersionUID = -7033704041589731104L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Long articleId;
	private Long selectIds[];

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
	public Long[] getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(Long[] selectIds) {
		this.selectIds = selectIds;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String save(){
		documentFac.saveRelated(getArticleId(), getSelectIds());
		return NONE;
	}
	
	public String delete(){
		documentFac.deleteRelated(getArticleId(), getSelectIds());
		return NONE;
	}
	
	public void up(){
		try{
			if (getArticleId() != null && getSelectIds() != null){
				documentFac.upRelated(getArticleId(), getSelectIds());
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
			if (getArticleId() != null && getSelectIds() != null){
				documentFac.downRelated(getArticleId(), getSelectIds());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}

	}
}
