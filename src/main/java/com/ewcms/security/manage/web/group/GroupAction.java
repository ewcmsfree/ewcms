/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.group;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.service.UserServiceException;
import com.ewcms.web.JsonBaseAction;
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
    
    private String name;
    private String fullname;
    private String remark;
    private List<String> newGroupNames;
    private String eventOP = ADD_OPERATION;
    
    @Autowired
    private SecurityFacable fac;
    
    @Override
    public String input(){

        if(StringUtils.isEmpty(name)){
            eventOP = ADD_OPERATION;
            return INPUT;
        }
        
        eventOP = UPDATE_OPERATION;
        fullname = name;
        Group group = fac.getGroup(name);
        if(group == null){
            addActionError(name +"用户组不存在");
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
    private boolean isUpdateOperator(){
        return eventOP.equals(UPDATE_OPERATION);
    }
    
    @Override
    public String execute(){
        try{
            if(isUpdateOperator()){
                fullname = name;
                fac.updateGroup(name, remark);
                addActionMessage("用户组修改成功");
            }else{
                if(fac.isGroupnameExist(name)){
                    addActionError("用户组已经存在");
                }else{
                    fullname = fac.addGroup(name, remark);
                    if(newGroupNames == null){
                        newGroupNames = new ArrayList<String>();
                       }
                    newGroupNames.add(fullname);
                    addActionMessage("添加用户组成功，可以添加权限和用户");
                }
            }
            return SUCCESS;
        }catch(UserServiceException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
    
    public void delete(){
        JsonBaseAction json = new JsonBaseAction();
        fac.removeGroup(name);
        json.renderSuccess();
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

    public boolean isShowAuthUserTab(){
        return fullname != null;
    }
    
    public boolean isAddSaveState(){
        return eventOP.equals("add") && fullname != null;
    }
    
    public String getFullname(){
        return this.fullname;
    }
    
    public String getEventOP() {
        return eventOP;
    }

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }
    
    public List<String> getNewGroupNames() {
        return newGroupNames;
    }

    public void setNewGroupNames(List<String> newGroupNames) {
        this.newGroupNames = newGroupNames;
    }

    public void setFac(SecurityFacable fac) {
        this.fac = fac;
    }
}
