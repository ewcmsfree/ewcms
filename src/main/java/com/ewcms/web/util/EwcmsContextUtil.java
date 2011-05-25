/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ewcms.core.site.model.Site;
import com.ewcms.web.context.EwcmsContextHolder;
import com.ewcms.web.context.EwcmsContextable;

/**
 *
 * @author wangwei
 */
public class EwcmsContextUtil {

    public static Site getCurrentSite() {
        EwcmsContextable context = EwcmsContextHolder.getContext();
        return context.getSite();
    }
    
    public static UserDetails getUserDetails(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
    }
    
    public static String getUserName(){
    	return getUserDetails().getUsername();
    }
}
