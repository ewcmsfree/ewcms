/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.online.service;

import java.util.List;

import com.ewcms.plugin.online.model.WorkingBody;
import com.ewcms.web.vo.TreeNode;

/**
 *
 * @author 吴智俊
 */
public interface WorkingBodyServiceable {
	/**
	 * 新增在线办事主体信息
	 * 
	 * @param parentId 在线办事所属父编号
	 * @param name 办事名
	 * @param channelId 频道编号
	 * @return 办事主体编号
	 */
	public Integer addWorkingBody(Integer parentId, String name, Integer channelId);

	/**
	 * 重命名在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param name 在线办事主体名
	 * @param channelId 频道编号
	 * @return 在线办事主体编号
	 */
	public Integer renameWorkingBody(Integer workingBodyId, String name, Integer channelId);

	/**
	 * 查询在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 * @return 在线办事主体信息
	 */
	public WorkingBody getWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 删除在线办事主体信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void delWorkingBody(Integer workingBodyId, Integer channelId);

	/**
	 * 添加事项基本信息到在线办事主体信息内
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param matterIds 事项基本信息编号集合
	 * @param channelId 频道编号
	 */
	public List<Integer> addMatterToWorkingBody(Integer workingBodyId, List<Integer> matterIds, Integer channelId);
	
	/**
	 * 从在线办事主体信息中移事项基本信息
	 * 
	 * @param workingBodyId 在线办事主体编号
	 * @param matterId 事项基本信息编号
	 * @param channelId 频道编号
	 */
	public void removeMatterFromWorkingBody(Integer workingBodyId, Integer matterId, Integer channelId);

	/**
	 * 在线办事主体信息向上移动一位
	 * 
	 * @param parentId 父在线办事主体编号
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void upWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId);

	/**
	 * 在线办事主体信息向下移动一位
	 * 
	 * @param parentId 父在线办事主体编号
	 * @param workingBodyId 在线办事主体编号
	 * @param channelId 频道编号
	 */
	public void downWorkingBody(Integer parentId, Integer workingBodyId, Integer channelId);
	
	/**
	 * 获得窗树型
	 * 
	 * @param channelId 频道编号 
	 * @return TreeNode对象
	 */
	public TreeNode getWorkingBodyWindowTree(Integer channelId, Boolean isMatter);	

	/**
	 * 新增根节点
	 * 
	 * @param channelId
	 */
	public void addWorkingBodyRoot(Integer channelId);
	
	/**
	 * 关联组织机构到网上办事主体
	 * 
	 * @param workingBodyId 网上办事主体编号
	 * @param organIds 组织机构编号集合
	 * @param String 网上办事名称
	 */
	public String addOrganToWorkingBody(Integer workingBodyId, List<Integer> organIds);
	
	/**
	 * 从网上办事主体中移除关联组织机构
	 * 
	 * @param workingBodyId 网上办事主体编号
	 * @param String 网上办事名称
	 */
	public String removeOrganFromWorkingBody(Integer workingBodyId);
}
