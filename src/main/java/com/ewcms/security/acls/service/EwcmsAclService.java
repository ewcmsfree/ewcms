/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.ewcms.security.acls.domain.EwcmsPermission;

public class EwcmsAclService extends JdbcMutableAclService implements EwcmsAclServiceable {
    
    private static final Logger logger = LoggerFactory.getLogger(EwcmsAclService.class);
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String GROUP_PREFIX = "GROUP_";
    
    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();
    private Sid adminSid = new GrantedAuthoritySid("ROLE_ADMIN");
    private String rolePrefix = ROLE_PREFIX;
    private String groupPerfix = GROUP_PREFIX;
    
    public EwcmsAclService(DataSource dataSource,LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
    }

    @Override
    public Set<Permission> getPermissions(final Object object) {
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        return getPermissions(objectIdentity);
    }

    @Override
    public Set<Permission> getPermissions(final ObjectIdentity objectIdentity) {
        Assert.notNull(objectIdentity, "objectIdentity is null");
        
        List<Sid> sids = sidRetrievalStrategy.getSids(
                SecurityContextHolder.getContext().getAuthentication());
        Set<Permission> permissions = new HashSet<Permission>();
        if (isRoleAdmin(sids)) {
            permissions.add(EwcmsPermission.ADMIN);
        }
        try{
            MutableAcl acl = (MutableAcl) readAclById(objectIdentity);
            getPermissions(permissions, acl, sids);
        }catch(NotFoundException e){
            if(logger.isDebugEnabled()){
                logger.debug("Not found acl by {}",objectIdentity.toString());
            }
        }   
        
        return permissions;
    }
    
    private boolean isRoleAdmin(final List<Sid> sids) {
        for (Sid sid : sids) {
            if (sid.equals(adminSid)) {
                return true;
            }
        }
        return false;
    }

    private void getPermissions(final Set<Permission> permissions,final Acl acl, final List<Sid> sids) {
        for (Sid sid : sids) {
            for (AccessControlEntry ace : acl.getEntries()) {
                if (ace.getSid().equals(sid)) {
                    permissions.add(ace.getPermission());
                    break;
                }
            }
        }
        if (acl.getParentAcl() != null) {
            getPermissions(permissions, acl.getParentAcl(), sids);
        }
    }
    
    @Override
    public List<AccessControlEntry> findAces(final Object object) {
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        return findAces(objectIdentity);
    }
    
    @Override
    public List<AccessControlEntry> findAces(final ObjectIdentity objectIdentity) {
        try{
            final Acl acl = readAclById(objectIdentity);
            return acl.getEntries();
        }catch(NotFoundException e){
            if(logger.isDebugEnabled()){
                logger.debug("Not found acl by {}",objectIdentity.toString());
            }
            return new ArrayList<AccessControlEntry>();
        }
    }
    
    @Override
    public void updatePermissions(final Object object,final Map<Sid,Permission> sidPermissions,final Object parent){
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        final ObjectIdentity parentIdentity = (parent == null? null :  new ObjectIdentityImpl(parent));
        updatePermissions(objectIdentity,sidPermissions,parentIdentity);
    }
    
    @Override
    public void updatePermissions(final ObjectIdentity objectIdentity,final Map<Sid,Permission> sidPermissions,ObjectIdentity parentIdentity){
        
        MutableAcl acl = null;
        try{
            acl = (MutableAcl)readAclById(objectIdentity);
        }catch(NotFoundException e){
            if(logger.isDebugEnabled()){
                logger.debug("Not found acl by {}",objectIdentity.toString());
            }
            acl = createAcl(objectIdentity);
        }
        
        //empty history entries
        if(acl.getEntries() != null && !acl.getEntries().isEmpty()){
            for(int i = 0 ; i < acl.getEntries().size(); i++){
                acl.deleteAce(i);
            }
        }
       
        //inert new entries
        for(Iterator<Sid> iterator = sidPermissions.keySet().iterator(); iterator.hasNext() ;){
            Sid sid = iterator.next();
            Permission permission = sidPermissions.get(sid);
            boolean granting =(sid instanceof PrincipalSid);
            acl.insertAce(acl.getEntries().size(), permission, sid, granting);
        }
        
        //set inherit
        if(parentIdentity != null){
            try{
                final Acl parentAcl = (MutableAcl)readAclById(parentIdentity);
                acl.setParent(parentAcl);
                acl.setEntriesInheriting(Boolean.TRUE);
            }catch(NotFoundException e){
                if(logger.isDebugEnabled()){
                    logger.debug("Not found acl of parent by {}",objectIdentity.toString());
                }
            }
        }else{
            acl.setEntriesInheriting(Boolean.FALSE);
        }
        
        updateAcl(acl);
    }

    @Override
    public void updatePermissionsBySidNamePermissionMask(Object object,Map<String, Integer> sidNamePermissionMasks, Object parent) {
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        final ObjectIdentity parentIdentity = (parent == null? null :  new ObjectIdentityImpl(parent));
        updatePermissionsBySidNamePermissionMask(objectIdentity,sidNamePermissionMasks,parentIdentity);
    }

    @Override
    public void updatePermissionsBySidNamePermissionMask(ObjectIdentity objectIdentity,Map<String, Integer> sidNamePermissionMasks,ObjectIdentity parentIdentity) {
        Map<Sid,Permission> sidPermissions = new LinkedHashMap<Sid,Permission>();
        for(Iterator<String> iterator = sidNamePermissionMasks.keySet().iterator(); iterator.hasNext() ;){
            String name = iterator.next();
            Sid sid ;
            if(isGrant(name)){
                sid = new PrincipalSid(name);
                
            }else{
                sid= new GrantedAuthoritySid(name);
            }
            int mask = sidNamePermissionMasks.get(name);
            Permission permission = EwcmsPermission.maskOf(mask);
            sidPermissions.put(sid, permission);
        }
        updatePermissions(objectIdentity,sidPermissions,parentIdentity);
    }
    
    protected boolean isGrant(final String name){
        return StringUtils.startsWith(name, rolePrefix) || StringUtils.startsWith(name, groupPerfix);
    }
    
    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy){
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }
    
    public void setAdminSid(String sid){
        adminSid = new GrantedAuthoritySid(sid);
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public String getGroupPerfix() {
        return groupPerfix;
    }

    public void setGroupPerfix(String groupPerfix) {
        this.groupPerfix = groupPerfix;
    }
}
