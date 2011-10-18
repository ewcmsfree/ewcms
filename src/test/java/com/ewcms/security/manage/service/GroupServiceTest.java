/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

import com.ewcms.security.core.session.EwcmsSessionRegistry;
import com.ewcms.security.core.session.EwcmsSessionRegistryImpl;
import com.ewcms.security.manage.dao.AuthorityDAOable;
import com.ewcms.security.manage.dao.GroupDAOable;
import com.ewcms.security.manage.dao.UserDAOable;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;

/**
 * Test {@link GroupService}
 * 
 * @author wangwei
 */
public class GroupServiceTest extends TestCase{

    @Test
    public void testGroupnameNotExist(){
        GroupService service = new GroupService();
        GroupDAOable dao = mock(GroupDAOable.class);
        when(dao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(dao);
        assertFalse(service.isGroupnameExist("GROUP_USER"));
    }
    
    @Test
    public void testGroupnameExist(){
        GroupService service = new GroupService();
        GroupDAOable dao = mock(GroupDAOable.class);
        when(dao.get(any(String.class))).thenReturn(new Group());
        service.setGroupDao(dao);
        assertTrue(service.isGroupnameExist("Pertty"));
    }
    
    @Test
    public void testAddGroupGroupnameExist(){
        GroupService service = new GroupService();
        GroupDAOable dao = mock(GroupDAOable.class);
        when(dao.get(any(String.class))).thenReturn(new Group());
        service.setGroupDao(dao);
        try{
            service.addGroup("Pertty", null);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testAddGroupNamefull()throws UserServiceException{
        GroupService service = new GroupService();
        String nameFull = service.groupNameFull("user");
        assertEquals(nameFull,"GROUP_user");
        nameFull = service.groupNameFull("GROUP_ADMIN");
        assertEquals(nameFull,"GROUP_ADMIN");
    }
    
    @Test
    public void testAddGroup()throws UserServiceException{
        GroupService service = new GroupService();
        
        GroupDAOable dao = mock(GroupDAOable.class);
        when(dao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(dao);
        
        service.addGroup("Pertty", "Pertty is group");
        verify(dao).persist(any(Group.class));
    }
    
    @Test
    public void testUpdateGroupNotExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(groupDao);
        
        try{
            service.updateGroup("GROUP_ADMIN", null);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testUpdateGroup(){
        GroupService service = new GroupService();
        
        Group group = new Group("GROUP_ADMIN","administraction group");
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(group);
        service.setGroupDao(groupDao);
        
        service.updateGroup("GROUP_ADMIN", "new administracation group");
        
        verify(groupDao).persist(any(Group.class));
    }
    
    @Test
    public  void testRemoveAuthsInGroupButGroupNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(groupDao);
        try{
            service.removeAuthsInGroup("GROUP_ADMIN", new HashSet<String>());
            fail();
        }catch(UserServiceException e){
             
        }
    }
    
    @Test
    public void testRemoveAuthsInGroup()throws UserServiceException{
        GroupService service = new GroupService();
        
        Group group = new Group();
        group.setName("GROUP_ADMIN");
        Set<Authority> authorities= new HashSet<Authority>();
        authorities.add(new Authority("ROLE_ADMIN"));
        authorities.add(new Authority("ROLE_USER"));
        group.setAuthorities(authorities);
        Set<User> users= new HashSet<User>();
        users.add(new User("admin"));
        users.add(new User("user"));
        group.setUsers(users);
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(group);
        service.setGroupDao(groupDao);
        
        EwcmsSessionRegistry sessionRegistry = new EwcmsSessionRegistryImpl();
        sessionRegistry.registerNewSession("adminid", "admin");
        sessionRegistry.registerNewSession("userid", "user");
        sessionRegistry.registerNewSession("perttyid", "pertty");
        service.setSessionRegistry(sessionRegistry);
        
        Set<String> removeAuthNames = new HashSet<String>();
        removeAuthNames.add("ROLE_USER");
        service.removeAuthsInGroup("GROUP_ADMIN", removeAuthNames);
        
        assertTrue(group.getAuthorities().size() == 1);
        assertEquals(group.getAuthorities().iterator().next().getName(),"ROLE_ADMIN");
        assertTrue(sessionRegistry.getAllSessions("admin", true).isEmpty());
        assertTrue(sessionRegistry.getAllSessions("user", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("pertty", true).isEmpty());
    }
    
    @Test
    public  void testRemoveUsersInGroupButGroupNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(groupDao);
        try{
            service.removeUsersInGroup("GROUP_ADMIN", new HashSet<String>());
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testRemoveUsersInGroup()throws UserServiceException{
        GroupService service = new GroupService();
        
        Group group = new Group();
        group.setName("GROUP_ADMIN");
        Set<User> users= new HashSet<User>();
        users.add(new User("admin"));
        users.add(new User("pertty"));
        group.setUsers(users);
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(group);
        service.setGroupDao(groupDao);
        
        EwcmsSessionRegistry sessionRegistry = new EwcmsSessionRegistryImpl();
        sessionRegistry.registerNewSession("adminid", "admin");
        sessionRegistry.registerNewSession("userid", "user");
        sessionRegistry.registerNewSession("perttyid", "pertty");
        service.setSessionRegistry(sessionRegistry);
        
        Set<String> removeUsernames = new HashSet<String>();
        removeUsernames.add("admin");
        service.removeUsersInGroup("GROUP_ADMIN", removeUsernames);
        
        assertTrue(group.getUsers().size() == 1);
        assertEquals(group.getUsers().iterator().next().getUsername(),"pertty");
        assertTrue(sessionRegistry.getAllSessions("admin", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("user", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("pertty", true).isEmpty());
    }
    
    @Test
    public void testAddUsersToGroupButGroupNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(groupDao);
        try{
            Set<String> usernames = new HashSet<String>();
            usernames.add("Jons");
            service.addUsersToGroup("GROUP_ADMIN", usernames);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testAddUsersToGroupButUserNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(new Group());
        service.setGroupDao(groupDao);
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get(any(String.class))).thenReturn(null);
        service.setUserDao(userDao);
        try{
            Set<String> usernames = new HashSet<String>();
            usernames.add("Jons");
            service.addUsersToGroup("GROUP_ADMIN", usernames);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testAddUserToGroup(){
        GroupService service = new GroupService();

        Group group = new Group("GROUP_ADMIN");
        Set<User> users= new HashSet<User>();
        users.add(new User("pertty"));
        group.setUsers(users);
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(group);
        service.setGroupDao(groupDao);
        UserDAOable userDao = mock(UserDAOable.class);
        when(userDao.get("admin")).thenReturn(new User("admin"));
        when(userDao.get("pertty")).thenReturn(new User("pertty"));
        service.setUserDao(userDao);
        
        EwcmsSessionRegistry sessionRegistry = new EwcmsSessionRegistryImpl();
        sessionRegistry.registerNewSession("adminid", "admin");
        sessionRegistry.registerNewSession("userid", "user");
        sessionRegistry.registerNewSession("perttyid", "pertty");
        service.setSessionRegistry(sessionRegistry);
        
        Set<String> usernames = new HashSet<String>();
        usernames.add("admin");
        usernames.add("pertty");
        Set<User> newUsers = service.addUsersToGroup("GROUP_ADMIN",usernames);
        
        assertTrue(group.getUsers().size() == 2);
        assertTrue(newUsers.size() == 1);
        assertEquals(newUsers.iterator().next().getUsername(),"admin");
        assertTrue(sessionRegistry.getAllSessions("admin", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("user", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("pertty", true).isEmpty());
    }
    
    @Test
    public void testAddAuthoritiesToGroupButGroupNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(null);
        service.setGroupDao(groupDao);
        try{
            Set<String> usernames = new HashSet<String>();
            usernames.add("Jons");
            service.addUsersToGroup("GROUP_ADMIN", usernames);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testAddAuthoritiesToGroupButAuthorityNoExist(){
        GroupService service = new GroupService();
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(new Group());
        service.setGroupDao(groupDao);
        AuthorityDAOable authDao = mock(AuthorityDAOable.class);
        when(authDao.get(any(String.class))).thenReturn(null);
        service.setAuthorityDao(authDao);
        try{
            Set<String> authnames = new HashSet<String>();
            authnames.add("ROLE_USER");
            service.addAuthoritiesToGroup("GROUP_ADMIN", authnames);
            fail();
        }catch(UserServiceException e){
            
        }
    }
    
    @Test
    public void testAddAuthoritiesToGroup(){
        GroupService service = new GroupService();

        Group group = new Group("GROUP_ADMIN");
        Set<Authority> auths= new HashSet<Authority>();
        auths.add(new Authority("ROLE_ADMIN"));
        group.setAuthorities(auths);
        Set<User> users= new HashSet<User>();
        users.add(new User("pertty"));
        group.setUsers(users);
        
        GroupDAOable groupDao = mock(GroupDAOable.class);
        when(groupDao.get(any(String.class))).thenReturn(group);
        service.setGroupDao(groupDao);
        AuthorityDAOable authDao = mock(AuthorityDAOable.class);
        when(authDao.get("ROLE_ADMIN")).thenReturn(new Authority("ROLE_ADMIN"));
        when(authDao.get("ROLE_USER")).thenReturn(new Authority("ROLE_USER"));
        service.setAuthorityDao(authDao);
        
        EwcmsSessionRegistry sessionRegistry = new EwcmsSessionRegistryImpl();
        sessionRegistry.registerNewSession("adminid", "admin");
        sessionRegistry.registerNewSession("userid", "user");
        sessionRegistry.registerNewSession("perttyid", "pertty");
        service.setSessionRegistry(sessionRegistry);
        
        Set<String> authnames = new HashSet<String>();
        authnames.add("ROLE_ADMIN");
        authnames.add("ROLE_USER");
        Set<Authority> newAuthorities = service.addAuthoritiesToGroup("GROUP_ADMIN",authnames);
        
        assertTrue(group.getAuthorities().size() == 2);
        assertTrue(newAuthorities.size() == 1);
        assertEquals(newAuthorities.iterator().next().getName(),"ROLE_USER");
        
        assertFalse(sessionRegistry.getAllSessions("admin", true).isEmpty());
        assertFalse(sessionRegistry.getAllSessions("user", true).isEmpty());
        assertTrue(sessionRegistry.getAllSessions("pertty", true).isEmpty());
    }
}
