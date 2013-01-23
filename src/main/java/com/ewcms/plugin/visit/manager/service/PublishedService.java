/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.dao.OrganDAO;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Organ;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.PublishedVo;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.web.vo.TreeGridNode;

@Service
public class PublishedService implements PublishedServiceable {

	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private ChannelDAO channelDAO;
	@Autowired
	private OrganDAO organDAO;
	
	@Override
	public List<PublishedVo> findStaffReleased(String startDate, String endDate, Integer siteId, Integer channelId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		return visitDAO.findStaffReleased(start, end, siteId, channelId);
	}

	@Override
	public List<TreeGridNode> findChannelRelease(String startDate, String endDate, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		Channel rootChannel = channelDAO.getChannelRoot(siteId);
		List<Channel> channels = channelDAO.getChannelChildren(rootChannel.getId());
		
		List<TreeGridNode> nodes = new ArrayList<TreeGridNode>();
		
		for (Channel channel : channels){
			PublishedVo vo = visitDAO.findChannelReleased(start, end, channel.getId(), siteId);
			if (vo == null) continue;
			
			TreeGridNode node = new TreeGridNode();
			node.setId(channel.getId());
			node.setText(channel.getName());
			node.setState("open");
			findChannelType(channel, node);
			node.setData(vo);
			
			findTreeGridNode(node, channel.getId(), start, end, siteId);
			
			nodes.add(node);
		}
		
		return nodes;
	}

	private void findTreeGridNode(TreeGridNode parentNode, Integer parentId, Date start, Date end, Integer siteId){
		List<TreeGridNode> treeGridNodes = new ArrayList<TreeGridNode>();
		
		List<Channel> channels = channelDAO.getChannelChildren(parentId);
		for (Channel channel : channels){
			PublishedVo vo = visitDAO.findChannelReleased(start, end, channel.getId(), siteId);
			if (vo == null) continue;
			
			TreeGridNode treeGridNode = new TreeGridNode();

			treeGridNode.setId(channel.getId());
			treeGridNode.setText(channel.getName());
			treeGridNode.setState("open");
			treeGridNode.setData(vo);
			findChannelType(channel, treeGridNode);

			findTreeGridNode(treeGridNode, channel.getId(), start, end, siteId);
			
			treeGridNodes.add(treeGridNode);
		}
		parentNode.setChildren(treeGridNodes);
	}
	
	private void findChannelType(Channel channel, TreeGridNode node){
		switch(channel.getType()){
		case ARTICLE: node.setIconCls("icon-channel-article");break;
		case LEADER: node.setIconCls("icon-channel-leader");break;
		case ONLINE : node.setIconCls("icon-channel-online");break;
		case LEADERARTICLE :node.setIconCls("icon-channel-articlerefer");break;
		case RETRIEVAL : node.setIconCls("icon-channel-retrieval");break;
		case PROJECT : node.setIconCls("icon-channel-project");break;
		case PROJECTARTICLE : node.setIconCls("icon-channel-projectarticle");break;
		case ENTERPRISE : node.setIconCls("icon-channel-enterprise");break;
		case ENTERPRISEARTICLE : node.setIconCls("icon-channel-enterprisearticle");break;
		case EMPLOYE : node.setIconCls("icon-channel-employe");break;
		case EMPLOYEARTICLE : node.setIconCls("icon-channel-employearticle");break;
		default : node.setIconCls("icon-channel-note");
		}
	}

	@Override
	public List<TreeGridNode> findOrganReleased(String startDate, String endDate, Integer siteId) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		List<Organ> rootOrgans = organDAO.getOrganChildren(null);
		
		List<TreeGridNode> nodes = new ArrayList<TreeGridNode>();
		for (Organ organ : rootOrgans){
			PublishedVo vo = visitDAO.findOrganReleased(start, end, organ.getId(), siteId);
			if (vo == null) continue;
			
			TreeGridNode node = new TreeGridNode();
			node.setId(organ.getId());
			node.setText(organ.getName());
			node.setState("open");
			node.setIconCls("icon-organ");
			node.setData(vo);
			
			findOrganChildNode(node, organ.getId(), siteId, start, end);
			
			nodes.add(node);
		}
		
		return nodes;
	}
	
	private void findOrganChildNode(TreeGridNode parentNode, Integer parentId, Integer siteId, Date start, Date end){
		List<TreeGridNode> treeGridNodes = new ArrayList<TreeGridNode>();
		
		List<Organ> organs = organDAO.getOrganChildren(parentId);
		for (Organ organ : organs){
			PublishedVo vo = visitDAO.findOrganReleased(start, end, organ.getId(), siteId);
			if (vo == null) continue;
			
			TreeGridNode treeGridNode = new TreeGridNode();

			treeGridNode.setId(organ.getId());
			treeGridNode.setText(organ.getName());
			treeGridNode.setState("open");
			treeGridNode.setIconCls("icon-organ");
			treeGridNode.setData(vo);

			findOrganChildNode(treeGridNode, organ.getId(), siteId, start, end);
			
			treeGridNodes.add(treeGridNode);
		}
		parentNode.setChildren(treeGridNodes);
	}
}
