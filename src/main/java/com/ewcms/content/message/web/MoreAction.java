/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.web;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author 吴智俊
 */
public class MoreAction extends ActionSupport {

	private static final long serialVersionUID = -4080921684611100928L;
	
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
