/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.model;

/**
 * 
 * @author 吴智俊
 */
public enum ResourceType {
	ANNEX("附件"), IMAGE("图片"), FLASH("Flash"), VIDEO("视频"), SOURCE("网站资源");

	private String description;

	private ResourceType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
