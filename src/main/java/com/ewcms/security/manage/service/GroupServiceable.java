/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

import java.util.Set;

import com.ewcms.security.manage.model.Authority;
import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;

public interface GroupServiceable {
    
    /**
     * 添加用户组
     * 
     * 与用户组相关联用户会被强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param remark 备注
     * @param authNames 权限名称列表
     * @param usernames 用户名称列表
     */
    void addGroup(String name,String remark,Set<String> authNames,Set<String> usernames);

    /**
     * 修改用户组
     *
     * 修改用户组权限或用户时，与用户组相关联用户会被强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param remark 备注
     * @param authNames 权限名称列表
     * @param usernames 用户名称列表
     */
    void updateGroup(String name,String remark,Set<String> authNames,Set<String> usernames);
    
    /**
     * 删除用户组
     * 
     * 与用户组相关联用户会被强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     */
    void removeGroup(String name);
    
    /**
     * 得到用户组
     * 
     * @param name 用户名
     * @return 用户组对象
     */
    Group getGroup(String name);
    
    /**
     * 添加用户到用户组
     * 
     * 添加用户强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param username 用户名称集合
     * @return 新增用户集合
     */
    Set<User> addUsersToGroup(String name,Set<String> usernames);
    
    /**
     * 移除用户组中用户
     * 
     * 被移除用户强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param username 用户组名称
     */
    void removeUserInGroup(String name,String username);
    
    /**
     * 添加权限到用户组
     * 
     * 用户组中用户强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param authNames 权限名称集合
     * @return 新增权限集合
     */
    Set<Authority> addAuthoritiesToGroup(String name,Set<String> authNames);
    
    /**
     * 移除用户组中权限
     * 
     * 用户组中用户被强迫退出（移除cache,session中的信息）。
     * 
     * @param name 用户组名称
     * @param authName 权限名称
     */
    void removeAuthInGroup(String name,String authName);
    
    /**
     * 判断用户组名是否存在
     * 
     * @param name 用户组名 
     * @return if 'true' exist ,else 'false' not exist
     */
    boolean isGroupnameExist(String name);
}
