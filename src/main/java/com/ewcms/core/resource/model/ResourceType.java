/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.resource.model;

/**
 *
 * @author 吴智俊
 */
public enum ResourceType {
	ANNEX("附件"),
        IMAGE("图片"),
        FLASH("Flash"),
        VIDEO("视频"),
        SOURCE("网站资源");
	
	private String description;
	
	private ResourceType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
