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

@Controller("security.account.password")
public class PasswordAction extends ActionSupport{

    private String password;
    private String newPassword;
    private String againPassword;
    private boolean closeWindow = false;
    
    @Autowired
    private UserServiceable userService;
    
    @Override
    public String input(){
        password = null;
        newPassword = null;
        againPassword = null;
        closeWindow=false;
        return INPUT;
    }
    
    @Override
    public String execute(){
        if(!againPassword.equals(newPassword)){
            this.addFieldError("passwordAgain", "密码不一致");
            return ERROR;
        }
        try{
            userService.changePassword(password, newPassword);
            closeWindow =true;
            return SUCCESS;
        }catch(AuthenticationException e){
            addActionError(e.toString());
            return ERROR;
        }
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String oldPassword) {
        this.password = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }
    
    public boolean isCloseWindow() {
        return closeWindow;
    }

    public void setUserService(UserServiceable userService) {
        this.userService = userService;
    }
}
