/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

/**
 * 实现对象控制访问
 * 
 * @author wangwei
 */
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
            logger.debug("Not found acl by {}",objectIdentity.toString());
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
            logger.debug("Not found acl by {}",objectIdentity.toString());
            return new ArrayList<AccessControlEntry>();
        }
    }
    
    /**
     * 得到MutableAcl对象
     * 
     * 如果MutableAcl对象不存在,就创建它。
     * 
     * @param objectIdentity 被设置访问控制的象标识
     * @return
     */
    private MutableAcl getMutableAcl(ObjectIdentity objectIdentity){
        try{
            return (MutableAcl)readAclById(objectIdentity);
        }catch(NotFoundException e){
            logger.debug("Not found acl by {}",objectIdentity.toString());
            return createAcl(objectIdentity);
        }
    }
    
    @Override
    public void updateInheriting(Object object,Object parent) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        ObjectIdentity parentIdentity = (parent == null? null :  new ObjectIdentityImpl(parent));
        
        MutableAcl acl = getMutableAcl(objectIdentity);
        if(parentIdentity == null){
            acl.setEntriesInheriting(Boolean.FALSE);
            acl.setParent(null);
        }else{
	        Acl parentAcl = getMutableAcl(parentIdentity);
	        acl.setParent(parentAcl);
	        acl.setEntriesInheriting(Boolean.TRUE);
        }
        updateAcl(acl);
    }
    
    private Sid getSid(String name){
        return isGrant(name) ? new GrantedAuthoritySid(name) : new PrincipalSid(name);
    }
    
    @Override
    public void addPermission(Object object, String name, Integer mask) {
        Permission permission = EwcmsPermission.maskOf(mask);
        addPermission(object, getSid(name), permission);
    }

    @Override
    public void addPermission(Object object, Sid sid, Permission permission) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        
        MutableAcl acl = getMutableAcl(objectIdentity);
        acl.insertAce(acl.getEntries().size(), permission, sid, Boolean.TRUE);
        updateAcl(acl);
    }
    
    @Override
    public void removePermission(Object object, String name) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(object);
        MutableAcl acl = (MutableAcl)readAclById(objectIdentity);
        
        if(acl.getEntries() == null || acl.getEntries().isEmpty()){
            return ;
        }
        
        for(int i = 0 ; i < acl.getEntries().size(); i++){
            AccessControlEntry entry = acl.getEntries().get(i);
            if(entry.getSid().equals(getSid(name))){
                acl.deleteAce(i);
                updateAcl(acl);
                break;
            }
        }
    }
    
    @Override
    public void addOrUpdatePermission(Object object, String name, Integer mask) {
        removePermission(object,name);
        addPermission(object,name,mask);
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
