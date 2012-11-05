/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class TemplateHistoryAction extends ActionSupport {

	private static final long serialVersionUID = -7033704041589731104L;

	@Autowired
	private SiteFacable siteFac;
	
	private Integer templateId;

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	private Long historyId;
	
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public void restore(){
		try{
			if (getHistoryId() != null && getHistoryId().longValue() > 0){
				Boolean result = siteFac.restoreTemplate(getTemplateId(), getHistoryId());
				Struts2Util.renderJson(JSONUtil.toJSON(result.toString()));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
