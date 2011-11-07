/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

/**
 * 文章状态
 *
 * @author 吴智俊
 */
public enum ArticleStatus {
	DRAFT("初稿"),REEDIT("重新编辑"),REVIEW("审核中"),REVIEWBREAK("审核中断"),PRERELEASE("发布版"),RELEASE("已发布");
	
	private String description;
	
	private ArticleStatus(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
