/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.model;

/**
 *
 * @author 吴智俊
 */
public enum ArticleStatus {
	GENERAL("普通新闻"),TITLE("标题新闻");
	
	private String description;
	
	private ArticleStatus(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

}
