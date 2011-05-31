/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface EwcmsAclServiceable extends MutableAclService{

    /**
     * 得到对象在当前用户的操作权限
     * 
     * @param object 对象
     * @return 返回权限集合
     */
    Set<Permission> getPermissions(Object object);
    
    /**
     * 通过对象标识得到当前用户的操作权限
     * 
     * 对象标识属性不为“id”时，无法使用缺省的ObjectIdentityImpl创建,需要自己定义
     * 
     * @param objectIdentity 对象标识 
     * @return 返回权限集合
     */
    Set<Permission> getPermissions(ObjectIdentity objectIdentity);
    
    /**
     * 查询对象访问控制情况
     * 
     * @param object 对象
     * @return 返回控制访问列表
     */
    List<AccessControlEntry> findAces(Object object);
    
    /**
     * 通过对象标识查询对象访问控制情况
     * 
     * @param objectIdentity 对象标识 
     * @return
     */
    List<AccessControlEntry> findAces(ObjectIdentity objectIdentity);
    
    /**
     * 实体权限继承
     * 
     * <p>父对象标识不为空，则继承权限</p>
     * 
     * @param objectIdentity 被设置访问控制的对象标识
     * @param parentIdentity 被设置访问控制的父对象标识
     */
    void entryInheriting(ObjectIdentity objectIdentity,ObjectIdentity parentIdentity);
    
    /**
     * 更新对象访问控制权限
     * 
     * 父对象不为空，则继承权限
     * 
     * @param object 被设置访问控制的对象
     * @param sidPermissions 控制访问集合(key:Sid,value:Permission)
     * @param parent 被设置访问控制的父对象
     */
    void updatePermissions(Object object,Map<Sid,Permission> sidPermissions,Object parent);
    
    /**
     * 通过对象标识更新访问控制权限
     * 
     * 父对象标识不为空，则继承权限
     * 
     * @param objectIdentity 被设置访问控制的对象标识
     * @param sidPermissions 控制访问集合(key:Sid,value:Permission)
     * @param parentIdentity 被设置访问控制的父对象标识
     */
    void updatePermissions(ObjectIdentity objectIdentity,Map<Sid,Permission> sidPermissions,ObjectIdentity parentIdentity);
    
    /**
     * 更新对象访问控制权限
     * 
     * 父对象不为空，则继承权限
     * 
     * @param object 被设置访问控制的对象
     * @param sidNamePermissionMasks 控制访问集合(key:sid name,value:permission mark)
     * @param parent 被设置访问控制的父对象
     */
    void updatePermissionsBySidNamePermissionMask(Object object,Map<String,Integer> sidNamePermissionMasks,Object parent);
    
    /**
     * 通过对象标识更新访问控制权限
     * 
     * 父对象不为空，则继承权限
     * 
     * @param object 被设置访问控制的对象
     * @param sidNamePermissionMarks 控制访问集合(key:sid name,value:permission mark)
     * @param parent 被设置访问控制的父对象
     */
    void updatePermissionsBySidNamePermissionMask(ObjectIdentity objectIdentity,Map<String,Integer> sidNamePermissionMasks,ObjectIdentity parentIdentity);
}
