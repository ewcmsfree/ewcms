/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

/**
 * @deprecated
 * @author wangwei
 *
 */
public enum GlobaPath {
    
	ResourceDir("/pub_res"),
    DocumentDir("/document");
	
	private String path;

	private GlobaPath(String path){ 
		this.path = path;
	}
	public String getPath(){
		return path;
	}
}
