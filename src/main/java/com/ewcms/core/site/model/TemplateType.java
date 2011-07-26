/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.model;

public enum TemplateType {
    HOME("专栏首页"),LIST("专栏列表"),DETAIL("文章内容");
	private String description;
	
	private TemplateType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
