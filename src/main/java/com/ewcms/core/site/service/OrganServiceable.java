/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site.service;

import java.util.List;

import com.ewcms.core.site.model.Organ;
import com.ewcms.web.vo.TreeNode;

/**
 * @author wuzhijun
 */
public interface OrganServiceable {
	
	public Integer addOrgan(Integer parentId, String organName);

	public Integer updOrgan(Organ vo);

	public void delOrgan(Integer id);

	public Organ getOrgan(Integer id);

	public Integer saveOrganInfo(Organ vo);

	/**
	 * 获取跟机构集
	 * 
	 */
	public List<TreeNode> getOrganTreeList();

	/**
	 * 获取机构子机构集
	 * 
	 */
	public List<TreeNode> getOrganTreeList(Integer parentId);
}
