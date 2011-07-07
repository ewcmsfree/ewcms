/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

/**
 * 问卷调查主题选项状态
 * 
 * @author wu_zhijun
 */
public enum SubjectStatus {
	RADIO("单选"),OPTION("多选"),INPUT("录入");
	
	private String description;
	
	private SubjectStatus(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
