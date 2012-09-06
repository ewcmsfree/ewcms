/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site;

import java.util.List;
import java.util.Set;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Permission;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.publication.PublishException;
import com.ewcms.web.vo.TreeNode;

/**
 * @author wuzhijun
 */
public interface SiteFacable {
	public Acl findAclOfChannel(final Channel channel);

	public void addOrUpdatePermission(Integer id, String name, int mask);

	public void removePermission(Integer id, String name);

	public void updateInheriting(Integer id, boolean inheriting);

	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable);

	public Set<Permission> getPermissionsById(int id);

	public List<TreeNode> getOrganSiteTreeList(Integer organId);

	public List<TreeNode> getOrganSiteTreeList(Integer parentId, Integer organId);

	public List<TreeNode> getCustomerSiteTreeList();

	public List<TreeNode> getCustomerSiteTreeList(Integer parentId);

	public Integer addSite(Integer parentId, String siteName, Integer organId);

	public void delSiteBatch(List<Integer> idList);

	public Integer updSite(Site vo);

	public void delSite(Integer id);

	public Integer saveSiteServer(Site vo);

	public Site getSite(Integer id);

	public Template getTemplate(Integer id);

	public Integer addTemplate(Template vo);

	public Integer updTemplate(Template vo);

	public void delTemplateBatch(List<Integer> idList);

	public void delTemplate(Integer id);

	public List<Template> getTemplateList();

	public List<ChannelNode> getChannelChildren(Integer parentId,
			Boolean publicenable);

	public Integer addChannel(Integer parentId, String name);

	public void renameChannel(Integer id, String name);

	public Integer updChannel(Channel vo);

	public void delChannel(Integer id);

	public Channel getChannel(Integer id);

	public Channel getChannelRoot();

	public ChannelNode channelNodeRoot();

	public List<Template> getTemplaeTreeList(Boolean channelEnable);

	public List<Template> getTemplaeTreeList(Integer parentId,
			Boolean channelEnable);

	public List<Template> getTemplaeTreeList(Integer parentId,
			String channelName);

	public void updSiteParent(Integer organId, Integer parentId,
			Integer newParentId);

	public Integer addOrgan(Integer parentId, String organName);

	public Integer updOrgan(Organ vo);

	public void delOrgan(Integer id);

	public Organ getOrgan(Integer id);

	public Integer saveOrganInfo(Organ vo);

	public List<TreeNode> getOrganTreeList();

	public List<TreeNode> getOrganTreeList(Integer parentId);

	public TemplateSource getTemplateSource(Integer id);

	public Integer addTemplateSource(TemplateSource vo);

	public Integer updTemplateSource(TemplateSource vo);

	public void delTemplateSource(Integer id);

	public List<TemplateSource> getTemplaeSourceTreeList(Boolean channelEnable);

	public List<TemplateSource> getTemplaeSourceTreeList(Integer parentId,
			Boolean channelEnable);

	public Template channelTemplate(String tplName);

	public Template channelTPLRoot();

	public TemplateSource channelTemplateSource(String srcName);

	public TemplateSource channelSRCRoot();

	public void saveAppChild(Integer channelId, List<Integer> templateIds);

	public void forceRelease(Integer channelId, Boolean children)
			throws PublishException;
	
	/**
	 * 同一级目录下的专栏下移一位
	 * 
	 * @param channelId 选中栏目编号
	 * @param parentId 选中栏目的上一级栏目编号
	 */
	public void downChannel(Integer channelId, Integer parentId);
	
	/**
	 * 同一级目录下的专栏上移一位
	 * 
	 * @param channelId 选中栏目编号
	 * @param parentId 选中栏目的上一级栏目编号
	 */
	public void upChannel(Integer channelId, Integer parentId);
	
	/**
	 * 移动专栏
	 * 
	 * @param channel
	 * @param parentId
	 */
	public void moveToChannel(Integer channel, Integer parentId);
}
