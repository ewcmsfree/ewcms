/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

/**
 * 问卷调查主体查看状态
 * 
 * @author wu_zhijun
 */
public enum QuestionnaireStatus {
	VIEW("直接查看"),VOTEVIEW("投票后查看"),NOVIEW("不允许查看");
	
	private String description;
	
	private QuestionnaireStatus(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
