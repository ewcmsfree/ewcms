/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Organ;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.model.UserInfo;

@Service
public class UserService extends AbstractService implements UserServiceable{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    private String defaultPassword = "666666";
    
    @Autowired(required = false)
    private AuthenticationManager authenticationManager;
    
    @Autowired(required = false)
    private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
    
    @Autowired(required = false)
    private SaltSource saltSource;
    
    @Autowired(required = false)
    private SiteFac siteFac;
    
    @Override
    public String addUser(final String username,final String password,
            final boolean enabled,final Date accountStart,final  Date accountEnd,
            final UserInfo userInfo, final Integer organId)throws UserServiceException {
    	Organ organ = null;
        if (organId != null){
        	organ = siteFac.getOrgan(organId);
        }
    	
        if(accountStart !=null && accountEnd != null ){
            Assert.isTrue(accountEnd.getTime() > accountStart.getTime(),"account date start > end");
        }
        
        if(hasUsername(username)){
            throw new UserServiceException(
                    messages.getMessage("UserService.usernameExist","username already exist"));
        }
        
        User user = new User(username,enabled,accountStart,accountEnd);
        UserInfo info = (userInfo != null ? userInfo : new UserInfo());
        info.setUsername(username);
        if(info.getName() == null){
            info.setName(username);
        }
        
        user.setUserInfo(info);
        user.setOrgan(organ);
        
        //初始用户密码
        String newPassword = StringUtils.isBlank(password) ? defaultPassword : password;
        user.setPassword(newPassword);  //创建UserDetails password不为空
        UserDetails userDetails = createUserDetails(user);
        user.setPassword(passwordEncoder(userDetails, newPassword));
        
        userDao.persist(user);
    
        return username;
    }
    
    /**
     * 得到存在的用户
     * 
     * 用户不存在抛出异常
     * 
     * @param username 用户名
     * @return  用户对象
     * @throws UserServiceException
     */
    private User getExistUser(final String username)throws UserServiceException{
        User user = userDao.get(username);
        if(user == null){
            throw new UserServiceException(messages.getMessage(
                    "GroupService.groupNotFound",new Object[]{username},"Can't found  "+ username + " username"));
        }
        return user;
    }
    
    @Override
    public String updateUser(final String username,
            final boolean enabled,final Date accountStart,
            final Date accountEnd,final UserInfo userInfo, final Integer organId) throws UserServiceException{
    	Organ organ = null;
    	if (organId != null){
    		organ = siteFac.getOrgan(organId);
    	}
    	
        if(accountStart !=null && accountEnd != null ){
            Assert.isTrue(accountEnd.getTime() > accountStart.getTime(),"account date start > end");
        }
        
        User user = getExistUser(username);
        user.setEnabled(enabled);
        user.setAccountStart(accountStart);
        user.setAccountEnd(accountEnd);
        user.setOrgan(organ);
        user.getUserInfo().setBirthday(userInfo.getBirthday());
        user.getUserInfo().setEmail(userInfo.getEmail());
        user.getUserInfo().setIdentification(userInfo.getIdentification());
        user.getUserInfo().setMphone(userInfo.getMphone());
        user.getUserInfo().setName(userInfo.getName());
        user.getUserInfo().setPhone(userInfo.getPhone());
        
        userDao.persist(user);
        
        return username;
    }
    
    @Override
    public User getUser(final String username) {
        return userDao.get(username);
    }

    @Override
    public void removeUser(final String username) {
        userDao.removeByPK(username);
        userCache.removeUserFromCache(username);
    }
    
    @Override
    public void activeUser(final String username)throws UserServiceException{
        User user  = getExistUser(username);
        user.setEnabled(true);
    }
    
    @Override
    public void inactiveUser(final String username)throws UserServiceException{
        User user  = getExistUser(username);
        user.setEnabled(false);
        userDao.persist(user);
        removeExpiredUserByUsername(username);
    }
    
    @Override
    public Set<Authority> addAuthoritiesToUser(String username,Set<String> names) throws UserServiceException{
        User user  = getExistUser(username);
        Set<Authority> newAuths =new HashSet<Authority>();
        for(String name : names){
            Authority newAuth = getAuthorityByName(name);
            if(!user.getAuthorities().contains(newAuth)){
                newAuths.add(newAuth);
            }
        }
        user.getAuthorities().addAll(newAuths);
        userDao.persist(user);
        
        removeExpiredUserByUsername(username);
        
        return newAuths;
    }

    @Override
    public void removeAuthoritiesInUser(String username, Set<String> names) throws UserServiceException{
        User user  = getExistUser(username);
        
        Set<Authority> auths = new HashSet<Authority>();
        for(Authority auth : user.getAuthorities()){
            if(!names.contains(auth.getName())){
                auths.add(auth);
            }
        }
        user.setAuthorities(auths);
        userDao.persist(user);
        
        removeExpiredUserByUsername(username);
    }

    @Override
    public Set<Group> addGroupsToUser(String username, Set<String> names) {
        User user  = getExistUser(username);
        
        Set<Group> newGroups =new HashSet<Group>();
        for(String name : names){
            Group newGroup = getGroupByName(name);
            if(!user.getGroups().contains(newGroup)){
                newGroups.add(newGroup);
            }
        }
        user.getGroups().addAll(newGroups);
        userDao.persist(user);
        
        removeExpiredUserByUsername(username);
        
        return newGroups;
    }

