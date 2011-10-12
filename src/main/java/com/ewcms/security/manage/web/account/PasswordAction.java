/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.account;

import com.opensymphony.xwork2.ActionSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.service.UserServiceable;

/**
 * 用户修改密码Action
 * 
 * @author wangwei
 */
@Controller("security.account.password.action")
public class PasswordAction extends ActionSupport{

    private String oldPassword;
    private String password;
    private String againPassword;
    
    @Autowired
    private UserServiceable userService;
    
    @Override
    public String input(){
        oldPassword = null;
        password = null;
        againPassword = null;
        return INPUT;
    }
    
    @Override
    public String execute(){
        if(!againPassword.equals(password)){
            addActionError("密码输入不一致");
            return ERROR;
        }
        try{
            userService.changePassword(oldPassword, password);
            addActionMessage("修改密码成功");
            return SUCCESS;
        }catch(AuthenticationException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }

    public void setUserService(UserServiceable userService) {
        this.userService = userService;
    }
}
