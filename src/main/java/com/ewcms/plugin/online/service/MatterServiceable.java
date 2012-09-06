/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.service;

import java.util.List;

import com.ewcms.plugin.online.model.Matter;

/**
 *
 * @author 吴智俊
 */
public interface MatterServiceable {
	/**
	 * 新增事项基本信息
	 * 
	 * @param matter 事项基本信息
	 * @param filePaths 文件路径集合
	 * @param legends 文件说明集合
	 * @return 事项基本编号
	 */
	public Integer addMatter(Matter matter, List<String> filePaths, List<String> legends);
	
	/**
	 * 修改事项基本信息
	 * 
	 * @param matter 事项基本信息
	 * @param matterAnnexsIds 事项附件编号集合
	 * @param filePaths 文件路径集合
	 * @param legends 文件说明集合
	 * @return 事项基本编号
	 */
	public Integer updMatter(Matter matter, List<Integer> matterAnnexIds, List<String> filePaths, List<String> legends);
	
	/**
	 * 查询事项基本信息
	 * 
	 * @param matterId 事项基本编号
	 * @return 事项基本信息
	 */
	public Matter getMatter(Integer matterId);
	
	/**
	 * 删除事项基本信息
	 * 
	 * @param matterId 事项基本编号
	 */
	public void delMatter(Integer matterId);
	
	/**
	 * 事项基本信息向上移动一位
	 * 
	 * @param matterId 事项基本编号
	 */
	public void upMatter(Integer matterId);
	
	/**
	 * 事项基本信息向下移动一位
	 * 
	 * @param matterId 事项基本编号
	 */
	public void downMatter(Integer matterId);
	
	/**
	 * 获得所有排序的事项基本信息集合(sort)
	 * 
	 * @return 事项基本信息集合
	 */
	public List<Matter> getMatterAllOrderBySort();
	
	/**
	 * 关联组织机构到事项基本信息
	 * 
	 * @param matterId 事项基本信息编号
	 * @param organId 组织机构编号
	 */
	public String addOrganToMatter(Integer matterId, Integer organId);
	
	/**
	 * 从事项基本信息中移除关联组织
	 * 
	 * @param matterId 事项基本信息编号
	 */
	public String removeOrganFromMatter(Integer matterId);
	
	/**
	 * 关联公民信息到事项基本信息
	 * 
	 * @param matterId 事项基本信息编号
	 * @param citizenIds 公民编号集合
	 */
	public String addCitizenToMatter(Integer matterId, List<Integer> citizenIds);
	
	/**
	 * 从事项基本信息中移除关联公民
	 * 
	 * @param matterId 事项基本信息编号
	 */
	public String removeCitizenFromMatter(Integer matterId);
}
