/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.Struts2Util;

@Controller
public class UserAction extends CrudBaseAction<User,String>{
    
    private static final String EVENTOP_ADD = "add";
    private static final String EVENTOP_UPDATE = "update";
    
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String GROUP_PREFIX = "GROUP_";
    
    private User user;
    private Set<Authority> authorities = new HashSet<Authority>();
    private Set<Group> groups = new HashSet<Group>();
    private String eventOP = "add";
    
    @Autowired
    private UserServiceable userService;
    
    public void usernameExist(){
        String format = "{\"exist\":%b}";
        boolean exist = false;
        if(!operatorPK.isEmpty()){
            exist = userService.usernameExist(operatorPK.get(0));
        }
        Struts2Util.renderJson(String.format(format, exist));
    }
    
    public void active(){
        String format = "{\"success\":%b,\"message\":\"%s\"}";
        try{
            if(!operatorPK.isEmpty()){
                for(String username:operatorPK){
                    userService.activeUser(username);
                }
            }
        }catch(AuthenticationException e){
            Struts2Util.renderJson(String.format(format, false,e.toString()));
        }
        Struts2Util.renderJson(String.format(format, true,""));
    }
    
    public void inactive(){
        String format = "{\"success\":%b,\"message\":\"%s\"}";
        try{
            if(!operatorPK.isEmpty()){
                for(String username:operatorPK){
                    userService.inactiveUser(username);
                }
            }
        }catch(AuthenticationException e){
            Struts2Util.renderJson(String.format(format, false,e.toString()));
        }
        Struts2Util.renderJson(String.format(format, true,""));
    }
    
    @Override
    public String input()throws Exception{
        if(operatorPK==null || operatorPK.isEmpty()){
            eventOP = EVENTOP_ADD;
        }else{
            eventOP = EVENTOP_UPDATE;
        }
        return super.input();
    }
    
    private boolean isUsernameValidate(final String username){
        if(StringUtils.startsWith(username, ROLE_PREFIX) || StringUtils.startsWith(username, GROUP_PREFIX)){
            this.addActionError("用户名不能使用\""+ROLE_PREFIX+"\"和\""+GROUP_PREFIX+"\"前缀");
            return false;
        }
        return true;
    }
    
    @Override
    public String save()throws Exception{
        if(vo.getAccountEnd()!=null && vo.getAccountStart() != null){
            if(vo.getAccountEnd().getTime()<vo.getAccountStart().getTime()){
                this.addActionError("授权开始时间大于授权结束日期");
                return ERROR;
            }
        }
        if(!isUsernameValidate(vo.getUsername())){
            return ERROR;
        }
        return super.save();
    }
    
    @Override
    public String execute()throws Exception{
        return save();
    }
    
    protected boolean isUpdateOperator(){
        return eventOP.equals(EVENTOP_UPDATE);
    }
    
    @Override
    protected User getOperator(String pk) {
        return userService.getUser(pk);
    }

    @Override
    protected void deleteOperator(String pk) {
        userService.deleteUser(pk);
    }

    private Set<String> getAuthNames(){
        Set<String> names = new HashSet<String>();
        for(Authority auth : authorities){
           names.add(auth.getName()); 
        }
        return names;
    }
    
    private Set<String> getGroupNams(){
        Set<String> names = new HashSet<String>();
        for(Group group : groups){
            names.add(group.getName());
        }
        return names;
    }
    @Override
    protected String saveOperator(User vo, boolean isUpdate) {
        if(isUpdate){
            userService.updateUser(user.getUsername(), vo.isEnabled(),
                    vo.getAccountStart(), vo.getAccountEnd(), vo.getUserInfo(),
                    getAuthNames(), getGroupNams());
        }else{
            userService.addUser(vo.getUsername(),vo.getPassword(), vo.isEnabled(),
                    vo.getAccountStart(), vo.getAccountEnd(), vo.getUserInfo(),
                    getAuthNames(), getGroupNams());
        }
        
        return vo.getUsername();
    }

    @Override
    protected String getPK(User vo) {
        return vo.getUsername();
    }
    
    @Override
    protected User constructVo(User vo) {
        if(vo.getAuthorities() != null){
            authorities = vo.getAuthorities();
        }
        if(vo.getGroups() != null){
            groups = vo.getGroups();
        }
        user = vo;
        return vo;
    }
    
    @Override
    protected User createEmptyVo() {
        return new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        super.setVo(user);
        this.user = user;
    }

    public List<String> getSelections() {
        return getOperatorPK();
    }

    public void setSelections(List<String> selections) {
        setOperatorPK(selections);
    }

    public String getEventOP() {
        return eventOP;
    }

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
    
    public Set<Group> getGroups() {
        return groups;
    }
    
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    
    public String getDefaultPassword(){
        return userService.getDefaultPassword();
    }

    public void setUserService(UserServiceable userService) {
        this.userService = userService;
    }
}
