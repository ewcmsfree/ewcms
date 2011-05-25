/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.security.manage.service;

import java.util.Date;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ewcms.security.manage.model.User;
import com.ewcms.security.manage.model.UserInfo;

/**
 *
 * @author 王伟
 */
public interface UserServiceable extends UserDetailsService{


    /**
     * 添加用户
     * 
     * 如果用户已经存在，则抛出UsernameExistException异常。
     * 
     * @param username 用户名
     * @param password 密码
     * @param enabled  有效
     * @param accountStart 授权开始时间
     * @param accountEnd 授权结束时间
     * @Param userInfo 用户详细信息
     * @param authNames 赋予权限名称集合
     * @param groupNames 所属用户组名
     * 
     * @return 用户名
     * @throws UserServiceException
     */
    String addUser(String username,String password,boolean enabled,
            Date accountStart,Date accountEnd,UserInfo userInfo,
            Set<String> authNames,Set<String> groupNames)
            throws UserServiceException;

    /**
     * 更新用户信息
     * 
     * @param username 用户名
     * @param enabled  有效
     * @param accountStart 授权开始时间
     * @param accountEnd 授权结束时间
     * @Param userInfo 用户详细信息
     * @param authNames 赋予权限名称集合
     * @param groupNames 所属用户组名
     * 
     * @return 用户名
     * @throws UserServiceException
     */
    String updateUser(String username,boolean enabled,
            Date accountStart,Date accountEnd,UserInfo userInfo,
            Set<String> authNames,Set<String> groupNames)
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
    void deleteUser(String username);
    
    /**
     * 激活（开启）用户
     * 
     * 失效用户可以通过该过程激活，成为正常用户
     * 
     * @param username 用户名
     */
    void activeUser(String username);
    
    /**
     * 失效（关闭）用户
     * 
     * 正常用户可以通过该过程失效，成为无效用户
     * 
     * @param username
     */
    void inactiveUser(String username);
    
    /**
     * 得到当前用户信息
     * 
     * @return
     */
    UserInfo getCurrentUserInfo();
    
    /**
     * 更新当前用户信息
     * 
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 用户修改密码
     * 
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    void changePassword(String oldPassword,String newPassword);

    /**
     * 初始用户密码
     * 
     * 用户丢失自己密码，管理初始一个新密码，再次开通该用户
     * 
     * @param username  用户名 
     * @param password 密码
     */
    void initPassword(String username,String password);

    /**
     * 判读用户名是否存在
     * 
     * 用户名是关键字，新增时判断用户名是否被用
     * 
     * @param username 用户名
     * @return if true 存在 ,if false 不存在
     */
    boolean usernameExist(String username);
    
    /**
     * 得到缺省用户密码
     * @return 缺省密码
     */
    String getDefaultPassword();
    
    String getUserRealName();
}
