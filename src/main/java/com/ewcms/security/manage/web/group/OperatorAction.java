/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.group;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.web.JsonBaseAction;

/**
 * 用户组操作，实现权限或用户的添加和删除。
 * 
 * @author wangwei
 */
@Controller("security.group.operator.action")
public class OperatorAction extends JsonBaseAction{

    private Set<String> authNames;
    private Set<String> usernames;
    private String name;
    
    @Autowired
    private SecurityFacable fac;
    
    /**
     * 添加权限和用户
     */
    public void addAuthsAndUsers(){
        fac.addAuthsAndUsersToGroup(name, authNames, usernames);
        renderSuccess();
    }
    
    /**
     * 删除权限和用户
     */
    public void removeAuthsAndUsers(){
        fac.removeAuthsAndUsersInGroup(name, authNames, usernames);
        renderSuccess();
    }
    
    /**
     * 判断用户组名称是否存在
     */
    public void hasName(){
        String format = "{\"exist\":%b}";
        boolean exist = fac.isGroupnameExist(name);
        render(String.format(format, exist));
    }

    public Set<String> getAuthNames() {
        return authNames;
    }

    public void setAuthNames(Set<String> authNames) {
        this.authNames = authNames;
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Set<String> usernames) {
        this.usernames = usernames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
