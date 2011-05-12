/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.jict.org
 */
package com.ewcms.security.manage.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
     * @param names 用户名集合
     */
    private void removeExpiredUseByUsernamesr(final Collection<String> names){
        for(Iterator<String> iterator = names.iterator(); iterator.hasNext();){
            String name = iterator.next();
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
    public void addGroup(final String name,final String remark,final Set<String> authNames,final Set<String> usernames)throws UserServiceException {
        
        if(isGroupnameExist(name)){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupnameExist","groupname exist"));
        }
        
        String namefull = groupNameFull(name);
        Group group = new Group(namefull,remark);
        if(authNames != null){
            group.setAuthorities(getAuthoritiesByNames(authNames));
        }
        if(usernames != null){
            group.setUsers(getUsersByNames(usernames));
        }
        groupDao.persist(group);
        
        if(usernames != null){
            removeExpiredUseByUsernamesr(usernames);
        }
    }

    @Override
    public void updateGroup(final String name,final String remark,final Set<String> authNames,final Set<String> usernames)throws UserServiceException {
        Assert.notNull(authNames,"authNames is null");
        Assert.notNull(usernames,"usernames is null");
        
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        
        group.setRemark(remark);
        Collection<Authority> deffAuthorities = syncCollection(
                group.getAuthorities(),getAuthoritiesByNames(authNames));
        Collection<User> deffUsers = syncCollection(
                group.getUsers(),getUsersByNames(usernames));
        groupDao.persist(group);
        
        if(!deffAuthorities.isEmpty()){
            for(User user : group.getUsers()){
                removeExpiredUserByUsername(user.getUsername());
            }
        }
        if(!deffUsers.isEmpty()){
            for(User user : deffUsers){
                removeExpiredUserByUsername(user.getUsername());
            }
        }
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
        
        return newAuths;
    }

    @Override
    public void removeUserInGroup(final String name,final String username) {
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        if(group.getUsers().isEmpty()){
            return ;
        }
        for(User user : group.getUsers()){
            if(user.getUsername().equals(username)){
                group.getUsers().remove(user);
                break;
            }
        }
        groupDao.persist(group);
    }

    @Override
    public void removeAuthInGroup(final String name,final String authName) throws UserServiceException{
        Group group = groupDao.get(name);
        if(group == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{name},"Can't found  "+ name + " gruop"));
        }
        if(group.getAuthorities().isEmpty()){
            return ;
        }
        for(Authority auth : group.getAuthorities()){
            if(auth.getName().equals(authName)){
                group.getAuthorities().remove(auth);
                break;
            }
        }
        groupDao.persist(group);
    }
    
    @Override
    public boolean isGroupnameExist(final String name) {
        Group group = groupDao.get(name);
        return group != null;
    }
}
