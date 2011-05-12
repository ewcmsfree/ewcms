/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.leadingwindow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.document.dao.ArticleRmcDAO;
import com.ewcms.core.document.model.ArticleRmc;
import com.ewcms.plugin.leadingwindow.dao.LeaderChannelDAO;
import com.ewcms.plugin.leadingwindow.dao.LeaderDAO;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;

/**
 *
 * @author 吴智俊
 */
@Service()
public class LeaderChannelService implements LeaderChannelServiceable {
	
	@Autowired
	private LeaderDAO leaderDAO;
	@Autowired
	private LeaderChannelDAO leaderChannelDAO;
	@Autowired
	private ArticleRmcDAO articleRmcDAO;
	
	@Override
	public Integer addLeaderChannel(Integer leaderId, LeaderChannel leaderChannel, Integer channelId){
		Leader leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		if (leader == null) return null;
		String leaderChannelName = leaderChannel.getName();
		if (leaderChannelName == null || leaderChannelName.length() == 0) return null;
		Boolean uiqueness = leaderChannelDAO.findByName(leaderId, leaderChannelName, channelId);
		if (!uiqueness) return null;
		
		Integer sort = leaderChannelDAO.findLeaderChannelMaxSort(leaderId, channelId) + 1;
		leaderChannel.setSort(sort);
		leaderChannel.setChannelId(channelId);
		
		List<LeaderChannel> leaderChannels = leader.getLeaderChannels();
		if (leaderChannels == null) {
			leaderChannels = new ArrayList<LeaderChannel>();
		}
		leaderChannels.add(leaderChannel);
		leader.setLeaderChannels(leaderChannels);
		leaderDAO.merge(leader);
		
		return leaderChannel.getId();
	}
	
	@Override
	public Integer updLeaderChannel(Integer leaderId, LeaderChannel leaderChannel, Integer channelId){
		Leader leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		if (leader == null) return null;
		String leaderChannelName = leaderChannel.getName();
		if (leaderChannelName == null || leaderChannelName.length() == 0) return null;
		Boolean uiqueness = leaderChannelDAO.findByName(leaderId, leaderChannelName, channelId);
		if (!uiqueness) return null;
		
		leaderChannel.setChannelId(channelId);
		
		List<LeaderChannel> leaderChannels = leader.getLeaderChannels();
		if (leaderChannels == null) {
			leaderChannels = new ArrayList<LeaderChannel>();
		}
		leaderChannels.add(leaderChannel);
		leader.setLeaderChannels(leaderChannels);
		leaderDAO.merge(leader);
		
		return leaderChannel.getId();
		
	}
	
