/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.model;

/**
 * 
 * @author wu_zhijun
 *
 */
public enum MsgType {
	GENERAL("普通"),	NOTICE("公告"),SUBSCRIPTION("订阅");
	
	private String description;

	private MsgType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
