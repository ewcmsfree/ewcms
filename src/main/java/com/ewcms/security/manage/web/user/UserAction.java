/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.service.UserServiceException;
import com.ewcms.web.JsonBaseAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户操作Action
 * 
 * @author wangwei
 */
@Controller("security.user.user.action")
public class UserAction extends ActionSupport{
    
	private static final long serialVersionUID = -286253039838980638L;

	private static final String EVENTOP_ADD = "add";
    private static final String EVENTOP_UPDATE = "update";
    
    private String username;
    private List<String> newUsernames;
    private User user;
    private boolean showAuthGroupTab = false;
    private String eventOP = EVENTOP_ADD;
    
    @Autowired
    private SecurityFacable fac;
    
    public void hasUsername(){
        String format = "{\"exist\":%b}";
        boolean  exist = fac.hasUsername(username);
        JsonBaseAction json = new JsonBaseAction();
        json.render(String.format(format, exist));
    }
    
    public void active(){
         JsonBaseAction json = new JsonBaseAction();
         try{
             fac.activeUser(username);
             json.renderSuccess();
         }catch(UserServiceException e){
            json.renderError(e.getMessage());
           }
    }
    
    public void inactive(){
        JsonBaseAction json = new JsonBaseAction();
        try{
            fac.inactiveUser(username);
            json.renderSuccess();
        }catch(UserServiceException e){
           json.renderError(e.getMessage());
          }
    }
    
    @Override
    public String input(){
        
        if(username == null){
            eventOP = EVENTOP_ADD;
            user = new User();
            user.setEnabled(Boolean.TRUE);
            return INPUT;
        }
        
        showAuthGroupTab = true; 
        eventOP = EVENTOP_UPDATE;
        user = fac.getUser(username);
        if(user == null){
            addActionError("修改的用户不存在");
            username = "";
            eventOP = EVENTOP_ADD;
        }

        return INPUT;
    }
    
    /**
     * 验证授权日期是否正确
     * 
     * 结束日期必须大于开始日期
     * 
     * @param start 开始日期
     * @param end   结束日期
     * @return true 验证正确
     */
    private boolean validateAccoutDate(Date start,Date end){
        if(end != null && start != null){
            if(end.getTime()<start.getTime()){
                this.addActionError("授权开始时间大于授权结束日期");
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是否是更新操作
     * 
     * @return
     */
    private boolean isUpdateOperator(){
        return EVENTOP_UPDATE.equals(eventOP);
    }
    
    @Override
    public String execute(){
        if(!validateAccoutDate(user.getAccountStart(), user.getAccountEnd())){
            return ERROR;
         }
        
        try{
            if(isUpdateOperator()){
                username = fac.updateUser(user.getUsername(),
                        user.isEnabled(), user.getAccountStart(), 
                        user.getAccountEnd(), user.getUserInfo());
                addActionMessage("用户修改成功");
            }else{
                if(fac.hasUsername(user.getUsername())){
                    addActionError("用户名称已经存在");
                    return ERROR;
                }
                username = fac.addUser(user.getUsername(),user.getPassword(),
                        user.isEnabled(), user.getAccountStart(), 
                        user.getAccountEnd(), user.getUserInfo());
                newUsernames = (newUsernames == null ? new ArrayList<String>() : newUsernames);
                newUsernames.add(username);
                addActionMessage("添加用户成功，可以添加权限和所属用户组");
            }
            showAuthGroupTab = true; 
            return SUCCESS;
        }catch(UserServiceException e){
            addActionError(e.getMessage());
            return ERROR;
        }
    }
      
    public void delete(){
        JsonBaseAction json = new JsonBaseAction();
        fac.removeUser(username);
        json.renderSuccess();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getEventOP() {
        return eventOP;
    }

    public void setEventOP(String eventOP) {
        this.eventOP = eventOP;
    }

    public List<String> getNewUsernames() {
        return newUsernames;
    }

    public void setNewUsernames(List<String> newUsernames) {
        this.newUsernames = newUsernames;
    }

    public boolean isShowAuthGroupTab(){
        return showAuthGroupTab; 
    }
    
    public boolean isAddSaveState(){
        return !isUpdateOperator() && showAuthGroupTab;
    }
    
    public void setFac(SecurityFacable fac) {
        this.fac = fac;
    }
}
