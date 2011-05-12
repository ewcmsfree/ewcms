/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.service;

import java.util.List;

import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;

/**
 *
 * @author 吴智俊
 */
public interface LeaderChannelServiceable {
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
	 * @param articleRmcIds 文章主体编号集合
	 * @param leaderChannelId 领导频道编号 
	 * @param channelId 频道编号
	 */
	public void delLeaderChannelArticleRmc(Integer[] articleRmcIds,Integer leaderChannelId, Integer channelId);
	
	public void addArticleRmcToLeaderChannel(Integer articleRmcId, Integer[] leaderChannelIds);
}
