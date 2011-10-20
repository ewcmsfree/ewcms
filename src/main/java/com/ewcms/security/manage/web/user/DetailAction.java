/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceException;
import com.ewcms.web.JsonBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户组明细Action
 * 
 * @author wangwei
 */
@Controller("security.user.detail.action")
public class DetailAction extends ActionSupport{
    
    private static final Logger logger = LoggerFactory.getLogger(DetailAction.class);
    private static final String Authority_User_Title = "权限";
    private static final String Group_User_Title = "用户组";
    
    private String username;
    private Set<String> authNames;
    private Set<String> groupNames;
    private Boolean showTitle = Boolean.TRUE;
    
    @Autowired
    private SecurityFacable fac;
    
    @Override
    public String execute(){
        return SUCCESS;
    }
    
    /**
     * 转换对象成PropertyGridItem
     */
    private interface ConvertToPropertyGridItem{
        
        PropertyGridItem convert(Object value);
    }
    
    private List<PropertyGridItem> getItems(Collection<?> values,ConvertToPropertyGridItem convert){
        List<PropertyGridItem> items = new ArrayList<PropertyGridItem>();
        for(Object value : values){
            items.add(convert.convert(value));
        }
        return items;
    }
    
    /**
     * 查询当前用户的权限和所属用户组
     */
    public void query(){
        User user = fac.getUser(username);
        
        if(user == null){
            logger.warn("Username is {} but get is null",username);
            DataGrid datagrid= new DataGrid(0,Collections.EMPTY_LIST);
            Struts2Util.renderJson(JSONUtil.toJSON(datagrid));
            return ;
        }
        
        List<PropertyGridItem> items = new ArrayList<PropertyGridItem>();
        items.addAll(getItems(user.getAuthorities(),new ConvertToPropertyGridItem (){
            @Override
            public PropertyGridItem convert(Object value) {
                Authority authority = (Authority)value;
                return new PropertyGridItem(authority.getName(),authority.getRemark(),Authority_User_Title);
            }
        }));
        items.addAll(getItems(user.getGroups(),new ConvertToPropertyGridItem (){
            @Override
            public PropertyGridItem convert(Object value) {
                Group group = (Group)value;
                return new PropertyGridItem(group.getName(),group.getRemark(),Group_User_Title);
            }
        }));
         
        JsonBaseAction json = new JsonBaseAction();
        json.renderObject(new DataGrid(items.size(),items));
    }
    
    /**
     * 添加权限和用户
     */
    public void addAuthsAndGroups(){
        JsonBaseAction json = new JsonBaseAction();
        try{
            fac.addAuthsAndGroupsToUser(username, authNames, groupNames);
            json.renderSuccess();    
        }catch(UserServiceException e){
            json.renderError(e.getMessage());
        }
    }
    
    /**
     * 删除权限和用户
     */
    public void removeAuthsAndGroups(){
        JsonBaseAction json = new JsonBaseAction();
        try{
            fac.removeAuthsAndGroupsInUser(username, authNames, groupNames);
            json.renderSuccess();    
        }catch(UserServiceException e){
            json.renderError(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Set<String> getAuthNames() {
        return authNames;
    }

    public void setAuthNames(Set<String> authNames) {
        this.authNames = authNames;
    }

    public Set<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(Set<String> groupNames) {
        this.groupNames = groupNames;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    /**
     * jquery easyui propertygrid 显示行对象
     */
    public static class PropertyGridItem {

        private Object name;
        private Object value;
        private String group;
        
        public PropertyGridItem(Object name,Object value,String group){
            this.name = name;
            this.value = value;
            this.group = group;
        }
        
        public Object getName() {
            return name;
        }
        
        public Object getValue() {
            return value;
        }
        
        public String getGroup() {
            return group;
        }
    }
}
