/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;

@Service
public class GroupService extends AbstractService implements GroupServiceable{

    static final Logger logger = LoggerFactory.getLogger(GroupService.class);
    
    private static final String GROUP_NAME_PERFIX = "GROUP_";
    
    /**
     * 移除过时的用户
     * 
     * @param users 用户集合
     */
    private void removeExpiredUsers(final Collection<User> users){
        for(User user : users){
            String name = user.getUsername();
            removeExpiredUserByUsername(name);
        }
    }
    
    /**
     * 通过用户名移除过时的用户
     * 
     * @param names 用户名集合
     */
    private void removeExpiredUseByUsernames(final Collection<String> names){
        for(String name : names){
            removeExpiredUserByUsername(name);
        }
    }
    
    /**
     * 得到完整用户组名
     * 
     * GROUP_在通用权限中标志为用户组权限。
     * 
     * @param name 输入的名称
     * @return
     */
    String groupNameFull(final String name){
        if(!name.startsWith(GROUP_NAME_PERFIX)){
            return GROUP_NAME_PERFIX + name;
        }
        return name;
    }
    
    @Override
    public boolean isGroupnameExist(final String name) {
        Group group = groupDao.get(groupNameFull(name));
        return group != null;
    }
    
    @Override
    public String addGroup(final String name,final String remark)throws UserServiceException {
        
        if(isGroupnameExist(name)){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupnameExist","groupname exist"));
        }
        
        String namefull = groupNameFull(name);
        Group group = new Group(namefull,remark);
        groupDao.persist(group);
        
        return namefull;
    }

    @Override
    public void updateGroup(final String name,final String remark)throws UserServiceException {
        
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        
        group.setRemark(remark);
        groupDao.persist(group);
    }

    @Override
    public void removeGroup(final String name) {
        //TODO ACL result set clean;
        groupDao.removeByPK(name);
    }
    
    @Override
    public Group getGroup(final String name) {
        return groupDao.get(name);
    }
    
    @Override
    public Set<User> addUsersToGroup(final String name,final Set<String> usernames) {
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        Set<User> newUsers = new HashSet<User>();
        for(String username : usernames){
            User user = getUserByUsername(username);
            if(!group.getUsers().contains(user)){
                newUsers.add(user);
            }
        }
        group.getUsers().addAll(newUsers);
        groupDao.persist(group);
        
        removeExpiredUsers(newUsers);
        
        return newUsers;
    }

    @Override
    public Set<Authority> addAuthoritiesToGroup(final String name,final Set<String> authNames) {
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        Set<Authority> newAuths = new HashSet<Authority>(); 
        for(String authName : authNames){
            Authority auth = getAuthorityByName(authName);
            if(!group.getAuthorities().contains(auth)){
                newAuths.add(auth);
            }
        }
        group.getAuthorities().addAll(newAuths);
        groupDao.persist(group);
        
        removeExpiredUsers(group.getUsers());
        
        return newAuths;
    }

    @Override
    public void removeUsersInGroup(final String name,final Set<String> usernames) {
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        if(group.getUsers().isEmpty()){
            return ;
        }
       Set<User> newUsers = new HashSet<User>();
       for(User user : group.getUsers()){
           if(!usernames.contains(user.getUsername())){
               newUsers.add(user);                    
             }
        }

       group.setUsers(newUsers);
       groupDao.persist(group);
       removeExpiredUseByUsernames(usernames);
    }

    @Override
    public void removeAuthsInGroup(final String name,final Set<String> authNames) throws UserServiceException{
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        if(group.getAuthorities().isEmpty()){
            return ;
        }
        Set<Authority> newAuths = new HashSet<Authority>();
        for(Authority auth : group.getAuthorities()){
            if(!authNames.contains(auth.getName())){
                newAuths.add(auth);
            }
        }
        group.setAuthorities(newAuths);
        groupDao.persist(group);
        removeExpiredUsers(group.getUsers());
    }
}
