/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage;

import java.util.Date;
import java.util.Set;

import com.ewcms.security.manage.model.Group;
import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.model.UserInfo;
import com.ewcms.security.manage.service.UserServiceException;

/**
 * 权限管理接口
 * 
 * @author wangwei
 */
public interface SecurityFacable {

    /**
     * 判断通过用权限名是否存在
     * 
     * @param name 通用权限名 
     * @return if 'true' exist ,else 'false' not exist
     */
    public boolean hasAuthorityname(String name);
    
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
    boolean hasGroupname(String name);
    
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
     * 添加用户
     * 
     * @param username 用户名
     * @param password 密码
     * @param enabled  有效
     * @param accountStart 授权开始时间
     * @param accountEnd 授权结束时间
     * @Param userInfo 用户详细信息
     * 
     * @return 用户名
     * @throws UserServiceException
     */
    String addUser(String username,String password,boolean enabled,
            Date accountStart,Date accountEnd,UserInfo userInfo)
            throws UserServiceException;

    /**
     * 更新用户信息
     * 
     * @param username 用户名
     * @param enabled  有效
     * @param accountStart 授权开始时间
     * @param accountEnd 授权结束时间
     * @Param userInfo 用户详细信息
     * 
     * @return 用户名
     * @throws UserServiceException
     */
    String updateUser(String username,boolean enabled,
            Date accountStart,Date accountEnd,UserInfo userInfo)
            throws UserServiceException;
    
    /**
     * 得到用户
     * 
     * @param username 用户名
     * @return 用户对象
     */
    User getUser(String username);
    
    /**
     * 删除用户
     * 
     * @param username 用户名
     */
    void removeUser(String username);
    
    /**
     * 添加权限和用户组到指定的用户中
     * 
     * @param username    用户组名称
     * @param authNames   权限名称集合
     * @param groupNames  用户组名称集合
     */
    void addAuthsAndGroupsToUser(String username,Set<String> authNames,Set<String> groupNames);
    
    /**
     * 移除指定用户组中的权限和用户
     * 
     * @param username   用户组名称
     * @param authNames  权限名称集合
     * @param groupNames  用户组名称集合
     */
    void removeAuthsAndGroupsInUser(String username,Set<String> authNames,Set<String> groupNames);
    
    /**
     * 激活（启用）用户
     * 
     * 失效用户可以通过该过程激活，成为正常用户
     * 
     * @param username 用户名
     */
    void activeUser(String username);
    
    /**
     * 失效（停用）用户
     * 
     * 正常用户可以通过该过程失效，成为无效用户
     * 
     * @param username
     */
    void inactiveUser(String username);
    
    /**
     * 判断用户名是否存在
     * 
     * 用户名是关键字，新增时判断用户名是否被用
     * 
     * @param username 用户名
     * @return if true 存在 ,if false 不存在
     */
    boolean hasUsername(String username);
    
    /**
     * 得到缺省用户密码
     * 
     * @return 缺省密码
     */
    String getDefaultPassword();
    
    /**
     * 用户修改密码
     * 
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    void changePassword(String oldPassword,String newPassword);
    
    /**
     * 更新当前用户信息
     * 
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);
    
    /**
     * 得到当前用户信息
     * 
     * @return
     */
    UserInfo getCurrentUserInfo();
}
