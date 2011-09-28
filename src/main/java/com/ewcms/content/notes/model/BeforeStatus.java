/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.notes.model;

/**
 * 提前提醒枚举
 * 
 * @author 吴智�?
 */
public enum BeforeStatus {
	NONE("正点"), ONE("1分钟"), FIVE("5分钟"), TEN("10分钟"), FIFTEEN("15分钟"), TWENTY("20分钟"), TWENTYFIVE("25分钟"), THIRTY("30分钟"), FORTYFIVE("45分钟"), ONEHOUR("1小时"), TWOHOUR("2小时"), THREEHOUR("3小时"), TWELVEHOUR("12小时"), TWENTYFOUR("24小时"), TWODAY("2�?), ONEWEEK("1�?);

	private String description;

	private BeforeStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
