/**
 *
 */
package com.ewcms.core.site.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ewcms.web.context.EwcmsContextUtil;

/**
 * @author 周冬初
 * 
 */
@Service
public class ChannelService {

    @Autowired
    private ChannelDAO channelDao;

    @Autowired
    private EwcmsAclServiceable aclService;
    
    @PreAuthorize("isAuthenticated()")
    public Set<Permission> getPermissionsOfChannelById(final Integer id) {
        final Channel channel = channelDao.get(id);
        Assert.notNull(channel, "channel is null");
        return getPermissionsofChannel(channel);
    }

    @PreAuthorize("isAuthenticated()")
    public Set<Permission> getPermissionsofChannel(final Channel channel) {
        Assert.notNull(channel, "channel is null");
        return aclService.getPermissions(channel);
    }


    @PreAuthorize("isAuthenticated()")
    public Acl findAclOfChannel(final Channel channel){
        final ObjectIdentity objectIdentity = new ObjectIdentityImpl(channel);
        try{
            return aclService.readAclById(objectIdentity);
        }catch(NotFoundException e){
            return null;
        }        
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    public void updatePermissionOfChannel(final Integer id,final Map<String,Integer> sidNamePermissionMasks,boolean inherit){
        Channel channel = getChannel(id);
        Channel parent = null;
        if(inherit){
            parent = channel.getParent();
        }
        aclService.updatePermissionsBySidNamePermissionMask(channel, sidNamePermissionMasks, parent);
    }
    
    /**
     * 获取当前频道的子频道
     * 
     * TODO 说明原因
     * 
     * @param id 频道编号 
     * @param publicenable 是否发布(true:只显示发布的子频道,false:显示所有子频道）
     * @return 子频道集合
     */
    @PreAuthorize("isAuthenticated()")
    public List<ChannelNode> getChannelChildren(Integer id,Boolean publicenable) {
        List<ChannelNode> nodes = new ArrayList<ChannelNode>();
        List<Channel> channels = channelDao.getChannelChildren(id,getCurSite().getId());
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
    private void initAclOfChannel(final Channel channel,final boolean createCommon,final boolean inherit){
        Map<Sid,Permission> sidPermissions = new LinkedHashMap<Sid,Permission>();
        
        final Sid principalSid = new PrincipalSid(
                SecurityContextHolder.getContext().getAuthentication());
        sidPermissions.put(principalSid, EwcmsPermission.ADMIN);

        if(createCommon){
            final Sid userSid = new GrantedAuthoritySid("ROLE_USER");
            sidPermissions.put(userSid, EwcmsPermission.READ);
            final Sid writerSid = new GrantedAuthoritySid("ROLE_WRITER");
            sidPermissions.put(writerSid, EwcmsPermission.WRITE);
            final Sid editorSid = new GrantedAuthoritySid("ROLE_EDITOR");
            sidPermissions.put(editorSid, EwcmsPermission.PUBLISH);
        }
        Channel parent = null;
        if(inherit && channel.getParent() != null){
             parent = channel.getParent();
        }
        
        aclService.updatePermissions(channel, sidPermissions, parent);
    }
    
    /**
     * 得到顶级站点专栏（根站点专栏）
     * 
     * 顶级站点专栏不存在，则创建顶级站点专栏。
     * 
     * @return channel
     */
    @PreAuthorize("isAuthenticated()")
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
    
    public Channel getChannelRoot() {
        Channel channel = channelDao.getChannelRoot(getCurSite().getId());
        return channel;
    }
    /**
     * 创建站点专栏.
     * 
     * @param parentId 父栏目编号.
     * @param name 栏目名称.
     * 
     * @return 频道编号
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#parentId,'com.ewcms.core.site.model.Channel','ADMIN') " 
            +"or hasPermission(#parentId,'com.ewcms.core.site.model.Channel','CREATE')")
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

    /**
     * 重命名站点专栏.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN') " 
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel','UPDATE')")
    public void renameChannel(Integer id, String name) {
        Channel vo = getChannel(id);
        vo.setName(name);
        channelDao.merge(vo);
    }

    /**
     * 修改站点专栏.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#channel,'ADMIN') or hasPermission(#channel,'UPDATE')")
    public Integer updChannel(final Channel channel) {
        channelDao.merge(channel);
        updAbsUrlAndPubPath(channel);
        return channel.getId();
    }

    /**
     * 更新子站点专栏AbsUrl和PubPath
     * 
     * 当前站点专栏的目录(dir)和(url)发生改变,则子站点专栏的absUrl和pubPath也要发生改变。
     * 
     * @param channel 站点专栏
     */
    private void updAbsUrlAndPubPath(final Channel channel) {
        List<Channel> children = channelDao.getChannelChildren(channel.getId(), getCurSite().getId());
        for (Channel child : children ) {
            child.setAbsUrl(null);
            child.setPubPath(null);
            updAbsUrlAndPubPath(child);
        }
        channelDao.merge(channel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN') " 
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel','DELETE')")
    public void delChannel(Integer id) {
        channelDao.removeByPK(id);
    }

    public Channel getChannel(Integer id) {
        return channelDao.get(id);
    }

    private Site getCurSite() {
        return EwcmsContextUtil.getCurrentSite();
    }
}
