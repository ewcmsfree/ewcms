/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

/**
 * 问卷调查主题明细选项状态
 * 
 * @author wu_zhijun
 */
public enum SubjectItemStatus {
	CHOOSE("选项"),SINGLETEXT("单行文本"),MULTITEXT("多行文本");
	
	private String description;
	
	private SubjectItemStatus(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
