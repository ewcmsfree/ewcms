/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.model.UserInfo;
import com.ewcms.security.manage.service.GroupServiceable;
import com.ewcms.security.manage.service.UserServiceException;
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
    public boolean hasGroupname(String name) {
        return groupService.hasGroupname(name);
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
    public String addUser(String username, String password, boolean enabled,
            Date accountStart, Date accountEnd, UserInfo userInfo)
            throws UserServiceException {
        
        return userService.addUser(username, password, enabled,
                accountStart, accountEnd, userInfo);
    }

    @Override
    public String updateUser(String username, boolean enabled,
            Date accountStart, Date accountEnd, UserInfo userInfo)
            throws UserServiceException {
        
        return userService.updateUser(username, enabled, 
                accountStart, accountEnd, userInfo);
    }
    
    @Override
    public User getUser(String username) {
        return userService.getUser(username);
    }
    
    @Override
    public void removeUser(String username) {
        userService.removeUser(username);
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
    
    @Override
    public void activeUser(String username) {
        userService.activeUser(username);
        
    }

    @Override
    public void inactiveUser(String username) {
        userService.inactiveUser(username);
    }
    
    @Override
    public boolean hasUsername(String username) {
        return userService.hasUsername(username);
    }
    
    @Override
    public String getDefaultPassword() {
        return userService.getDefaultPassword();
    }
    
    public void setGroupSerivce(GroupServiceable groupSerivce) {
        this.groupService = groupSerivce;
    }
    
    public void setUserService(UserServiceable userService){
        this.userService = userService;
    }
}
