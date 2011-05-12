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

import com.ewcms.plugin.leadingwindow.dao.LeaderDAO;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;

/**
 *
 * @author 吴智俊
 */
@Service()
public class LeaderService implements LeaderServiceable {

	@Autowired
	private LeaderDAO leaderDAO;
	
	@Override
	public Integer addLeader(Leader leader, Integer channelId) {
		Leader isLeader = leaderDAO.findLeaderByLeaderNameAndChannelId(leader.getName(), channelId);
		if (isLeader != null) return null;
		Integer max_sort = leaderDAO.findLeaderMaxSort(channelId) + 1;
		leader.setChannelId(channelId);
		leader.setSort(max_sort);
		
		List<LeaderChannel> leaderChannels = new ArrayList<LeaderChannel>();
		LeaderChannel leaderChannel = new LeaderChannel();
		leaderChannel.setChannelId(channelId);
		leaderChannel.setName("言论");
		leaderChannel.setSort(1);
		leaderChannels.add(leaderChannel);
		
		leaderChannel = new LeaderChannel();
		leaderChannel.setChannelId(channelId);
		leaderChannel.setName("活动");
		leaderChannel.setSort(2);
		leaderChannels.add(leaderChannel);
		
		leader.setLeaderChannels(leaderChannels);
		
		leaderDAO.persist(leader);
		return leader.getId();
	}
	
	@Override
	public void delLeader(Integer leaderId, Integer channelId){
		Leader leaderVo = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		Integer sort = leaderVo.getSort();
		List<Leader> leaderVos = leaderDAO.findAll();
		Integer size = leaderVos.size();
		
		leaderDAO.removeByPK(leaderId);
		
		if (sort.intValue() != size.intValue()){
			sort = 1;
			leaderVos = leaderDAO.findLeaderAllOrderBySort(channelId);
			for (Leader leader : leaderVos){
				leader.setSort(sort);
				sort++;
				leaderDAO.merge(leader);
			}
		}
	}
	
	@Override
	public Leader getLeader(Integer leaderId, Integer channelId) {
		return leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
	}

	@Override
	public Integer updLeader(Leader leader, Integer channelId) {
		Leader isLeader = leaderDAO.findLeaderByLeaderIdAndLeaderNameAndChannelId(leader.getId(), leader.getName(), channelId);
		if (isLeader != null) return null;
		if (leader.getChannelId().intValue() != channelId.intValue()) return null;
		Leader leader_old = leaderDAO.findLeaderByLeaderIdAndChannelId(leader.getId(), channelId);
		if (leader_old == null) return null;
		
		leader_old.setChargeWork(leader.getChargeWork());
		leader_old.setContact(leader.getContact());
		leader_old.setDuties(leader.getDuties());
		leader_old.setEmail(leader.getEmail());
		leader_old.setImage(leader.getImage());
		leader_old.setMobile(leader.getMobile());
		leader_old.setName(leader.getName());
		leader_old.setOfficeAddress(leader.getOfficeAddress());
		leader_old.setResume(leader.getResume());
		
		leaderDAO.merge(leader_old);
		return leader.getId();
	}

	@Override
	public void downLeader(Integer leaderId, Integer channelId) {
		if (leaderId == null) return;
		Leader leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		if (leader == null) return;
		Integer sort = leader.getSort();
		Integer maxSort = leaderDAO.findLeaderMaxSort(channelId);
		if (sort == null || sort.intValue() >= maxSort.intValue()) return;
		Leader leader_next = leaderDAO.findLeaderBySort(sort + 1, channelId);
		if (leader_next == null) return;
		leader.setSort(sort + 1);
		leaderDAO.merge(leader);
		leader_next.setSort(sort);
		leaderDAO.merge(leader_next);
	}

	@Override
	public void upLeader(Integer leaderId, Integer channelId) {
		if (leaderId == null) return;
		Leader leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		if (leader == null) return;
		Integer sort = leader.getSort();
		if (sort == null || sort.intValue() <= 1) return;
		Leader leader_prv = leaderDAO.findLeaderBySort(sort - 1, channelId);
		if (leader_prv == null) return;
		leader.setSort(sort - 1);
		leaderDAO.merge(leader);
		leader_prv.setSort(sort);
		leaderDAO.merge(leader_prv);
	}
	
	@Override
	public List<Leader> getLeaderAllOrderBySort(Integer channelId) {
		return leaderDAO.findLeaderAllOrderBySort(channelId);
	}
}
