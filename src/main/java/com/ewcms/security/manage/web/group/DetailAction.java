/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户组明细Action
 * 
 * @author wangwei
 */
@Controller("security.group.detail.action")
public class DetailAction extends ActionSupport{
    
    private static final Logger logger = LoggerFactory.getLogger(DetailAction.class);
    private static final String Authority_Group_Title = "权限";
    private static final String User_Group_Title = "用户";
    
    private String name;
    private Boolean showTitle = Boolean.TRUE;
    
    @Autowired
    private SecurityFacable fac;
    
    @Override
    public String execute(){
        return SUCCESS;
    }
    
    public void query(){
        Group group = fac.getGroup(name);
        
        if(group == null){
            logger.warn("Group name is {} but get is null",name);
            DataGrid datagrid= new DataGrid(0,Collections.EMPTY_LIST);
            Struts2Util.renderJson(JSONUtil.toJSON(datagrid));
            return ;
        }
        
        List<PropertyGridItem> items = new ArrayList<PropertyGridItem>();
        items.addAll(getItems(group.getAuthorities(),new ConvertToPropertyGridItem (){
            @Override
            public PropertyGridItem convert(Object value) {
                Authority authority = (Authority)value;
                return new PropertyGridItem(authority.getName(),authority.getRemark(),Authority_Group_Title);
            }
        }));
        items.addAll(getItems(group.getUsers(),new ConvertToPropertyGridItem (){
            @Override
            public PropertyGridItem convert(Object value) {
                User user = (User)value;
                return new PropertyGridItem(user.getUsername(),user.getUserInfo().getName(),User_Group_Title);
            }
        }));
            
        DataGrid datagrid = new DataGrid(items.size(),items);
        String json = JSONUtil.toJSON(datagrid);
        Struts2Util.renderJson(json);
    }
    
    private List<PropertyGridItem> getItems(Collection<?> values,ConvertToPropertyGridItem convert){
        List<PropertyGridItem> items = new ArrayList<PropertyGridItem>();
        for(Object value : values){
            items.add(convert.convert(value));
        }
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    /**
     * 转换对象成PropertyGridItem
     */
    private interface ConvertToPropertyGridItem{
        
        PropertyGridItem convert(Object value);
    }
}
