/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.List;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.service.SitePublishServiceable;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 *
 */
public interface SiteServiceable extends SitePublishServiceable {
	public Integer addSite(Integer parentId, String siteName, Integer organId);
	
	public Integer saveSiteServer(Site vo);
	
	public void delSiteBatch(List<Integer> idList);

	public Integer updSite(Site vo);

	public void delSite(Integer id);

	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable);
	public void updSiteParent(Integer organId, Integer parentId,
			Integer newParentId);
	
	/**
	 * 获取机构跟站点集
	 * 
	 */ 	
	public List<TreeNode> getOrganSiteTreeList(Integer organId);
	
	/**
	 * 获取机构站点子站点集
	 * 
	 */ 	
	public List<TreeNode> getOrganSiteTreeList(Integer parentId,Integer organId);
	
	/**
	 * 获取客户跟站点集
	 * 
	 */ 	
	public List<TreeNode> getCustomerSiteTreeList();
	
	/**
	 * 获取客户站点子站点集
	 * 
	 */ 	
	public List<TreeNode> getCustomerSiteTreeList(Integer parentId);	
	
	/**
	 * 获取子站�?
	 */
	public List<TreeNode> getSiteChildren(Integer parentId, Integer organId);
}