    @Override
    public void removeGroupsInUser(String username, Set<String> names) {
        User user  = getExistUser(username);
        
        Set<Group> groups = new HashSet<Group>();
        for(Group group : user.getGroups()){
            if(!names.contains(group.getName())){
                groups.add(group);
            }
        }
        user.setGroups(groups);
        userDao.persist(user);
        
        removeExpiredUserByUsername(username);
    }
    
    @Override
    public UserInfo getCurrentUserInfo()throws AuthenticationException{
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException("Can't change user info as no Authentication object found in context for current user.");
        }

        final String username = currentUser.getName();
        
        User user = userDao.get(username);
        return user == null ? null : user.getUserInfo();        
    }
    
    @Override
    public void updateUserInfo(final UserInfo userInfo)throws AuthenticationException{
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException("Can't change user info as no Authentication object found in context for current user.");
        }

        final String username = currentUser.getName();
        
        User user = userDao.get(username);
        if(user == null){
            if(logger.isDebugEnabled()){
                logger.debug("{} user is null",username);
            }
            return ;
        }
        
        user.getUserInfo().setBirthday(userInfo.getBirthday());
        user.getUserInfo().setEmail(userInfo.getEmail());
        user.getUserInfo().setIdentification(userInfo.getIdentification());
        user.getUserInfo().setMphone(userInfo.getMphone());
        user.getUserInfo().setName(userInfo.getName());
        user.getUserInfo().setPhone(userInfo.getPhone());
        
        userDao.persist(user);
    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            throw new AccessDeniedException("Can't change password as no Authentication object found in context for current user.");
        }

        final String username = currentUser.getName();

        if (this.authenticationManager != null) {
            if(logger.isDebugEnabled()){
                logger.debug("Reauthenticating user '{}' for password change request.",username);    
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("No authentication manage set.Password won't be re-checked");    
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug("Changing password for user {}'", username);
        }
        UserDetails userDetails = loadUserByUsername(username);
        String encoder = passwordEncoder(userDetails, newPassword);
        userDao.updatePassword(username, encoder);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(userDetails, encoder));

        userCache.removeUserFromCache(username);
    }

    protected String passwordEncoder(UserDetails userDetails, String password) {
        Object salt = (saltSource == null ? null : saltSource.getSalt(userDetails));
        return passwordEncoder.encodePassword(password, salt);
    }

    protected Authentication createNewAuthentication(UserDetails userDetails, String newPassword) {
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        newAuthentication.setDetails(userDetails);
        return newAuthentication;
    }

    @Override
    public void initPassword(final String username,final String password) throws AuthenticationException {
        UserDetails userDetails = this.loadUserByUsername(username);
        String encoder = passwordEncoder(userDetails, password);
        userDao.updatePassword(username, encoder);
        userCache.removeUserFromCache(username);
    }

    @Override
    public boolean hasUsername(String username) {
        User user = userDao.get(username);
        return user != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        UserDetails userDetails = userCache.getUserFromCache(username);
        if (userDetails != null) {
            if(logger.isDebugEnabled()){
                logger.debug("userDetails has cache");
            }
            return userDetails;
        }
        if(logger.isDebugEnabled()){
            logger.debug("userDetails has not cache");
        }

        User user = userDao.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("not fonund " + username);
        }

        userDetails = createUserDetails(user);
        userCache.putUserInCache(userDetails);
        return userDetails;
    }
    
    /**
     * 通过PO User来创建Spring Security UserDetails
     * 
     * @param user
     * @return UserDetails
     */
    private UserDetails createUserDetails(final User user){
        Assert.notNull(user,"user is null");
        
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        Set<Authority> auths = user.getAuthorities();
        if (auths != null) {
            if(logger.isDebugEnabled()){
                logger.debug("loadding user authorities");
            }
            for (Authority auth : auths) {
                authorities.add(new GrantedAuthorityImpl(auth.getName()));
            }
        }

        Set<Group> groups = user.getGroups();
        if(groups != null){
            for (Group group : groups) {
                if(logger.isDebugEnabled()){
                    logger.debug("lodding {} of group authorities",group.getName());
                }
                authorities.addAll(groupAuthorities(group));
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(),
                noExpired(user.getAccountStart(),user.getAccountEnd()),
                true, true, authorities);
    }

    /**
     * 判断输入的日期没有过去
     * 
     * 如果start=null并且end=null,则用户永远不过期。
     * 
     * @param start 开始日期
     * @param end   结束日期
     * @return 如 true 不过期 false 过期
     */
    protected boolean noExpired(final Date start,final Date end) {
        
        if(start == null && end == null){
            return true;
        }
        
        boolean nonExpired = true;
        long now = System.currentTimeMillis();
        if (start != null) {
            nonExpired = nonExpired && start.getTime() <= now;
        }
        if (end != null) {
            nonExpired = nonExpired && end.getTime() >= now;
        }

        return nonExpired;
    }

    /**
     * 得到用户组通用权限
     * 
     * 用户组本身也属于通用权限一部分，可以用户组可以实现ACL控制。
     * 
     * @param group 用户组对象
     * @return 用户组通用权限组
     */
    protected Set<GrantedAuthority> groupAuthorities(final Group group) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        
        Set<Authority> auths = group.getAuthorities();
        if (auths != null) {
            for (Authority auth : auths) {
                authorities.add(new GrantedAuthorityImpl(auth.getName()));
            }
        }
        authorities.add(new GrantedAuthorityImpl(group.getName()));
        
        return authorities;
    }
    
    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
    
    @Override
    public String getDefaultPassword(){
        return this.defaultPassword;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }
}
