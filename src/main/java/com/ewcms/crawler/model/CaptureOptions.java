/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.model;

/**
 * 
 * @author wuzhijun
 *
 */
public enum CaptureOptions {
	download("下载文件"),Removed("去除链接");
	
	private String description;
	
	private CaptureOptions(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
 