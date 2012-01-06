/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.model;

import com.ewcms.publication.deploy.DeployOperatorFactory;
import com.ewcms.publication.deploy.DeployOperatorable;

/**
 * 输出服务类型
 * 
 * @author 周冬初
 */
public enum OutputType {
    
    LOCAL("本地"),SFTP("sftp"),FTP("ftp"),FTPS("ftps"),SMB("windows共享");
    
	private String description;
	
	private OutputType(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public DeployOperatorable deployOperator(SiteServer server){
	    return DeployOperatorFactory.factory(server);
	}
}
