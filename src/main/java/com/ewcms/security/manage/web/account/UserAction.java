/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.model.UserInfo;
import com.ewcms.security.manage.service.UserServiceable;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 修改用户信息Action
 * 
 * @author wangwei
 */
@Controller("security.account.user.action")
public class UserAction extends ActionSupport{

    private boolean closeWindow = false;
    private UserInfo userInfo;
    
    @Autowired
    private UserServiceable userService;
    
    @Override
    public String input(){
        userInfo = userService.getCurrentUserInfo();
        closeWindow = false;
        return INPUT;
    }
    
    @Override
    public String execute(){
        try{
            userService.updateUserInfo(userInfo);
            closeWindow = true;
            return SUCCESS;
        }catch(Exception e){
            this.addActionError("修改用户信息错误");
            return ERROR;
        }
    }
    
    public boolean isCloseWindow() {
        return closeWindow;
    }
     
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setUserService(UserServiceable userService) {
        this.userService = userService;
    }   
}
