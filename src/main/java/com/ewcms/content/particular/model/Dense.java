/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.model;

public enum Dense {
	GENERAL("普通"), TOPSECRET("绝密"), CONFIDENCE("机密"), ARCANUM("秘密");

	private String description;

	private Dense(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