	@Override
	public void upLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId){
		if (leaderChannelId == null) return;
		LeaderChannel leaderChannel = leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
		if (leaderChannel == null) return;
		Integer sort = leaderChannel.getSort();
		if (sort == null || sort.intValue() <= 1) return;
		LeaderChannel leaderChannel_prv = leaderChannelDAO.findLeaderChannelBySort(leaderId, sort - 1, channelId);
		if (leaderChannel_prv == null) return;
		leaderChannel.setSort(sort - 1);
		leaderChannelDAO.merge(leaderChannel);
		leaderChannel_prv.setSort(sort);
		leaderChannelDAO.merge(leaderChannel_prv);
	}
	
	@Override
	public void downLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId){
		if (leaderChannelId == null) return;
		LeaderChannel leaderChannel = leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
		if (leaderChannel == null) return;
		Integer sort = leaderChannel.getSort();
		Integer maxSort = leaderChannelDAO.findLeaderChannelMaxSort(leaderId, channelId);
		if (sort == null || sort.intValue() >= maxSort.intValue()) return;
		LeaderChannel leaderChannel_next = leaderChannelDAO.findLeaderChannelBySort(leaderId, sort + 1, channelId);
		if (leaderChannel_next == null) return;
		leaderChannel.setSort(sort + 1);
		leaderChannelDAO.merge(leaderChannel);
		leaderChannel_next.setSort(sort);
		leaderChannelDAO.merge(leaderChannel_next);
	}
	
	@Override
	public void delLeaderChannel(Integer leaderId, Integer leaderChannelId, Integer channelId){
		if (leaderId == null) return;
		if (leaderChannelId == null) return;
		LeaderChannel leaderChannel = leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
		if (leaderChannel == null) return;
		leaderChannelDAO.remove(leaderChannel);
		
		List<LeaderChannel> leaderChannels = leaderChannelDAO.findLeaderChannelByLeaderIdOrderBySort(leaderId, channelId);
		Integer sort = 1;
		for (LeaderChannel leaderChannelSort : leaderChannels){
			leaderChannelSort.setSort(sort);
			sort++;
			leaderChannelDAO.merge(leaderChannelSort);
		}
	}

	@Override
	public LeaderChannel getLeaderChannel(Integer leaderChannelId, Integer channelId) {
		return leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
	}
	
	@Override
	public List<ArticleRmc> getArticleRmcByLeaderChannelIdAndChannelId(Integer leaderChannelId, Integer channelId){
		return leaderChannelDAO.findArticleRmcByLeaderChannelId(leaderChannelId, channelId);
	}
	
	@Override
	public void addLeaderChannelArticleRmc(Integer[] articleRmcIds, Integer leaderChannelId, Integer channelId){
		if (articleRmcIds == null || articleRmcIds.length == 0) return;
		LeaderChannel leaderChannel = leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
		if (leaderChannel == null) return;
		List<ArticleRmc> leaderChannel_articleRmcs = leaderChannel.getArticleRmcs();
		if (leaderChannel_articleRmcs == null || leaderChannel_articleRmcs.isEmpty()) leaderChannel_articleRmcs = new ArrayList<ArticleRmc>();
		for (Integer articleRmcId : articleRmcIds){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			if (articleRmc == null) continue;
//			ArticleRmc refArticleRmc = new ArticleRmc();
//			
//			refArticleRmc.setArticle(articleRmc.getArticle());
//			refArticleRmc.setChannel(null);
//			refArticleRmc.setDeleteFlag(false);
//			refArticleRmc.setDeleteTime(null);
//			refArticleRmc.setPublished(articleRmc.getPublished());
//			refArticleRmc.setRecommends(articleRmc.getRecommends());
//			refArticleRmc.setRefArticleRmc(articleRmc);
//			refArticleRmc.setRelateds(articleRmc.getRelateds());
//			refArticleRmc.setStatus(articleRmc.getStatus());
//			refArticleRmc.setUrl(null);
			
			leaderChannel_articleRmcs.add(articleRmc);
		}
		leaderChannel.setArticleRmcs(leaderChannel_articleRmcs);
		leaderChannelDAO.merge(leaderChannel);
	}

	@Override
	public void delLeaderChannelArticleRmc(Integer[] articleRmcIds,Integer leaderChannelId, Integer channelId) {
		LeaderChannel leaderChannel = leaderChannelDAO.findLeaderChannelByLeaderChannelIdAndChannelId(leaderChannelId, channelId);
		if (leaderChannel == null) return;
		List<ArticleRmc> articleRmcs = leaderChannel.getArticleRmcs();
		if (articleRmcs == null || articleRmcs.isEmpty()) return;
		for (Integer articleRmcId : articleRmcIds){
			ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
			articleRmcs.remove(articleRmc);
		}
		leaderChannel.setArticleRmcs(articleRmcs);
		leaderChannelDAO.merge(leaderChannel);
	}
	
	@Override
	public void addArticleRmcToLeaderChannel(Integer articleRmcId, Integer[] leaderChannelIds){
		if (articleRmcId == null) return;
		ArticleRmc articleRmc = articleRmcDAO.get(articleRmcId);
		LeaderChannel leaderChannel = null;
		for (Integer leaderChannelId : leaderChannelIds){
			leaderChannel = leaderChannelDAO.get(leaderChannelId);
			List<ArticleRmc> articleRmcs = leaderChannel.getArticleRmcs();
			if (articleRmcs == null || articleRmcs.isEmpty()) articleRmcs = new ArrayList<ArticleRmc>();
			articleRmcs.add(articleRmc);
			leaderChannel.setArticleRmcs(articleRmcs);
			leaderChannelDAO.merge(leaderChannel);
		}
	}
}
