/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.core.site.service.ChannelServiceable;
import com.ewcms.core.site.service.OrganService;
import com.ewcms.core.site.service.SiteServiceable;
import com.ewcms.core.site.service.TemplateServiceable;
import com.ewcms.core.site.service.TemplateSourceServiceable;
import com.ewcms.publication.PublishException;
import com.ewcms.web.vo.TreeNode;
import freemarker.template.Configuration;

/**
 * 
 * @author 周冬初
 */
@Service
public class SiteFac{
	@Autowired
	private SiteServiceable siteService;
	@Autowired
	private TemplateServiceable templateService;
	@Autowired
	private ChannelServiceable channelService;
	@Autowired
	private OrganService organService;	
	@Autowired
	private TemplateSourceServiceable templateSourceService;
	@Autowired
    private Configuration cfg;
    
    @PreAuthorize("isAuthenticated()")
    public Acl findAclOfChannel(final Channel channel){
    	return channelService.findAclOfChannel(channel);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    public void addOrUpdatePermission(Integer id,String name,int mask){
    	channelService.addOrUpdatePermission(id, name, mask);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    public void removePermission(Integer id,String name){
        channelService.removePermission(id, name);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    public void updateInheriting(Integer id,boolean inheriting){
        channelService.updateInheriting(id, inheriting);
    }
    
	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable) {
		return siteService.getSiteListByOrgans(organs, publicenable);
	}
	
	public Set<Permission> getPermissionsById(int id) {
		return channelService.getPermissionsById(id);
	}
	
	public List<TreeNode> getOrganSiteTreeList(Integer organId) {
		return siteService.getOrganSiteTreeList(organId);
	}

	public List<TreeNode> getOrganSiteTreeList(Integer parentId, Integer organId) {
		return siteService.getOrganSiteTreeList(parentId, organId);
	}

	public List<TreeNode> getCustomerSiteTreeList() {
		return siteService.getCustomerSiteTreeList();
	}

	public List<TreeNode> getCustomerSiteTreeList(Integer parentId) {
		return siteService.getCustomerSiteTreeList(parentId);
	}

	public Integer addSite(Integer parentId, String siteName,Integer organId){
		return siteService.addSite(parentId,siteName,organId);
	}

	public void delSiteBatch(List<Integer> idList) {
		siteService.delSiteBatch(idList);		
	}
	
	public Integer updSite(Site vo) {
		return siteService.updSite(vo);
	}
	
	public void delSite(Integer id) {
		siteService.delSite(id);		
	}

	public Integer saveSiteServer(Site vo){
		return siteService.saveSiteServer(vo);
	}
	
	public Site getSite(Integer id) {
		return siteService.getSite(id);
	}
	
	public Template getTemplate(Integer id) {
		return templateService.getTemplate(id);
	}

	public Integer addTemplate(Template vo) {
		return templateService.addTemplate(vo);
	}

	public Integer updTemplate(Template vo) {
		Integer id = templateService.updTemplate(vo);
		cfg.clearTemplateCache();
		return id;
	}

	
	public void delTemplateBatch(List<Integer> idList) {
		templateService.delTemplateBatch(idList);		
		cfg.clearTemplateCache();
	}

	
	public void delTemplate(Integer id) {
		templateService.delTemplate(id);		
	}

	public List<Template> getTemplateList() {
		return templateService.getTemplateList();
	}

	@PreAuthorize("isAuthenticated()")
	public List<ChannelNode> getChannelChildren(Integer parentId,
			Boolean publicenable) {
		return channelService.getChannelChildren(parentId, publicenable);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#parentId,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','CREATE'})") 
	public Integer addChannel(Integer parentId, String name) {
		return channelService.addChannel(parentId, name);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','UPDATE'})")
	public void renameChannel(Integer id, String name) {
		channelService.renameChannel(id, name);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#channel,new String[]{'ADMIN','UPDATE'})")	
	public Integer updChannel(Channel vo) {
		return channelService.updChannel(vo);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','DELETE'})")
	public void delChannel(Integer id) {
		channelService.delChannel(id);
	}

	
	public Channel getChannel(Integer id) {
		return channelService.getChannel(id);
	}	
	@PreAuthorize("isAuthenticated()")
	public Channel getChannelRoot() {
		return channelService.getChannelRoot();
	}
	
	public ChannelNode channelNodeRoot() {
		return channelService.channelNodeRoot();
	}
	
	public List<Template> getTemplaeTreeList(Boolean channelEnable) {
		return templateService.getTemplaeTreeList(channelEnable);
	}

	
	public List<Template> getTemplaeTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateService.getTemplaeTreeList(parentId, channelEnable);
	}

	
	public List<Template> getTemplaeTreeList(Integer parentId,
			String channelName) {
		return templateService.getTemplaeTreeList(parentId, channelName);
	}

	public void updSiteParent(Integer organId,Integer parentId, Integer newParentId) {
		siteService.updSiteParent(organId,parentId, newParentId);
		
	}

	public Integer addOrgan(Integer parentId, String organName) {
		return organService.addOrgan(parentId, organName);
	}
	
	public Integer updOrgan(Organ vo){
		return organService.updOrgan(vo);
	}
	
	public void delOrgan(Integer id){
		organService.delOrgan(id);
	}
	public Organ getOrgan(Integer id){
		return organService.getOrgan(id);
	}
	
	public Integer saveOrganInfo(Organ vo){
		return organService.saveOrganInfo(vo);	
	}

	
	public List<TreeNode> getOrganTreeList() {
		return organService.getOrganTreeList();
	}

	
	public List<TreeNode> getOrganTreeList(Integer parentId) {
		return organService.getOrganTreeList(parentId);
	}

	public TemplateSource getTemplateSource(Integer id) {
		return templateSourceService.getTemplateSource(id);
	}

	public Integer addTemplateSource(TemplateSource vo) {
		return templateSourceService.addTemplateSource(vo);
	}
	
	public Integer updTemplateSource(TemplateSource vo) {
		return templateSourceService.updTemplateSource(vo);
	}

	public void delTemplateSource(Integer id) {
		templateSourceService.delTemplateSource(id);	
	}

	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(channelEnable);
	}
	
	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(parentId, channelEnable);
	}
	
	public Template channelTemplate(String tplName) {
		return templateService.channelTemplate(tplName);
	}

	public Template channelTPLRoot() {
		return templateService.channelTPLRoot();
	}

	public TemplateSource channelTemplateSource(String srcName) {
		return templateSourceService.channelTemplateSource(srcName);
	}

	public TemplateSource channelSRCRoot() {
		return templateSourceService.channelSRCRoot();
	}
	
    public void saveAppChild(Integer channelId, List<Integer> templateIds){
    	templateService.saveAppChild(channelId, templateIds);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
	public void forceRelease(Integer channelId) throws PublishException{
		channelService.forceRelease(channelId);
	}
}
