/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ChannelAclAction extends ActionSupport{

    private Integer id;
    private Boolean inherit = Boolean.TRUE;
    private Map<String,Integer> sidPermissions;
    
	@Autowired
	private SiteFac siteFac;
        
    @Override
    public String input(){
        
        final Channel channel = siteFac.getChannel(id);
        final Acl acl = siteFac.findAclOfChannel(channel);  
        
        sidPermissions =new LinkedHashMap<String,Integer>();
        if(acl == null){
           return INPUT; 
        }
        
        final List<AccessControlEntry> aces = acl.getEntries();
        if(aces != null && !aces.isEmpty()){
            for(AccessControlEntry ace : aces){
                Sid sid = ace.getSid();
                Permission permission = ace.getPermission();
                if(sid instanceof PrincipalSid){
                    sidPermissions.put(((PrincipalSid)sid).getPrincipal(),permission.getMask());
                }else{
                    sidPermissions.put(((GrantedAuthoritySid)sid).getGrantedAuthority(),permission.getMask());
                }
            }
        }
        this.inherit = acl.isEntriesInheriting();
                
        return INPUT;
    }
    
    @Override
    public String execute(){
    	siteFac.updatePermissionOfChannel(id, sidPermissions, inherit);
        return SUCCESS;
    }
       
    public Map<String, Integer> getSidPermissions() {
        return sidPermissions;
    }

    public void setSidPermissions(Map<String, Integer> sidPermissions) {
        this.sidPermissions = sidPermissions;
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
}
