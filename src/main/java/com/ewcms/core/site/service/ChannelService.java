/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.service;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
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
import com.ewcms.core.site.dao.TemplateDAO;
import com.ewcms.core.site.dao.TemplateSourceDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.util.ConvertUtil;
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
    private TemplateDAO templateDAO;
    @Autowired
    private TemplateSourceDAO templateSourceDAO;
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
            	permit = getEntriesInheriting(channel, autorityNames, groupNames, userDetail);
            }
            
            if (permit){
	            ChannelNode node = new ChannelNode(channel,getPermissionsofChannel(channel));
	            nodes.add(node);
            }
        }
        return nodes;
    }
    
    private Boolean getEntriesInheriting(Channel channel, List<String> autorityNames, List<String> groupNames, UserDetails userDetail){
    	Acl acl = findAclOfChannel(channel);
    	
    	if (acl == null) return false;
    	
        if(acl.getEntries() == null || acl.getEntries().isEmpty()) {
            Boolean inherit = acl.isEntriesInheriting();
            if (!inherit) {
            	return false;
            }else{
            	Channel parent = channel.getParent();
               	if (parent == null) return false;
               	
               	return getEntriesInheriting(parent, autorityNames, groupNames, userDetail);
            }
        }
        List<AccessControlEntry> aces = acl.getEntries();
        
        for(AccessControlEntry ace : aces){
        	Sid sid = ace.getSid();
            String n = (sid instanceof PrincipalSid) ? ((PrincipalSid)sid).getPrincipal() : ((GrantedAuthoritySid)sid).getGrantedAuthority();
			if (groupNames.contains(n) || userDetail.getUsername().equals(n)){
				return true;
			}
        }
        
        Boolean inherit = acl.isEntriesInheriting();
        if (!inherit) return false;
      	
        Channel parent = channel.getParent();
       	if (parent == null) return false;
       	
       	return getEntriesInheriting(parent, autorityNames, groupNames, userDetail);
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
            initAclOfChannel(channel,true,false);
        }
