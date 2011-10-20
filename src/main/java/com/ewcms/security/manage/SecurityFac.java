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
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.GroupServiceable;
import com.ewcms.security.manage.service.UserServiceable;

/**
 * 实现权限管理
 * 
 * @author wangwei
 */
@Service
public class SecurityFac implements SecurityFacable{

    @Autowired
    private GroupServiceable groupService;
    
    @Autowired
    private UserServiceable userService;
    
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
            groupService.removeAuthoritiesInGroup(name, authNames);    
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
    
    @Override
    public User getUser(String username) {
        return userService.getUser(username);
    }
    
    @Override
    public void addAuthsAndGroupsToUser(String username, 
            Set<String> authNames, Set<String> groupNames) {
        
        if(authNames != null && !authNames.isEmpty()){
            userService.addAuthoritiesToUser(username, authNames);
        }
        
        if(groupNames != null && !groupNames.isEmpty()){
            userService.addGroupsToUser(username, groupNames);
        }
    }

    @Override
    public void removeAuthsAndGroupsInUser(String username, 
            Set<String> authNames, Set<String> groupNames) {
        
        if(authNames != null && !authNames.isEmpty()){
            userService.removeAuthoritiesInUser(username, authNames);
        }
        
        if(groupNames != null && !groupNames.isEmpty()){
            userService.removeGroupsInUser(username, groupNames);
        }
        
    }
    public void setGroupSerivce(GroupServiceable groupSerivce) {
        this.groupService = groupSerivce;
    }
    
    public void setUserService(UserServiceable userService){
        this.userService = userService;
    }
}
