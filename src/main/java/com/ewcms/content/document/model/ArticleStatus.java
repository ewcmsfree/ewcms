/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

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
