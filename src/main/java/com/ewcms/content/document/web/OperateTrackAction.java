/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class OperateTrackAction extends ActionSupport {

	private static final long serialVersionUID = 905066626239705845L;

	private Long articleMainId;

	public Long getArticleMainId() {
		return articleMainId;
	}

	public void setArticleMainId(Long articleMainId) {
		this.articleMainId = articleMainId;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
