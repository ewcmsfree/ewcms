package com.ewcms.security.web.group;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.GroupServiceable;
import com.ewcms.security.manage.service.UserServiceException;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class GroupAction extends ActionSupport{

    private static final String ADD_OPERATION = "add";
    private static final String UPDATE_OPERATION = "update";
    private static final String RETURN_DEFAULT_JSON_FORMAT= "{\"success\":%b,\"message\":\"%s\"}";
    
    private String name;
    private String remark;
    private Set<User> users = new HashSet<User>();
    private Set<Authority> authorities = new HashSet<Authority>();
    private String eventOP = ADD_OPERATION;
    private boolean closeWindow = false;
    private String detailName;
    private Set<String> detailNames = new HashSet<String>();
    
    @Autowired
    private GroupServiceable groupService;
    
    @Override
    public String input(){
        if(StringUtils.isEmpty(name)){
            return INPUT;
        }
        eventOP = UPDATE_OPERATION;
        Group group = groupService.getGroup(name);
        if(group == null){
            this.addActionError("'" + name + "'" +"用户不存在");
            return INPUT;
        }
        remark = group.getRemark();
        users = group.getUsers();
        authorities = group.getAuthorities();
        return INPUT;
    }
    
    /**
     * 添加操作
     * 
     * @return true add operator
     */
    private boolean isAdd(){
        return eventOP.equals(ADD_OPERATION);
    }
    
    /**
     * 修改操作
     * 
     * @return true update operator
     */
    private boolean isUpdate(){
        return eventOP.equals(UPDATE_OPERATION);
    }

    /**
     * 得到用户组的权限名称
     * 
     * @return
     */
    private Set<String> getAuthNames(){
        Set<String> names = new HashSet<String>();
        if(authorities== null || authorities.isEmpty()){
            return names;
        }
        for(Authority auth : authorities){
           names.add(auth.getName()); 
        }
        return names;
    }
    
    /**
     * 得到属于用户做用户名
     * 
     * @return
     */
    private Set<String> getUsernames(){
        Set<String> names = new HashSet<String>();
        if(users == null || users.isEmpty()){
            return names;
        }
        for(User user :users){
            names.add(user.getUsername());
        }
        return names;
    }
    
    @Override
    public String execute(){
        Set<String> authNames = getAuthNames();
        Set<String> usernames = getUsernames();
        try{
            if(isAdd()){
                groupService.addGroup(name, remark, authNames, usernames);
                name = "";
                remark = "";
                users =  new HashSet<User>();
                authorities = new HashSet<Authority>();
            }
            if(isUpdate()){
                groupService.updateGroup(name, remark, authNames, usernames);
                closeWindow = true;
            }    
            return SUCCESS;
        }catch(UserServiceException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
    
    /**
     * 判断用户组名是否存在
     */
    public void groupnameExist(){
        String format = "{\"exist\":%b}";
        boolean exist = false;
        if(!StringUtils.isEmpty(name)){
            exist = groupService.isGroupnameExist(name);
        }
        Struts2Util.renderJson(String.format(format, exist));
    }
    
    private boolean vaildateGroupname(final String format){
        if(StringUtils.isBlank(name)){
            Struts2Util.renderJson(String.format(format, false,"用户组名为空"));
            return false;
        }
        return true;
    }
    
    private boolean vaildateDetailName(final String format,final String message){
        if(StringUtils.isBlank(detailName)){
            Struts2Util.renderJson(String.format(format, false,message));
            return false;
        }
        return true;
    }
    
    public void delete(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        if(!vaildateGroupname(format)){
            return ;
        }
        groupService.removeGroup(name);
        Struts2Util.renderJson(String.format(format, true,""));
    }
    
    public void deleteAuth(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        if(!vaildateGroupname(format) || !vaildateDetailName(format,"权限名为空")){
            return ;
        }
        groupService.removeAuthInGroup(name, detailName);
        Struts2Util.renderJson(String.format(format, true,""));
    }
    
    public void deleteUser(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        if(!vaildateGroupname(format) || !vaildateDetailName(format,"用户名为空")){
            return ;
        }
        groupService.removeUserInGroup(name, detailName);
        Struts2Util.renderJson(String.format(format, true,""));
    }
    
    public void addAuthorities(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        if(!vaildateGroupname(format)){
            return ;
        }
        
        format="{\"success\":%b,\"message\":\"%s\",\"nodes\":%s}";
        if(detailNames != null && !detailNames.isEmpty()){
            Set<Authority> newAuths = groupService.addAuthoritiesToGroup(name,  detailNames);
            Set<GroupGridNode> nodes = new HashSet<GroupGridNode>();
            for(Authority auth : newAuths){
                GroupGridNode node =new GroupGridNode.Builder().build(name, auth);
                nodes.add(node);
            }
            String dataJSON = JSONUtil.toJSON(nodes);
            Struts2Util.renderJson(String.format(format, true,"",dataJSON));  
        }else{
            Struts2Util.renderJson(String.format(format, true,"","[]"));
        }
    }
    
    public void addUsers(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        if(!vaildateGroupname(format)){
            return ;
        }
        format="{\"success\":%b,\"message\":\"%s\",\"nodes\":%s}";
        if(detailNames != null && !detailNames.isEmpty()){
            Set<User> newUsers = groupService.addUsersToGroup(name,detailNames);
            Set<GroupGridNode> nodes = new HashSet<GroupGridNode>();
            for(User user : newUsers){
                GroupGridNode node =new GroupGridNode.Builder().build(name, user);
                nodes.add(node);
            }
            String dataJSON = JSONUtil.toJSON(nodes);
            Struts2Util.renderJson(String.format(format, true,"",dataJSON));  
        }else{
            Struts2Util.renderJson(String.format(format, true,"","[]"));
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
   
    public String getEventOP() {
        return eventOP;
    }

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }
    
    public boolean isCloseWindow() {
        return closeWindow;
    }
    
    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public void setDetailNames(Set<String> detailNames) {
        this.detailNames = detailNames;
    }

    public void setGroupService(GroupServiceable service) {
        this.groupService = service;
    }
}
