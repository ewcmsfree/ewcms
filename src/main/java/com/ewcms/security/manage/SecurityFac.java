/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.service.GroupServiceable;

/**
 * 实现权限管理
 * 
 * @author wangwei
 */
@Service
public class SecurityFac implements SecurityFacable{

    @Autowired
    private GroupServiceable groupService;
    
    @Override
    public Group getGroup(String name) {
        return groupService.getGroup(name);
    }

    @Override
    public void addAuthsAndUsersToGroup(String name,
            Set<String> authNames,Set<String> usernames) {
        
        if(authNames != null && !authNames.isEmpty()){
            groupService.addAuthoritiesToGroup(name, authNames);
        }
        
        if(usernames !=null && !usernames.isEmpty()){
            groupService.addUsersToGroup(name, usernames);
        }
    }
    
    @Override
    public void removeAuthsAndUsersInGroup(String name, 
            Set<String> authNames,Set<String> usernames) {
        
        if(authNames != null && !authNames.isEmpty()){
            groupService.removeAuthsInGroup(name, authNames);    
        }
        
        if(usernames != null && !usernames.isEmpty()){
            groupService.removeUsersInGroup(name, usernames);
        }
    }
    

    @Override
    public boolean isGroupnameExist(String name) {
        return groupService.isGroupnameExist(name);
    }
    
    @Override
    public String addGroup(String name, String remark) {
        return groupService.addGroup(name, remark);
        
    }

    @Override
    public void updateGroup(String name, String remark) {
        groupService.updateGroup(name, remark);
    }
    
    @Override
    public void removeGroup(String name) {
        groupService.removeGroup(name);
    }
    
    public void setGroupSerivce(GroupServiceable groupSerivce) {
        this.groupService = groupSerivce;
    }
}
