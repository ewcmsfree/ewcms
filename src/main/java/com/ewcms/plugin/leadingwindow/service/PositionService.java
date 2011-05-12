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

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.plugin.leadingwindow.dao.LeaderDAO;
import com.ewcms.plugin.leadingwindow.dao.PositionDAO;
import com.ewcms.plugin.leadingwindow.generator.LeadingReleaseable;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.plugin.leadingwindow.model.Position;
import com.ewcms.web.util.TreeNode;

/**
 *
 * @author 吴智俊
 */
@Service()
public class PositionService implements PositionServiceable {
	@Autowired
	private PositionDAO positionDAO;
	@Autowired
	private LeaderDAO leaderDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private LeadingReleaseable leadingRelease;

	
	@Override
	public Integer addPosition(Integer parentId, String name, Integer channelId){
		Position position_parent = positionDAO.findPositionByPositionIdAndChannelId(parentId, channelId);
		if (position_parent == null) return null;
		Position position = new Position();
		position.setName(name);
		position.setParent(position_parent);
		Integer sort = positionDAO.findPositionMaxSortByPositionId(parentId, channelId) + 1;
		position.setSort(sort);
		position.setChannelId(channelId);
		positionDAO.persist(position);
		return position.getId();
	}
	
	@Override
	public Integer renamePosition(Integer positionId, String name, Integer channelId){
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		position.setName(name);
		positionDAO.merge(position);
		return position.getId();
	}
	
	@Override
	public Position getPosition(Integer positionId, Integer channelId){
		return positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
	}
	
	@Override
	public void delPosition(Integer positionId, Integer channelId){
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		if (position != null) positionDAO.remove(position);
	}
	
	@Override
	public void addLeaderToPosition(Integer positionId, List<Integer> leaderIds, Integer channelId){
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		if (position == null) return;
		List<Leader> leaders = position.getLeaders();
		
		for (Integer leaderId : leaderIds){
			Leader leader = positionDAO.findLeaderByLeaderIdAndPositionId(leaderId, positionId, channelId);
			if (leader == null){
				leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
				if (leader == null) continue;
				leaders.add(leader);
			}
		}
		
		position.setLeaders(leaders);
		positionDAO.merge(position);
	}
	
	@Override
	public void removeLeaderFromPosition(Integer positionId, Integer leaderId, Integer channelId){
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		if (position == null) return;
		Leader leader = leaderDAO.findLeaderByLeaderIdAndChannelId(leaderId, channelId);
		if (leader == null) return;
		List<Leader> leaders = position.getLeaders();
		if (leaders.isEmpty()) return;
		leaders.remove(leader);
		position.setLeaders(leaders);
		positionDAO.merge(position);
	}
	
	@Override
	public void upPosition(Integer parentId, Integer positionId, Integer channelId){
		if (positionId == null) return;
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		if (position == null) return;
		Integer sort = position.getSort();
		if (sort == null || sort.intValue() <= 1) return;
		Position position_prv = positionDAO.findPositionBySort(parentId, sort - 1, channelId);
		if (position_prv == null) return;
		position.setSort(sort - 1);
		positionDAO.merge(position);
		position_prv.setSort(sort);
		positionDAO.merge(position_prv);
	}
	
	@Override
	public void downPosition(Integer parentId, Integer positionId, Integer channelId){
		if (positionId == null) return;
		Position position = positionDAO.findPositionByPositionIdAndChannelId(positionId, channelId);
		if (position == null) return;
		Integer sort = position.getSort();
		Integer maxSort = positionDAO.findPositionMaxSort(channelId);
		if (sort == null || sort.intValue() >= maxSort.intValue()) return;
		Position position_next = positionDAO.findPositionBySort(parentId, sort + 1, channelId);
		if (position_next == null) return;
		position.setSort(sort + 1);
		positionDAO.merge(position);
		position_next.setSort(sort);
		positionDAO.merge(position_next);
	}

	@Override
	public TreeNode getLeadingWindowTree(Integer channelId) {
		if (channelId == null) return null;
		Position position = positionDAO.findPositionParent(channelId);
		if (position == null) return null;
		TreeNode treeNode = new TreeNode();
		convertMenuNode(treeNode, position, channelId);
		return treeNode;
	}
	
    private void convertMenuNode(TreeNode treeNode, Position position, Integer channelId) {
        treeNode.setId(position.getId().toString());
        treeNode.setChecked(false);

        treeNode.setText(position.getName());
        treeNode.getAttributes().put("type", "position");
        treeNode.setState("closed");
        if (position.getParent() == null){
        	treeNode.getAttributes().put("type", "component");
        	treeNode.setState("open");
        }
        if (treeNode.getChildren() == null) {
            treeNode.setChildren(new ArrayList<TreeNode>());
        }
        List<Leader> leaders = positionDAO.findLeadersByPositionId(position.getId(),channelId);
        if (leaders != null && !leaders.isEmpty()){
	        for (Leader leader : leaders){
	        	TreeNode child = new TreeNode();
	        	covertLeaderNode(child,leader);
	        	if (child.getChildren() == null){
	        		child.setChildren(new ArrayList<TreeNode>());
	        	}
	        	List<LeaderChannel> leaderChannels = leaderDAO.findLeaderChannelByLeaderId(leader.getId(), channelId);
	        	if (leaderChannels != null && !leaderChannels.isEmpty()){
	        		for (LeaderChannel leaderChannel : leaderChannels){
	        			TreeNode child_channel = new TreeNode();
	        			coverLeaderChannelNode(child_channel, leaderChannel);
	        			child.getChildren().add(child_channel);
	        		}
	        	}
	        	treeNode.getChildren().add(child);
	        }
        }
        if (position.getChildren() != null && !position.getChildren().isEmpty()){
	        for (int i = 0; i < position.getChildren().size(); i++) {
	            TreeNode child = new TreeNode();
	            treeNode.getChildren().add(child);
	            convertMenuNode(child, position.getChildren().get(i), channelId);
	        }
        }
    }
	
    private void covertLeaderNode(TreeNode treeNode, Leader leader){
    	treeNode.setId(leader.getId().toString());
    	treeNode.setChecked(false);
    	treeNode.setState("closed");
    	treeNode.setText(leader.getName());
    	treeNode.getAttributes().put("type", "leader");
    }
    
    private void coverLeaderChannelNode(TreeNode treeNode, LeaderChannel leaderChannel){
    	treeNode.setId(leaderChannel.getId().toString());
    	treeNode.setChecked(false);
    	treeNode.setState("open");
    	treeNode.setText(leaderChannel.getName());
    	treeNode.getAttributes().put("type", "leaderchannel");
    }
    
    public void addPositionRoot(Integer channelId){
    	if (channelId == null) return;	
		Position position = positionDAO.findPositionParent(channelId);
		if (position == null){
			Channel channel = channelDAO.get(channelId);
			if (channel == null) return;
			position = new Position();
			position.setChannelId(channelId);
			position.setName(channel.getName());
			positionDAO.persist(position);
			
		}
    }
    
    public void pubPosition(Integer channelId) throws ReleaseException{
    	leadingRelease.release(channelId);
    }
}