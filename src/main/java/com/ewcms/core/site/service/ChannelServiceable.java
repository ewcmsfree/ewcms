/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Permission;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.model.Channel;
import com.ewcms.publication.service.ChannelPublishServiceable;

/**
 * 频道管理服务接口
 *  
 * @author 周冬初
 */
public interface ChannelServiceable extends ChannelPublishServiceable {

    /**
     * 得到当前用户访问频道权限
     * 
     * @param id 频道编号
     * @return 权限集合
     */
    public Set<Permission> getPermissionsById(int id);

    /**
     * 得到频道控制访问对象
     * 
     * @param channel 频道对象
     * @return
     */
    public Acl findAclOfChannel(Channel channel);

    /**
     * 添加频道权限
     * 
     * @param id 频道编号
     * @param name 名称（如：用户称）
     * @param mask 权限编码
     */
    public void addOrUpdatePermission(Integer id,String name,Integer mask);
    
    /**
     * 移除频道权限
     * 
     * @param id 频道编号
     * @param name 名称（如：用户称）
     */
    public void removePermission(Integer id,String name);
    
    /**
     * 更新继承权限
     * 
     * <p>父对象标识不为空，则继承权限</p>
     * 
     * @param id 频道编号
     * @param inheriting 是否继承权限（true:继承权限）
     */
    void updateInheriting(Integer id,boolean inheriting);
    

    /**
     * 获取当前频道的子频道
     * 
     * @param id
     *            频道编号
     * @param publicenable
     *            是否发布(true:只显示发布的子频道,false:显示所有子频道）
     * @return 子频道集合
     */
    public List<ChannelNode> getChannelChildren(Integer id, Boolean publicenable);

    /**
     * 得到顶级站点专栏（根站点专栏）
     * 
     * 顶级站点专栏不存在，则创建顶级站点专栏。
     * 
     * @return channel
     */
    public ChannelNode channelNodeRoot();

    public Channel getChannelRoot();

    /**
     * 创建站点专栏.
     * 
     * @param parentId
     *            父栏目编号.
     * @param name
     *            栏目名称.
     * 
     * @return 频道编号
     */
    public Integer addChannel(Integer parentId, String name);

    /**
     * 重命名站点专栏.
     */
    public void renameChannel(Integer id, String name);

    /**
     * 修改站点专栏.
     */
    public Integer updChannel(final Channel channel);

    public void delChannel(Integer id);

    public Channel getChannel(Integer id);
}