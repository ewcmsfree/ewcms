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
 * 专栏acl管理
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
    
    private Map<String,Object> gridItem(String name,Integer mask){
        Map<String,Object> map = new HashMap<String,Object>();
        
        map.put("name", name);
        map.put("value", mask);
        map.put("group", getGroup(name));
        map.put("editor", EDITOR);
        
        return map;
    }
    
    private String getGroup(String name){
        if(StringUtils.startsWith(name, "GROUP_")){
            return "用户组";
        }else if (StringUtils.startsWith(name, "ROLE_")){
            return "通用权限";
        }else{
            return "用户";
        }
    }
    
    public void query(){
        Channel channel = siteFac.getChannel(id);
        Acl acl = siteFac.findAclOfChannel(channel);  
     
        if(acl == null || acl.getEntries() == null){
            renderObject(new DataGrid(0,Collections.EMPTY_LIST));
        }
             
        List<Map<String,Object>> items =new ArrayList<Map<String,Object>>();
        for(AccessControlEntry ace : acl.getEntries()){
            Sid sid = ace.getSid();
            String n = (sid instanceof PrincipalSid) ?
                    ((PrincipalSid)sid).getPrincipal() 
                    :((GrantedAuthoritySid)sid).getGrantedAuthority();
            int m = ace.getPermission().getMask();
            items.add(gridItem(n,m));
        }
        
        renderObject(new DataGrid(items.size(),items));
    }
    
    private boolean hasName(){
        if(type.equals("user") && !securityFac.hasUsername(name)){
            renderError(String.format("\"%s\"用户不存在",name));
            return false;
        }else if(type.equals("group") && !securityFac.hasGroupname(name)){
            renderError(String.format("\"%s\"用户组不存在",name));
            return false;
        }else if(!securityFac.hasAuthorityname(name)){
            renderError(String.format("\"%s\"通用权限不存在",name));
            return false;
        }
        return true;
    }
    
    public void save(){
        if(!hasName()){
            return ;
        }
        
        siteFac.addOrUpdatePermission(id, name, mask);
        renderSuccess();
    }
    
    public void remove(){
        siteFac.removePermission(id, name);
        renderSuccess();
    }
    
    public void updateInherit(){
        siteFac.updateInheriting(id, inherit);
        renderSuccess();
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
