/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.service.UserServiceable;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 初始用户密码
 * 
 * @author wangwei
 */
@Controller("security.user.initpassword")
public class InitPasswordAction extends ActionSupport{

	private static final long serialVersionUID = 6586468960229031252L;

	private String password;
    private String passwordAgain;
    private String username;
    
    @Autowired
    private UserServiceable userService;
    
    public String input(){
        
        password = "";
        passwordAgain = "";
        
        return INPUT;
    }
    
    public String execute(){
        
        if(!password.equals(passwordAgain)){
            this.addActionError("密码不一致");
            return ERROR;
        }
        
        try{
            userService.initPassword(username, password);
            addActionMessage("修改密码成功");
            return SUCCESS;
        }catch(AuthenticationException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
}
