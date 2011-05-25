/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
