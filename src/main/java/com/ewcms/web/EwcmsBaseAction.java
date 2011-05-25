/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.web;

import org.springframework.security.core.userdetails.UserDetails;

import com.ewcms.core.site.model.Site;
import com.ewcms.web.util.EwcmsContextUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 周冬初
 *
 */
public class EwcmsBaseAction extends ActionSupport{  
	
    /**
	 * 信息输出处理
	 * 
	 * @deprecated 程序引入正常的日志
	 */
    public void outputInfo(String e){
    	System.out.print(e);
    }
    
	/**
	 * 获取当前站点
	 * 
	 * return Site
	 */    
    public Site getCurrentSite(){
    	return EwcmsContextUtil.getCurrentSite();
    }

    /**
     * 获得当前用户
     * 
     * @return UserDetails
     */
    public static UserDetails getUserDetails(){
        return EwcmsContextUtil.getUserDetails();
    }
}
