/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.UserInfo;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 修改用户信息Action
 * 
 * @author wangwei
 */
@Controller("security.account.user.action")
public class UserAction extends ActionSupport{

    private UserInfo userInfo;
    
    @Autowired
    private SecurityFacable fac;
    
    @Override
    public String input(){
        userInfo = fac.getCurrentUserInfo();
        return INPUT;
    }
    
    @Override
    public String execute(){
        try{
            fac.updateUserInfo(userInfo);
            this.addActionMessage("修改用户信息成功");
            return SUCCESS;
        }catch(Exception e){
            addActionError("修改用户信息失败");
            return ERROR;
        }
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setFac(SecurityFacable fac) {
        this.fac = fac;
    }
}
