/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow;

import java.util.List;

import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.plugin.leadingwindow.model.Position;
import com.ewcms.web.util.TreeNode;

/**
 * 领导之窗接口
 *
 * @author 吴智俊
 */
public interface LeadingWindowFacable {
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
	 * 新增领导信息
	 * 
	 * @param leader 领导信息
	 * @param channelId 频道编号
	 * @return 领导编号
	 */
	public Integer addLeader(Leader leader, Integer channelId);
	
	/**
	 * 修改领导信息
	 * 
	 * @param leader 领导信息
	 * @param channelId 频道编号
	 * @return 领导编号
	 */
	public Integer updLeader(Leader leader, Integer channelId);
	
	/**
	 * 查询领导信息
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 * @return 领导信息
	 */
	public Leader getLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 删除领导信息
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void delLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 领导信息向上移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void upLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 领导信息向下移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param channelId 频道编号
	 */
	public void downLeader(Integer leaderId, Integer channelId);
	
	/**
	 * 获得所有排序的领导信息集合(sort)
	 * 
	 * @param channelId 频道编号
	 * @return 领导信息集合
	 */
	public List<Leader> getLeaderAllOrderBySort(Integer channelId);
	
	/**
	 * 新增领导频道信息
	 * 
	 * @param leaderId 领导编号
	 * @param leaderChannel 领导频道信息
	 * @param channelId 频道编号
	 * @return 领导频道编号
	 */
	public Integer addLeaderChannel(Integer leaderId, LeaderChannel leaderChannel, Integer channelId);

	/**
	 * 修改领导频道信息
	 * 
	 * @param leaderId 领导编号
	 * @param leaderChannel 领导频道信息
	 * @param channelId 频道编号
	 * @return 领导频道编号
	 */
	public Integer updLeaderChannel(Integer leaderId, LeaderChannel leaderChannel, Integer channelId);
	
	/**
	 * 领导频道信息向上移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 */
	public void upLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId);

	/**
	 * 领导频道信息向下移动一位
	 * 
	 * @param leaderId 领导编号
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 */
	public void downLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId);

	/**
	 * 删除领导频道信息
	 * 
	 * @param leaderId 领导编号
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 */
	public void delLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId);
	
	/**
	 * 获得领导频道信息
	 * 
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 * @return 领导频道信息
	 */
	public LeaderChannel getLeaderChannel(Integer leaderChannelId, Integer channelId);
	
	/**
	 * 获是文章集合
	 * 
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 * @return 文章集合
	 */
	public List<ArticleRmc> getArticleRmcByLeaderChannelIdAndChannelId(Integer leaderChannelId, Integer channelId);
	
	/**
	 * 新增文章
	 * 
	 * @param articleRmcIds 文章编号集合
	 * @param leaderChannelId 领导频道编号
	 * @param channelId 频道编号
	 * @return 文章编号
	 */
	public void addLeaderChannelArticleRmc(Integer[] articleRmcIds, Integer leaderChannelId, Integer channelId);

	/**
	 * 删除文章
	 *  
	 * @param articleRmcId 文章主体编号集合
	 * @param leaderChannelId 领导频道编号 
	 * @param channelId 频道编号
	 */
	public void delLeaderChannelArticleRmc(Integer[] articleRmcIds,Integer leaderChannelId, Integer channelId);

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
	
	public void addArticleRmcToLeaderChannel(Integer articleRmcId, Integer[] leaderChannelIds);
	
	/**
	 * 发布领导之窗
	 * @param channelId
	 * @throws ReleaseException
	 */
	public void pubPosition(Integer channelId) throws ReleaseException;
}
