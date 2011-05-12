/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.service;

import java.util.List;

import com.ewcms.generator.release.ReleaseException;
import com.ewcms.plugin.leadingwindow.model.Position;
import com.ewcms.web.util.TreeNode;

/**
 * 职务服务接口
 *
 * @author 吴智俊
 */
public interface PositionServiceable {
	/**
	 * 新增职务信息
	 * 
	 * @param parentId 职务所属父编号
	 * @param name 职务名
	 * @param channelId 频道编号
	 * @return 职务编号
	 */
	public Integer addPosition(Integer parentId, String name, Integer channelId);

	/**
	 * 重命名职务信息
	 * 
	 * @param positionId 职务编号
	 * @param name 职务名
	 * @param channelId 频道编号
	 * @return 职务编号
	 */
	public Integer renamePosition(Integer positionId, String name, Integer channelId);

	/**
	 * 查询职务信息
	 * 
	 * @param positionId 职务编号
	 * @param channelId 频道编号
	 * @return 职务信息
	 */
	public Position getPosition(Integer positionId, Integer channelId);

	/**
	 * 删除职务信息
	 * 
	 * @param positionId 职务编号
	 * @param channelId 频道编号
	 */
	public void delPosition(Integer positionId, Integer channelId);

	/**
	 * 添加领导信息到职务信息内
	 * 
	 * @param positionId 职务编号
	 * @param leaderIds 领导编号集合
	 * @param channelId 频道编号
	 */
	public void addLeaderToPosition(Integer positionId, List<Integer> leaderIds, Integer channelId);
	
	/**
	 * 从职务信息中移除领导信息
	 * 
	 * @param positionId 职务编号
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void removeLeaderFromPosition(Integer positionId, Integer leaderId, Integer channelId);

	/**
	 * 职务信息向上移动一位
	 * 
	 * @param parentId 父职务编号
	 * @param positionId 职务编号
	 * @param channelId 频道编号
	 */
	public void upPosition(Integer parentId, Integer positionId, Integer channelId);

	/**
	 * 职务信息向下移动一位
	 * 
	 * @param parentId 父职务编号
	 * @param positionId 职务编号
	 * @param channelId 频道编号
	 */
	public void downPosition(Integer parentId, Integer positionId, Integer channelId);
	
	/**
	 * 获得领导之窗树型
	 * 
	 * @param channelId 频道编号 
	 * @return TreeNode对象
	 */
	public TreeNode getLeadingWindowTree(Integer channelId);	
	
	/**
	 * 新增根节点
	 * 
	 * @param channelId
	 * @return
	 */
	public void addPositionRoot(Integer channelId);
	
	/**
	 * 发布领导之窗
	 * @param channelId
	 * @throws ReleaseException
	 */
	public void pubPosition(Integer channelId) throws ReleaseException;
}
