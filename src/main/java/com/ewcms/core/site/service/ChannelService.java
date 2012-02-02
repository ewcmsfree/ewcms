/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.acls.domain.EwcmsPermission;
import com.ewcms.security.acls.service.EwcmsAclServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现专栏服务
 * 
 * @author 周冬初
 */
@Service
public class ChannelService implements ChannelServiceable{
    
    @Autowired
    private ChannelDAO channelDao;
    
    @Autowired
    private EwcmsAclServiceable aclService;

    private Set<Permission> getPermissionsofChannel(Channel channel) {
        Assert.notNull(channel, "channel is null");
        return aclService.getPermissions(channel);
    }
    
    @Override
    public Set<Permission> getPermissionsById(int id) {
        return getPermissionsofChannel(getChannel(id));
    }
    
    @Override
    public Acl findAclOfChannel(Channel channel){
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(channel);
        try{
            return aclService.readAclById(objectIdentity);
        }catch(NotFoundException e){
            return null;
        }        
    }
    
    @Override
    public void addOrUpdatePermission(Integer id, String name, Integer mask) {
        Channel channel = channelDao.get(id);
        Assert.notNull(channel, "channel is null");
        
        aclService.addOrUpdatePermission(channel, name, mask);
    }

    @Override
    public void removePermission(Integer id, String name) {
        Channel channel = channelDao.get(id);
        Assert.notNull(channel, "channel is null");
        
        aclService.removePermission(channel, name);
    }

    @Override
    public void updateInheriting(Integer id, boolean inheriting) {
        Channel channel = channelDao.get(id);
        Assert.notNull(channel, "channel is null");
        
        Channel parent = inheriting ? channel.getParent() : null;
        aclService.updateInheriting(channel,parent);
    }
    
    @Override
    public List<ChannelNode> getChannelChildren(Integer id,Boolean publicenable) {
        List<ChannelNode> nodes = new ArrayList<ChannelNode>();
        List<Channel> channels = channelDao.getChannelChildren(id);
        for (Channel channel : channels) {
            if (publicenable && !channel.getPublicenable()) {
                continue;
            }
            ChannelNode node = new ChannelNode(channel,getPermissionsofChannel(channel));
            nodes.add(node);
        }
        return nodes;
    }
    
    /**
     * 初始站点专栏访问权限
     * 
     * @param channel 站点专栏
     * @param createCommon 创建通用权限(true:创建通用权限)
     * @param inherit 继承父站点专栏权限
     */
    private void initAclOfChannel(Channel channel,boolean createCommon,boolean inherit){

        Sid principalSid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        aclService.addPermission(channel,principalSid,EwcmsPermission.ADMIN);

        if(createCommon){
            aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_USER"),EwcmsPermission.READ);
            aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_WRITER"),EwcmsPermission.WRITE);
            aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_EDITOR"),EwcmsPermission.PUBLISH);
        }
        
        if(inherit && channel.getParent() != null){
             aclService.updateInheriting(channel, channel.getParent());
        }
    }
    
    @Override
    public ChannelNode channelNodeRoot() {
        Channel channel = channelDao.getChannelRoot(getCurSite().getId());
        if (channel == null) {
            channel = new Channel();
            channel.setDir("");
            channel.setPubPath("");
            channel.setAbsUrl("");
            channel.setUrl("");
            channel.setName(getCurSite().getSiteName());
            channel.setSite(getCurSite());
            channelDao.persist(channel);
            initAclOfChannel(channel,false,false);
        }
        return new ChannelNode(channel,getPermissionsofChannel(channel));
    }
    
    @Override
    public Channel getChannelRoot() {
        Channel channel = channelDao.getChannelRoot(getCurSite().getId());
        return channel;
    }
    
    @Override
    public Integer addChannel(Integer parentId, String name) {
        Assert.notNull(parentId, "parentId is null");
        Channel channel = new Channel();
        channel.setName(name);
        channel.setParent(channelDao.get(parentId));
        channel.setSite(getCurSite());
        channelDao.persist(channel);
        initAclOfChannel(channel,true,true);
        return channel.getId();
    }

    @Override
    public void renameChannel(Integer id, String name) {
        Channel vo = getChannel(id);
        vo.setName(name);
        channelDao.merge(vo);
    }

    @Override
    public Integer updChannel(Channel channel) {
        channelDao.merge(channel);
        updAbsUrlAndPubPath(channel.getId(),getCurSite().getId());
        return channel.getId();
    }

    /**
     * 更新子专栏的AbsUrl和PubPath
     * 
     * 专栏的目录和链接地址发生改变,则子站点专栏的absUrl和pubPath也要发生改变。
     * 
     * @param channelId 专栏编号
     * @param siteId 站点编号
     */
    private void updAbsUrlAndPubPath(int channelId,int siteId) {
        Channel channel = channelDao.get(channelId);
        if(channel == null || (int)channel.getSite().getId() != siteId){
            return;
        }
        List<Channel> children = channelDao.getChannelChildren(channelId);
        for (Channel child : children ) {
            child.setAbsUrl(null);
            child.setPubPath(null);
            channelDao.merge(child);
            updAbsUrlAndPubPath(child.getId(),siteId);
        }
    }

    public void delChannel(Integer id) {
        channelDao.removeByPK(id);
    }

    public Channel getChannel(Integer id) {
        return channelDao.get(id);
    }

    private Site getCurSite() {
        return EwcmsContextUtil.getCurrentSite();
    }

	@Override
	public Channel getChannelRoot(Integer siteId) {
		return channelDao.getChannelRoot(siteId);
	}

	@Override
	public Channel getChannel(Integer siteId, Integer id) {
		return this.getChannel(id);
	}

	@Override
	public List<Channel> getChannelChildren(Integer id) {
		return channelDao.getChannelChildren(id);
	}

	@Override
	public Channel getChannelByUrlOrPath(Integer siteId, String path) {
		return channelDao.getChannelByURL(siteId, path);
	}
}
