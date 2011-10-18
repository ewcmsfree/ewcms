/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.group;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.service.GroupServiceable;
import com.ewcms.security.manage.service.UserServiceException;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 添加、删除用户组Action
 * 
 * @author wangwei
 */
@Controller("security.group.group.action")
public class GroupAction extends ActionSupport{

    private static final String ADD_OPERATION = "add";
    private static final String UPDATE_OPERATION = "update";
    private static final String RETURN_DEFAULT_JSON_FORMAT= "{\"success\":%b,\"message\":\"%s\"}";
    
    private String name;
    private String remark;
    private String eventOP = ADD_OPERATION;
    
    @Autowired
    private GroupServiceable groupService;
    
    @Override
    public String input(){

        if(StringUtils.isEmpty(name)){
            eventOP = ADD_OPERATION;
            return INPUT;
        }
        
        eventOP = UPDATE_OPERATION;
        Group group = groupService.getGroup(name);
        if(group == null){
            addActionError("'" + name + "'" +"用户不存在");
        }else{
            remark = group.getRemark();                
         }
        return INPUT;
    }
    
    /**
     * 修改操作
     * 
     * @return true update operator
     */
    private boolean isUpdate(){
        return eventOP.equals(UPDATE_OPERATION);
    }
    
    @Override
    public String execute(){
        try{
            if(isUpdate()){
                groupService.updateGroup(name, remark);
            }else{
                groupService.addGroup(name, remark);
                eventOP = UPDATE_OPERATION;   
            }
            return SUCCESS;
        }catch(UserServiceException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
    
    public void delete(){
        String format = RETURN_DEFAULT_JSON_FORMAT;
        groupService.removeGroup(name);
        Struts2Util.renderJson(String.format(format, true,""));
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

    public String getEventOP() {
        return eventOP;
    }

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }
   
    public void setGroupService(GroupServiceable service) {
        this.groupService = service;
    }
}
