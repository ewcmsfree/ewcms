/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.model;

/**
 * 出现频率枚举
 * 
 * @author wu_zhijun
 */
public enum FrequencyStatus {
	SINGLE("单次"), EVERYDAY("每天"), EVERYWEEK("每周"), EVERYMONTHWEEK("每月(星期)"), EVERYMONTH("每月(日)"), EVERYYEAR("每年");

	private String description;

	private FrequencyStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
