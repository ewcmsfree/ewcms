/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Channel;
import com.ewcms.security.manage.SecurityFacable;
import com.ewcms.web.JsonBaseAction;
import com.ewcms.web.vo.DataGrid;
import com.opensymphony.xwork2.Action;

/**
 * 专栏acl管理Action
 * 
 * @author wangwei
 */
@Controller("core.site.channel.acl.action")
public class AclAction extends JsonBaseAction{

    private static final Map<String,Object> EDITOR = createEditor();
    
    private Integer id;
    private String name;
    private Integer mask;
    private String type;
    private Boolean inherit;

    @Autowired
	private SiteFac siteFac;
    
    @Autowired
    private SecurityFacable securityFac;
    
    public String input(){
        type = "user";
        inherit = Boolean.TRUE;
        return Action.INPUT;
    }
    
    private Map<String,Object> permissionItem(String name,Integer mask){
        Map<String,Object> map = new HashMap<String,Object>();
        
        map.put("name", name);
        map.put("value", mask);
        map.put("group", getGroup(name));
        map.put("editor", EDITOR);
        
        return map;
    }
    
    private boolean isAuthority(String name){
        return StringUtils.startsWith(name, "ROLE_");
    }
    
    private boolean isGroup(String name){
        return StringUtils.startsWith(name, "GROUP_");
    }
    
    private String getGroup(String name){
        if(isAuthority(name)){
            return "通用权限";
        }else if (isGroup(name)){
            return "用户组";            
        }else{
            return "用户";
        }
    }
    
    private List<Map<String,Object>> permissionItems(List<AccessControlEntry> aces,boolean inheriting){
        
        List<Map<String,Object>> userItems =new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> groupItems =new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> authorityItems =new ArrayList<Map<String,Object>>();
        for(AccessControlEntry ace : aces){
            Sid sid = ace.getSid();
            String n = (sid instanceof PrincipalSid) ?
                    ((PrincipalSid)sid).getPrincipal() 
                    :((GrantedAuthoritySid)sid).getGrantedAuthority();
            int m = ace.getPermission().getMask();
            if(isAuthority(n)){
                authorityItems.add(permissionItem(n,m));
            }else if(isGroup(n)){
                groupItems.add(permissionItem(n,m));
            }else{
                userItems.add(permissionItem(n,m));
            }
        }
        
        List<Map<String,Object>> items =new ArrayList<Map<String,Object>>();
        items.addAll(authorityItems);
        items.addAll(groupItems);
        items.addAll(userItems);
        items.add(inheritItem(inheriting));
        
        return items;
    }
    
    private Map<String,Object> inheritItem(Boolean inherit){
        Map<String,Object> map = new HashMap<String,Object>();
        
        map.put("name", "继承权限");
        map.put("value", inherit.toString());
        map.put("group", "其他");
        Map<String,Object> editor = new HashMap<String,Object>();
        map.put("editor", editor);
        
        editor.put("type", "checkbox");
        Map<String,Object> checkboxData = new HashMap<String,Object>();
        editor.put("options",checkboxData);
        checkboxData.put("on", Boolean.TRUE);
        checkboxData.put("off", Boolean.FALSE);
        
        return map;
    }
    
    public void query(){
        Channel channel = siteFac.getChannel(id);
        Acl acl = siteFac.findAclOfChannel(channel);  
        if(acl == null || acl.getEntries() == null){
            renderObject(new DataGrid(0,Collections.EMPTY_LIST));
            return ;
        }
             
        List<Map<String,Object>> items = permissionItems(
                acl.getEntries(),acl.isEntriesInheriting());
        
        renderObject(new DataGrid(items.size(),items));
    }
    
    private boolean hasName(){
        
        if(type == null){
            return true;
        }
        
        if(type.equals("user") 
                && !securityFac.hasUsername(name)){
            renderError(String.format("\"%s\"用户不存在",name));
            return false;
        }else if(type.equals("group") 
                && !securityFac.hasGroupname(name)){
            renderError(String.format("\"%s\"用户组不存在",name));
            return false;
        }else if(type.equals("auth") 
                && !securityFac.hasAuthorityname(name)){
            renderError(String.format("\"%s\"通用权限不存在",name));
            return false;    
        }
        return true;
    }
    
    public void save(){
        if(!hasName()){
            return ;
        }
        
        try{
            siteFac.addOrUpdatePermission(id, name, mask);
            renderSuccess();    
        }catch(Exception e){
            renderError();
        }
    }
    
    public void remove(){
        try{
            siteFac.removePermission(id, name);
            renderSuccess();
        }catch(Exception e){
            renderError();
        }
    }
    
    public void updateInherit(){
        try{
            siteFac.updateInheriting(id, inherit);
            renderSuccess();    
        }catch(Exception e){
            renderError();
        }
    }
    
    public Boolean getInherit() {
        return inherit;
    }

    public void setInherit(Boolean inherit) {
        this.inherit = inherit;
    }
    
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setSiteFac(SiteFac siteFac) {
        this.siteFac = siteFac;
    }

    public void setSecurityFac(SecurityFacable securityFac) {
        this.securityFac = securityFac;
    }

    private static Map<String,Object> createEditor(){
        
        Map<String,Object> editor = new HashMap<String,Object>();
        editor.put("type", "combobox");
        Map<String,Object> options =new HashMap<String,Object>();
        editor.put("options",options);
        List<Map<String,Object>> comboboxData = new ArrayList<Map<String,Object>>();
        options.put("data", comboboxData);
        Integer[] values = new Integer[]{1,2,4,64};
        String[] texts = new String[]{"读","写","发布","管理"};
        for(int i = 0 ; i < values.length ; i++){
            Map<String,Object> data = new HashMap<String,Object>();    
            data.put("value", values[i]);
            data.put("text", texts[i]);
            comboboxData.add(data);
        }
        
        return editor;
    }
}
