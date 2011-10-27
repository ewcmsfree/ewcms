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
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Channel;
import com.ewcms.web.JsonBaseAction;
import com.ewcms.web.vo.DataGrid;
import com.opensymphony.xwork2.Action;

/**
 * 频道acl管理
 * 
 * @author wangwei
 */
@Controller("core.site.channel.acl.action")
public class AclAction extends JsonBaseAction{

    private static final Map<String,Object> EDITOR = createEditor();
    
    private Integer id;
    private String name;
    private Integer mask;
    private Boolean inherit = Boolean.TRUE;

    @Autowired
	private SiteFac siteFac;
        
    
    public String input(){
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
     
        if(acl == null){
            renderObject(new DataGrid(0,Collections.EMPTY_LIST));
        }
             
        List<AccessControlEntry> aces = acl.getEntries();
        if(aces == null || aces.isEmpty()){
            renderObject(new DataGrid(0,Collections.EMPTY_LIST));
        }
        
        List<Map<String,Object>> items =new ArrayList<Map<String,Object>>();
        for(AccessControlEntry ace : aces){
            Sid sid = ace.getSid();
            Permission permission = ace.getPermission();
            Map<String,Object> item;
            if(sid instanceof PrincipalSid){
                PrincipalSid psid = (PrincipalSid)sid;
                item = gridItem(psid.getPrincipal(),permission.getMask());
            }else{
                GrantedAuthoritySid gsid = (GrantedAuthoritySid)sid;
                item = gridItem(gsid.getGrantedAuthority(),permission.getMask());
             }
            items.add(item);
        }
        renderObject(new DataGrid(items.size(),items));
    }
    
    public void add(){
        
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
