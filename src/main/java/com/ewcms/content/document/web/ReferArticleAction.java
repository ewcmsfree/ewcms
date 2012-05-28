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

public class ReferArticleAction extends ActionSupport {

	private static final long serialVersionUID = 3392275424784371096L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;
	private Long[] articleMainIds;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Long[] getArticleMainIds() {
		return articleMainIds;
	}

	public void setArticleMainIds(Long[] articleMainIds) {
		this.articleMainIds = articleMainIds;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public void save(){
		try{
			documentFac.referArticleMain(getChannelId(), getArticleMainIds());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void delete(){
		try{
			documentFac.removeArticleMain(getArticleMainIds());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
