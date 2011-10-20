/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage;

import java.util.Set;

import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;

/**
 * 权限管理接口
 * 
 * @author wangwei
 */
public interface SecurityFacable {

    /**
     * 得到用户组
     * 
     * @param name 用户名
     * @return 用户组对象
     */
    Group getGroup(String name);
    
    /**
     * 添加权限和用户到指定的用户组中
     * 
     * @param name       用户组名称
     * @param authNames  权限名称集合
     * @param usernames  用户名称集合
     */
    void addAuthsAndUsersToGroup(String name,Set<String> authNames,Set<String> usernames);
    
    /**
     * 移除指定用户组中的权限和用户
     * 
     * @param name       用户组名称
     * @param authNames  权限名称集合
     * @param usernames  用户名称集合
     */
    void removeAuthsAndUsersInGroup(String name,Set<String> authNames,Set<String> usernames);
    
    /**
     * 判断用户组名是否存在
     * 
     * @param name 用户组名 
     * @return if 'true' exist ,else 'false' not exist
     */
    boolean isGroupnameExist(String name);
    
    /**
     * 添加用户组
     * 
     * @param name 用户组名称
     * @param remark 备注
     * @return 用户组名称
     */
    String addGroup(String name,String remark);

    /**
     * 修改用户组
     *
     * @param name 用户组名称
     * @param remark 备注
     */
    void updateGroup(String name,String remark);
    
    /**
     * 删除用户组
     * 
     * @param name 用户组名称
     */
    void removeGroup(String name);
    
    /**
     * 得到用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    User getUser(String username);
    
    /**
     * 添加权限和用户组到指定的用户中
     * 
     * @param name        用户组名称
     * @param authNames   权限名称集合
     * @param groupNames  用户组名称集合
     */
    void addAuthsAndGroupsToUser(String name,Set<String> authNames,Set<String> groupNames);
    
    /**
     * 移除指定用户组中的权限和用户
     * 
     * @param name       用户组名称
     * @param authNames  权限名称集合
     * @param groupNames  用户组名称集合
     */
    void removeAuthsAndGroupsInUser(String name,Set<String> authNames,Set<String> groupNames);
}
