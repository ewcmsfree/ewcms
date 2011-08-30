/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.model;

/**
 * @author 周冬�?
 *
 */
public enum OutputType {
    LOCAL("本地"),SFTP("sftp"),FTP("ftp"),SMB("windows共享");
	private String description;
	
	private OutputType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
}
