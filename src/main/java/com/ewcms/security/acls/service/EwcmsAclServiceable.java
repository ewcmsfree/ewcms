/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.acls.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

/**
 * 对象控制访问接口
 * 
 * @author wangwei
 */
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
     * 更新继承权限
     * 
     * <p>父对象标识不为空，则继承权限</p>
     * 
     * @param object 被设置访问控制的对象
     * @param parent 被设置访问控制的父对象
     */
    void updateInheriting(Object object,Object parent);
    
    /**
     * 添加对象访问控制权限
     * 
     * @param object 被设置访问控制的对象
     * @param name 名称（如：用户称）
     * @param mask 权限编码
     */
    void addPermission(Object object,String name,Integer mask);
    
    /**
     * 添加对象访问控制权限
     * 
     * @param object 被设置访问控制的对象
     * @param sid 身份对象
     * @param Permission 权限
     */
    void addPermission(Object object,Sid sid,Permission permisson);

    /**
     * 删除对象访问控制权限
     * 
     * @param object 被设置访问控制的对象
     * @param name 名称（如：用户称）
     */
    void removePermission(Object obejct,String name);
    
    /**
     * 权限不存在添加权限，权限存在修改
     * 
     * @param object 被设置访问控制的对象
     * @param name 名称（如：用户称）
     * @param mask 权限编码
     */
    void addOrUpdatePermission(Object object,String name,Integer mask);
}
