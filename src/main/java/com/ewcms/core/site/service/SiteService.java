/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.SiteDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.vo.TreeNode;

/**
 * @author 周冬初
 * 
 */
@Service
public class SiteService {

	@Autowired
	private SiteDAO siteDao;

	public Integer addSite(Integer parentId, String siteName, Integer organId) {
		Site vo = new Site();
		vo.setSiteName(siteName);
		if(parentId!=null)
		vo.setParent(siteDao.get(parentId));
		Organ organVo = new Organ();
		organVo.setId(organId);
		vo.setOrgan(organVo);
		siteDao.persist(vo);
		return vo.getId();
	}

	public void delSiteBatch(List<Integer> idList) {
		for (Integer id : idList) {
			delSite(id);
		}
	}

	public Integer updSite(Site vo) {
		siteDao.merge(vo);
		return vo.getId();
	}

	public void delSite(Integer id) {
		siteDao.removeByPK(id);
	}

	public Site getSite(Integer id) {
		return siteDao.get(id);
	}

	public List<Site> getSiteListByOrgans(Integer[] organs, Boolean publicenable) {
		return siteDao.getSiteListByOrgans(organs, publicenable);
	}

	public void updSiteParent(Integer organId, Integer parentId,
			Integer newParentId) {
		siteDao.updSiteParent(organId, parentId, newParentId);
	}
	
	/**
	 * 获取机构跟站点集
	 * 
	 */ 	
	public List<TreeNode> getOrganSiteTreeList(Integer organId){
		return getSiteChildren(null,organId);
	}
	
	/**
	 * 获取机构站点子站点集
	 * 
	 */ 	
	public List<TreeNode> getOrganSiteTreeList(Integer parentId,Integer organId){
		return getSiteChildren(parentId,organId);
	}
	
	/**
	 * 获取客户跟站点集
	 * 
	 */ 	
	public List<TreeNode> getCustomerSiteTreeList(){
		return getSiteChildren(null,null);
	}
	
	/**
	 * 获取客户站点子站点集
	 * 
	 */ 	
	public List<TreeNode> getCustomerSiteTreeList(Integer parentId){
		return getSiteChildren(parentId,null);
	}	
	
	/**
	 * 获取子站点.
	 */
	public List<TreeNode> getSiteChildren(Integer parentId, Integer organId) {
		List<TreeNode> tnList = new ArrayList<TreeNode>();
		List<Site> siteList = siteDao.getSiteChildren(parentId, organId);
		for (Site vo : siteList) {
			TreeNode tnVo = new TreeNode();
			tnVo.setId(vo.getId().toString());
			tnVo.setText(vo.getSiteName());

			if (vo.hasChildren()) {
				tnVo.setState("closed");
			} else {
				tnVo.setState("open");
			}
			tnList.add(tnVo);
		}
		return tnList;
	}

}
