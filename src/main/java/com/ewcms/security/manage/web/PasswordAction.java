package com.ewcms.security.manage.web;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import com.ewcms.security.manage.service.UserServiceable;

@Controller("security.user.password")
public class PasswordAction extends ActionSupport{

    private List<String> selections;
    private String password;
    private String passwordAgain;
    private String username;
    private boolean success = true;
    
    @Autowired
    private UserServiceable userService;
    
    public String input(){
        
        if(selections.isEmpty()){
            this.addActionError("请选择修改密码的用户");
            return ERROR;
        }
        
        username = selections.get(0);
        password = "";
        passwordAgain = "";
        
        return INPUT;
    }
    
    public String execute(){
        if(!password.equals(passwordAgain)){
            this.addActionError("密码不一致");
            success = false;
            return ERROR;
        }
        try{
            userService.initPassword(username, password);
            selections.remove(0);
            if(!selections.isEmpty()){
                return input();
            }
            return SUCCESS;
        }catch(AuthenticationException e){
            this.addActionError(e.toString());
            success = false;
            return ERROR;
        }
    }
    
    public List<String> getSelections() {
        return selections;
    }

    public void setSelections(List<String> selections) {
        this.selections = selections;
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

    public boolean isSuccess() {
        return success;
    }
}
