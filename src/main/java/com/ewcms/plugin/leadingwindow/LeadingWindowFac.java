/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.plugin.leadingwindow.model.Position;
import com.ewcms.plugin.leadingwindow.service.LeaderChannelServiceable;
import com.ewcms.plugin.leadingwindow.service.LeaderServiceable;
import com.ewcms.plugin.leadingwindow.service.PositionServiceable;
import com.ewcms.web.util.TreeNode;

/**
 * 
 * @author 吴智俊
 */
@Service()
public class LeadingWindowFac implements LeadingWindowFacable {

	@Autowired
	private PositionServiceable positionService;
	@Autowired
	private LeaderServiceable leaderService;
	@Autowired
	private LeaderChannelServiceable leaderChannelService;

	@Override
	public Integer addLeaderChannel(Integer leaderId,	LeaderChannel leaderChannel, Integer channelId) {
		return leaderChannelService.addLeaderChannel(leaderId,
				leaderChannel, channelId);
	}

	@Override
	public Integer addLeader(Leader leader, Integer channelId) {
		return leaderService.addLeader(leader, channelId);
	}

	@Override
	public void addLeaderChannelArticleRmc(Integer[] articleRmcIds, Integer leaderChannelId, Integer channelId) {
		leaderChannelService.addLeaderChannelArticleRmc(articleRmcIds,
				leaderChannelId, channelId);
	}

	@Override
	public void addLeaderToPosition(Integer positionId,	List<Integer> leaderIds, Integer channelId) {
		positionService.addLeaderToPosition(positionId, leaderIds, channelId);
	}

	@Override
	public Integer addPosition(Integer parentId, String name, Integer channelId) {
		return positionService.addPosition(parentId, name, channelId);
	}

	@Override
	public void delLeader(Integer leaderId, Integer channelId) {
		leaderService.delLeader(leaderId, channelId);
	}

	@Override
	public void delLeaderChannel(Integer leaderId, Integer leaderChannelId,	Integer channelId) {
		leaderChannelService.delLeaderChannel(leaderId, leaderChannelId,
				channelId);
	}

	@Override
	public void delLeaderChannelArticleRmc(Integer[] articleRmcIds,Integer leaderChannelId, Integer channelId) {
		leaderChannelService.delLeaderChannelArticleRmc(articleRmcIds, leaderChannelId, channelId);
	}

	@Override
	public void delPosition(Integer positionId, Integer channelId) {
		positionService.delPosition(positionId, channelId);
	}

	@Override
	public void downLeader(Integer leaderId, Integer channelId) {
		leaderService.downLeader(leaderId, channelId);
	}

	@Override
	public void downLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId) {
		leaderChannelService.downLeaderChannel(leaderId, leaderChannelId, channelId);
	}

	@Override
	public void downPosition(Integer parentId, Integer positionId, Integer channelId) {
		positionService.downPosition(parentId, positionId, channelId);
	}

	@Override
	public Position getPosition(Integer positionId, Integer channelId) {
		return positionService.getPosition(positionId, channelId);
	}

	@Override
	public List<ArticleRmc> getArticleRmcByLeaderChannelIdAndChannelId(Integer leaderChannelId, Integer channelId) {
		return leaderChannelService.getArticleRmcByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
	}

	@Override
	public Leader getLeader(Integer leaderId, Integer channelId) {
		return leaderService.getLeader(leaderId, channelId);
	}

	@Override
	public List<Leader> getLeaderAllOrderBySort(Integer channelId) {
		return leaderService.getLeaderAllOrderBySort(channelId);
	}

	@Override
	public LeaderChannel getLeaderChannel(Integer leaderChannelId, Integer channelId) {
		return leaderChannelService.getLeaderChannel(leaderChannelId, channelId);
	}

	@Override
	public TreeNode getLeadingWindowTree(Integer channelId) {
		return positionService.getLeadingWindowTree(channelId);
	}

	@Override
	public void removeLeaderFromPosition(Integer positionId, Integer leaderId, Integer channelId) {
		positionService.removeLeaderFromPosition(positionId, leaderId, channelId);
	}

	@Override
	public Integer renamePosition(Integer positionId, String name, Integer channelId) {
		return positionService.renamePosition(positionId, name, channelId);
	}

	@Override
	public void upLeader(Integer leaderId, Integer channelId) {
		leaderService.upLeader(leaderId, channelId);
	}

	@Override
	public void upLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId) {
		leaderChannelService.upLeaderChannel(leaderId, leaderChannelId, channelId);
	}

	@Override
	public void upPosition(Integer parentId, Integer positionId, Integer channelId) {
		positionService.upPosition(parentId, positionId, channelId);
	}

	@Override
	public Integer updLeader(Leader leader, Integer channelId) {
		return leaderService.updLeader(leader, channelId);
	}
	
	public Integer updLeaderChannel(Integer leaderId, LeaderChannel leaderChannel, Integer channelId){
		return leaderChannelService.updLeaderChannel(leaderId, leaderChannel, channelId);
	}
	
	public void addPositionRoot(Integer channelId){
		positionService.addPositionRoot(channelId);
	}

	@Override
	public void addArticleRmcToLeaderChannel(Integer articleRmcId, Integer[] leaderChannelIds) {
		leaderChannelService.addArticleRmcToLeaderChannel(articleRmcId, leaderChannelIds);
	}

	@Override
	public void pubPosition(Integer channelId) throws ReleaseException {
		positionService.pubPosition(channelId);
	}
}
