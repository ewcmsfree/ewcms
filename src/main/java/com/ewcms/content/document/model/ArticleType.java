/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

/**
 * 文章类型
 *
 * @author 吴智俊
 */
public enum ArticleType {
	GENERAL("普通新闻"),TITLE("标题新闻");
	
	private String description;
	
	private ArticleType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

}