//        Sid principalSid = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
//        aclService.addPermission(channel,principalSid,EwcmsPermission.ADMIN);
//
//        
//        aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_USER"),EwcmsPermission.READ);
//        aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_WRITER"),EwcmsPermission.WRITE);
//        aclService.addPermission(channel,new GrantedAuthoritySid("ROLE_EDITOR"),EwcmsPermission.PUBLISH);
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
        channel.setDir(ConvertUtil.pinYin(name));
        channel.setSort(channelDAO.findMaxSiblingChannel(parentId) + 1);
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
	
	@Override
	public void downChannel(Integer channelId, Integer parentId) {
		if (channelId != null && parentId != null){
			Long maxSort = channelDAO.findMaxSiblingChannel(parentId);
			Channel channel = channelDAO.get(channelId);
			if (channel.getSort().longValue() != maxSort.longValue()){
				Long sort = channel.getSort();
				sort = sort + 1;
				
				Channel channel_prev = channelDAO.findChannelByParentIdAndSort(parentId, sort);
				
				channel.setSort(sort);
				channel_prev.setSort(sort - 1);
				
				channelDAO.merge(channel);
				channelDAO.merge(channel_prev);
			}
		}
	}

	@Override
	public void upChannel(Integer channelId, Integer parentId) {
		if (channelId != null && parentId != null){
			Channel channel = channelDAO.get(channelId);
			if (channel.getSort().longValue() > 1){
				Long sort = channel.getSort();
				sort = sort - 1;
				Channel channel_prev = channelDAO.findChannelByParentIdAndSort(parentId, sort);
				
				channel.setSort(sort);
				channel_prev.setSort(sort + 1);
				
				channelDAO.merge(channel);
				channelDAO.merge(channel_prev);
			}
		}
	}
	
	public void moveSortChannel(Integer channelId, Integer parentId, Long sort){
		if (channelId != null && parentId != null){
			Channel moveChannel = channelDAO.get(channelId);
			
			Long oldSort = moveChannel.getSort();
			if (sort.longValue() == oldSort.longValue()) return;

			moveChannel.setSort(sort);
			
			if (sort.longValue() > oldSort.longValue()){
				List<Channel> channels = channelDAO.findChannelByParentIdAndLtSort(channelId, parentId, sort, oldSort);
				if (channels == null || channels.isEmpty()) return;
				
				for (Channel channel : channels){
					sort--;
					channel.setSort(sort);
					channelDAO.merge(channel);
				}
			}else{
				List<Channel> channels = channelDAO.findChannelByParentIdAndGtSort(channelId, parentId, sort, oldSort);
				if (channels == null || channels.isEmpty()) return;
				
				for (Channel channel : channels){
					sort++;
					channel.setSort(sort);
					channelDAO.merge(channel);
				}
			}
			
			channelDAO.merge(moveChannel);
		}
	}

	@Override
	public void moveToChannel(Integer channelId, Integer parentId) {
		Channel moveChannel = channelDAO.get(channelId);
		Long sort = moveChannel.getSort();
		Integer moveParentId = moveChannel.getParent().getId();
		
		Channel targetParentChannel = channelDAO.get(parentId);
		Long maxSort = channelDAO.findMaxSiblingChannel(parentId);
		moveChannel.setParent(targetParentChannel);
		moveChannel.setSort(maxSort + 1);
		
		List<Channel> channels = channelDAO.findChannelByGreaterThanSort(moveParentId, sort);
		if (channels != null && !channels.isEmpty()){
			for (Channel channel : channels){
				channel.setSort(channel.getSort() - 1);
				channelDAO.merge(channel);
			}
		}	
		
		updChannel(moveChannel);
	}
	
	@Override
	public void exportChannelZip(Integer channelId, ZipOutputStream zos, String channelPath){
		try{
			Channel channel = getChannel(channelId);
			if (channel == null) return;

			//创建栏目目录
			String filePath = channelPath + channel.getName() + "/";
			ZipEntry zipEntry = new ZipEntry(filePath);
			zipEntry.setUnixMode(755);
			zos.putNextEntry(zipEntry);

			//创建栏目目录下一级的“模板”目录
			String templatePath = filePath + "模板/";
			zipEntry = new ZipEntry(templatePath);
			zipEntry.setUnixMode(755);
			zos.putNextEntry(zipEntry);
			
			//创建栏目目录下一级的“资源”目录
			String templateSourcePath = filePath + "资源/";
			zipEntry = new ZipEntry(templateSourcePath);
			zipEntry.setUnixMode(755);
			zos.putNextEntry(zipEntry);
			
			List<Template> templates = templateDAO.getTemplatesInChannel(channelId);
			List<TemplateSource> templateSources = templateSourceDAO.getTemplateSourceInChannel(channelId);
			
			for (Template template : templates){
				if (template == null || template.getTemplateEntity() == null) continue;
				String fileName = template.getName();
				
				zipEntry = new ZipEntry(templatePath + fileName);
				zipEntry.setUnixMode(644);
				
				zos.putNextEntry(zipEntry);
				
				BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(template.getTemplateEntity().getTplEntity()));
				int b;
				while((b = bis.read())!=-1){  
					zos.write(b);  
		        }  
				
				zos.closeEntry();
				bis.close();
			}
			
			for (TemplateSource templateSource : templateSources){
				if (templateSource == null || templateSource.getSourceEntity() == null) continue;
				String fileName = templateSource.getName();
				
				zipEntry = new ZipEntry(templateSourcePath + fileName);
				zipEntry.setUnixMode(644);
				zos.putNextEntry(zipEntry);
				
				BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(templateSource.getSourceEntity().getSrcEntity()));
				int b;
				while((b = bis.read())!=-1){  
					zos.write(b);  
		        }  
				
				zos.closeEntry();
				bis.close();
			}
			
			List<Channel> childrenChannels = getChannelChildren(channelId);
			if (childrenChannels != null){
				for (Channel childrenChannel : childrenChannels){
					exportChannelZip(childrenChannel.getId(), zos, filePath);
				}
			}
		}catch(Exception e){
		}
	}
	
	@Override
	public List<Integer> findAppChannel(Integer channelId){
		List<Integer> channelIds = new ArrayList<Integer>();
		Channel channel = channelDAO.get(channelId);
		String appChannel = channel.getAppChannel();
		if (appChannel != null && appChannel.length() > 0){
			String[] appChannelIds = appChannel.split(",");
			for (String appChannelId : appChannelIds){
				try{
					Channel vo = channelDAO.get(Integer.valueOf(appChannelId));
					if (vo != null && vo.getId() != null) {
						channelIds.add(vo.getId());
					}
				}catch(Exception e){
				}
			}
		}
		
		return channelIds;
	}
	
	@Override
	public void delAppChannel(Integer channelId, Integer appChannelId){
		List<Integer> appChannelIds = findAppChannel(channelId);
		if (!appChannelIds.isEmpty()){
			appChannelIds.remove(appChannelId);
			Channel channel = channelDAO.get(channelId);
			if (appChannelIds.isEmpty()){
				channel.setAppChannel(null);
			}else{
				StringBuffer sb = new StringBuffer();
				int appChannelIdSize = appChannelIds.size();
				for (int i = 0; i < appChannelIdSize - 1; i++){
					if (channelId.intValue() == appChannelIds.get(i).intValue()) continue;
					sb.append(appChannelIds.get(i) + ",");
				}
				sb.append(appChannelIds.get(appChannelIdSize - 1));
				channel.setAppChannel(sb.toString());
			}
			channelDAO.merge(channel);
		}		
	}

	@Override
	public Channel getChannelParent(Integer id) {
		Channel channel = channelDAO.get(id);
		if (channel != null && channel.getParent() != null) return channel.getParent();
		return null;
	}
}
