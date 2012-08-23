/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.core.site.ChannelNode;
import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.util.ConvertToPinYin;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishFacable;
import com.ewcms.security.acls.domain.EwcmsPermission;
import com.ewcms.security.acls.service.EwcmsAclServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现专栏服务
 * 
 * @author 周冬初
 * @author wuzhijun
 */
@Service
public class ChannelService implements ChannelServiceable{
    
    @Autowired
    private ChannelDAO channelDAO;
    @Autowired
    private EwcmsAclServiceable aclService;
	@Autowired
	private WebPublishFacable webPublish;

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
        Channel channel = channelDAO.get(id);
        Assert.notNull(channel, "channel is null");
        
        aclService.addOrUpdatePermission(channel, name, mask);
    }

    @Override
    public void removePermission(Integer id, String name) {
        Channel channel = channelDAO.get(id);
        Assert.notNull(channel, "channel is null");
        
        aclService.removePermission(channel, name);
    }

    @Override
    public void updateInheriting(Integer id, boolean inheriting) {
        Channel channel = channelDAO.get(id);
        Assert.notNull(channel, "channel is null");
        
        Channel parent = inheriting ? channel.getParent() : null;
        aclService.updateInheriting(channel,parent);
    }
    
    @Override
    public List<ChannelNode> getChannelChildren(Integer id,Boolean publicenable) {
        List<ChannelNode> nodes = new ArrayList<ChannelNode>();
        List<Channel> channels = channelDAO.getChannelChildren(id);
		List<String> autorityNames = (List<String>) EwcmsContextUtil.getAutoritynames();
		List<String> groupNames = (List<String>)EwcmsContextUtil.getGroupnames();
		UserDetails userDetail = EwcmsContextUtil.getUserDetails();
        Boolean permit = false;
        for (Channel channel : channels) {
            if (publicenable && !channel.getPublicenable()) continue;
            permit = false;
            if (autorityNames.contains("ROLE_ADMIN")){
            	permit = true;
            }else{
	            //根据用户组和用户查询显示栏目
	            Acl acl = findAclOfChannel(channel);
	            if(acl == null || acl.getEntries() == null || acl.getEntries().isEmpty()) continue;
	            List<AccessControlEntry> aces = acl.getEntries();
	            
	            for(AccessControlEntry ace : aces){
	            	Sid sid = ace.getSid();
	                String n = (sid instanceof PrincipalSid) ? ((PrincipalSid)sid).getPrincipal() : ((GrantedAuthoritySid)sid).getGrantedAuthority();
	    			if (groupNames.contains(n) || userDetail.getUsername().equals(n)){
	    				permit = true;
	    				break;
	    			}
	            }
            }
            
            if (permit){
	            ChannelNode node = new ChannelNode(channel,getPermissionsofChannel(channel));
	            nodes.add(node);
            }
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
        Channel channel = channelDAO.getChannelRoot(getCurSite().getId());
        if (channel == null) {
            channel = new Channel();
            channel.setDir("");
            channel.setPubPath("");
            channel.setAbsUrl("");
            channel.setUrl("");
            channel.setName(getCurSite().getSiteName());
            channel.setSite(getCurSite());
            channelDAO.persist(channel);
            initAclOfChannel(channel,false,false);
        }
        return new ChannelNode(channel,getPermissionsofChannel(channel));
    }
    
    @Override
    public Channel getChannelRoot() {
        Channel channel = channelDAO.getChannelRoot(getCurSite().getId());
        return channel;
    }
    
    @Override
    public Integer addChannel(Integer parentId, String name) {
        Assert.notNull(parentId, "parentId is null");
        Channel channel = new Channel();
        channel.setName(name);
        channel.setParent(channelDAO.get(parentId));
        channel.setSite(getCurSite());
        channel.setDir(ConvertToPinYin.covert(name));
        channelDAO.persist(channel);
        initAclOfChannel(channel,true,true);
        return channel.getId();
    }

    @Override
    public void renameChannel(Integer id, String name) {
        Channel vo = getChannel(id);
        vo.setName(name);
        channelDAO.merge(vo);
    }

    @Override
    public Integer updChannel(Channel channel) {
        channelDAO.merge(channel);
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
        Channel channel = channelDAO.get(channelId);
        if(channel == null || (int)channel.getSite().getId() != siteId){
            return;
        }
        List<Channel> children = channelDAO.getChannelChildren(channelId);
        for (Channel child : children ) {
            child.setAbsUrl(null);
            child.setPubPath(null);
            channelDAO.merge(child);
            updAbsUrlAndPubPath(child.getId(),siteId);
        }
    }

    public void delChannel(Integer id) {
        channelDAO.removeByPK(id);
    }

    public Channel getChannel(Integer id) {
        return channelDAO.get(id);
    }

    private Site getCurSite() {
        return EwcmsContextUtil.getCurrentSite();
    }

	@Override
	public Channel getChannelRoot(Integer siteId) {
		return channelDAO.getChannelRoot(siteId);
	}

	@Override
	public Channel getChannel(Integer siteId, Integer id) {
		return this.getChannel(id);
	}

	@Override
	public List<Channel> getChannelChildren(Integer id) {
		return channelDAO.getChannelChildren(id);
	}

	@Override
	public Channel getChannelByUrlOrPath(Integer siteId, String path) {
		return channelDAO.getChannelByURL(siteId, path);
	}
	
	@Override
	public void forceRelease(Integer channelId, Boolean children) throws PublishException{
		if (isNotNull(channelId)) {
			webPublish.publishChannel(channelId, true, children);
		}
	}
}
