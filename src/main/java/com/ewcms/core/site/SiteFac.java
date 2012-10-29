/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site;

import java.util.List;
import java.util.Set;

import org.apache.tools.zip.ZipOutputStream;
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
import com.ewcms.core.site.service.OrganServiceable;
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
public class SiteFac implements SiteFacable{
	@Autowired
	private SiteServiceable siteService;
	@Autowired
	private TemplateServiceable templateService;
	@Autowired
	private ChannelServiceable channelService;
	@Autowired
	private OrganServiceable organService;	
	@Autowired
	private TemplateSourceServiceable templateSourceService;
	@Autowired
    private Configuration cfg;
    
    @PreAuthorize("isAuthenticated()")
    @Override
    public Acl findAclOfChannel(final Channel channel){
    	return channelService.findAclOfChannel(channel);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    @Override
    public void addOrUpdatePermission(Integer id,String name, Integer mask){
    	channelService.addOrUpdatePermission(id, name, mask);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    @Override
    public void removePermission(Integer id,String name){
        channelService.removePermission(id, name);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    @Override
    public void updateInheriting(Integer id,boolean inheriting){
        channelService.updateInheriting(id, inheriting);
    }
    
    @Override
	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable) {
		return siteService.getSiteListByOrgans(organs, publicenable);
	}
	
    @Override
	public Set<Permission> getPermissionsById(int id) {
		return channelService.getPermissionsById(id);
	}
	
    @Override
	public List<TreeNode> getOrganSiteTreeList(Integer organId) {
		return siteService.getOrganSiteTreeList(organId);
	}

    @Override
	public List<TreeNode> getOrganSiteTreeList(Integer parentId, Integer organId) {
		return siteService.getOrganSiteTreeList(parentId, organId);
	}

    @Override
	public List<TreeNode> getCustomerSiteTreeList() {
		return siteService.getCustomerSiteTreeList();
	}

    @Override
	public List<TreeNode> getCustomerSiteTreeList(Integer parentId) {
		return siteService.getCustomerSiteTreeList(parentId);
	}

    @Override
	public Integer addSite(Integer parentId, String siteName,Integer organId){
		return siteService.addSite(parentId,siteName,organId);
	}

    @Override
	public void delSiteBatch(List<Integer> idList) {
		siteService.delSiteBatch(idList);		
	}
	
    @Override
	public Integer updSite(Site vo) {
		return siteService.updSite(vo);
	}
	
    @Override
	public void delSite(Integer id) {
		siteService.delSite(id);		
	}

    @Override
	public Integer saveSiteServer(Site vo){
		return siteService.saveSiteServer(vo);
	}
	
    @Override
	public Site getSite(Integer id) {
		return siteService.getSite(id);
	}
	
    @Override
	public Template getTemplate(Integer id) {
		return templateService.getTemplate(id);
	}

    @Override
	public Integer addTemplate(Template vo) {
		return templateService.addTemplate(vo);
	}

    @Override
	public Integer updTemplate(Template vo) {
		Integer id = templateService.updTemplate(vo);
		cfg.clearTemplateCache();
		return id;
	}
	
    @Override
	public void delTemplateBatch(List<Integer> idList) {
		templateService.delTemplateBatch(idList);		
		cfg.clearTemplateCache();
	}
	
    @Override
	public void delTemplate(Integer id) {
		templateService.delTemplate(id);		
	}

    @Override
	public List<Template> getTemplateList() {
		return templateService.getTemplateList();
	}

	@PreAuthorize("isAuthenticated()")
    @Override
	public List<ChannelNode> getChannelChildren(Integer parentId,
			Boolean publicenable) {
		return channelService.getChannelChildren(parentId, publicenable);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#parentId,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','CREATE'})") 
    @Override
	public Integer addChannel(Integer parentId, String name) {
		return channelService.addChannel(parentId, name);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','UPDATE'})")
    @Override
	public void renameChannel(Integer id, String name) {
		channelService.renameChannel(id, name);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#channel,new String[]{'ADMIN','UPDATE'})")	
    @Override
	public Integer updChannel(Channel vo) {
		return channelService.updChannel(vo);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') "
            +"or hasPermission(#id,'com.ewcms.core.site.model.Channel',new String[]{'ADMIN','DELETE'})")
    @Override
	public void delChannel(Integer id) {
		channelService.delChannel(id);
	}

    @Override
	public Channel getChannel(Integer id) {
		return channelService.getChannel(id);
	}	

    @PreAuthorize("isAuthenticated()")
    @Override
	public Channel getChannelRoot() {
		return channelService.getChannelRoot();
	}
	
    @Override
	public ChannelNode channelNodeRoot() {
		return channelService.channelNodeRoot();
	}
	
    @Override
	public List<Template> getTemplaeTreeList(Boolean channelEnable) {
		return templateService.getTemplaeTreeList(channelEnable);
	}

    @Override
	public List<Template> getTemplaeTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateService.getTemplaeTreeList(parentId, channelEnable);
	}

    @Override
	public List<Template> getTemplaeTreeList(Integer parentId,
			String channelName) {
		return templateService.getTemplaeTreeList(parentId, channelName);
	}

    @Override
	public void updSiteParent(Integer organId,Integer parentId, Integer newParentId) {
		siteService.updSiteParent(organId,parentId, newParentId);
	}

    @Override
	public Integer addOrgan(Integer parentId, String organName) {
		return organService.addOrgan(parentId, organName);
	}
	
    @Override
	public Integer updOrgan(Organ vo){
		return organService.updOrgan(vo);
	}
	
    @Override
	public void delOrgan(Integer id){
		organService.delOrgan(id);
	}

    @Override
	public Organ getOrgan(Integer id){
		return organService.getOrgan(id);
	}
	
    @Override
	public Integer saveOrganInfo(Organ vo){
		return organService.saveOrganInfo(vo);	
	}

    @Override
	public List<TreeNode> getOrganTreeList() {
		return organService.getOrganTreeList();
	}

    @Override
	public List<TreeNode> getOrganTreeList(Integer parentId) {
		return organService.getOrganTreeList(parentId);
	}

    @Override
	public TemplateSource getTemplateSource(Integer id) {
		return templateSourceService.getTemplateSource(id);
	}

    @Override
	public Integer addTemplateSource(TemplateSource vo) {
		return templateSourceService.addTemplateSource(vo);
	}
	
    @Override
	public Integer updTemplateSource(TemplateSource vo) {
		return templateSourceService.updTemplateSource(vo);
	}

    @Override
	public void delTemplateSource(Integer id) {
		templateSourceService.delTemplateSource(id);	
	}

    @Override
	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(channelEnable);
	}
	
    @Override
	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,
			Boolean channelEnable) {
		return templateSourceService.getTemplaeSourceTreeList(parentId, channelEnable);
	}
	
    @Override
	public Template channelTemplate(String tplName) {
		return templateService.channelTemplate(tplName);
	}

    @Override
	public Template channelTPLRoot() {
		return templateService.channelTPLRoot();
	}

    @Override
	public TemplateSource channelTemplateSource(String srcName) {
		return templateSourceService.channelTemplateSource(srcName);
	}

    @Override
	public TemplateSource channelSRCRoot() {
		return templateSourceService.channelSRCRoot();
	}
	
    @Override
    public void saveAppChild(Integer channelId, List<Integer> templateIds, Boolean cover){
    	templateService.saveAppChild(channelId, templateIds, cover);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
    @Override
	public void forceRelease(Integer channelId, Boolean children) throws PublishException{
		channelService.forceRelease(channelId, children);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
	@Override
	public void downChannel(Integer channelId, Integer parentId) {
		channelService.downChannel(channelId, parentId);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
	@Override
	public void upChannel(Integer channelId, Integer parentId) {
		channelService.upChannel(channelId, parentId);
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
	@Override
	public void moveToChannel(Integer channel, Integer parentId) {
    	channelService.moveToChannel(channel, parentId);
	}
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'com.ewcms.core.site.model.Channel','ADMIN')")
	@Override
    public void moveSortChannel(Integer channelId, Integer parentId, Long sort){
    	channelService.moveSortChannel(channelId, parentId, sort);
    }

	@Override
	public List<Channel> getChannelChildren(Integer parentId) {
		return channelService.getChannelChildren(parentId);
	}
	
	public void exportChannelZip(Integer channelId, ZipOutputStream zos, String channelPath){
		channelService.exportChannelZip(channelId, zos, channelPath);
	}

	@Override
	public void exportTemplateZip(Integer templateId, ZipOutputStream zos, String templatePath) {
		templateService.exportTemplateZip(templateId, zos, templatePath);
	}

	@Override
	public void exportTemplateSourceZip(Integer templateSourceId, ZipOutputStream zos, String templateSourcePath) {
		templateSourceService.exportTemplateSourceZip(templateSourceId, zos, templateSourcePath);
	}

}