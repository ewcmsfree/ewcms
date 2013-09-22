/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.service.AuthorityServiceable;
import com.ewcms.security.manage.service.GroupServiceable;
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
    
    private static Collection<String> getGrantedAuthorities(String perfix){
        
        List<String> names = new ArrayList<String>();
        
        UserDetails user = getUserDetails();
        if(user == null){
            return names;
        }
        
        Collection<GrantedAuthority> authorites = user.getAuthorities();
        for(GrantedAuthority auth: authorites){
            if(StringUtils.startsWith(auth.getAuthority(),perfix)){
                names.add(auth.getAuthority());
            }
        }
        
        return names;
    }
    
    public static Collection<String> getGroupnames(){
        return getGrantedAuthorities(GroupServiceable.GROUP_NAME_PERFIX);
    }
    
    public static Collection<String> getAutoritynames(){
        return getGrantedAuthorities(AuthorityServiceable.Authority_NAME_PERFIX);
    }
    
    public static UserDetails getUserDetails(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
    }
    
    public static String getUserName(){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null){
            return "";
        }
        return currentUser.getName();
    }
}
