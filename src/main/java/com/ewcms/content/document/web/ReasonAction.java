/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;


import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 *
 */
public class ReasonAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DocumentFacable documentFac;
	
	private Long trackId;
	private String result;

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String execute() throws Exception {
		if (getTrackId() != null ){
			setResult(documentFac.getArticleOperateTrack(getTrackId()));
		}
		return SUCCESS;
	}
}
