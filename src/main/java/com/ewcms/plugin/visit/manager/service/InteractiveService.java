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

import com.ewcms.core.site.dao.OrganDAO;
import com.ewcms.core.site.model.Organ;
import com.ewcms.plugin.visit.manager.dao.VisitDAO;
import com.ewcms.plugin.visit.manager.vo.InteractiveVo;
import com.ewcms.plugin.visit.util.DateTimeUtil;
import com.ewcms.web.vo.TreeGridNode;

@Service
public class InteractiveService implements InteractiveServiceable {

	@Autowired
	private VisitDAO visitDAO;
	@Autowired
	private OrganDAO organDAO;
	
	@Override
	public List<TreeGridNode> findInteractive(String startDate, String endDate) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		List<Organ> rootOrgans = organDAO.getOrganChildren(null);
		
		List<TreeGridNode> nodes = new ArrayList<TreeGridNode>();
		for (Organ organ : rootOrgans){
			InteractiveVo vo = visitDAO.findInteractive(start, end, organ.getId());
			if (vo == null) continue;
			
			TreeGridNode node = new TreeGridNode();
			node.setId(organ.getId());
			node.setText(organ.getName());
			node.setState("open");
			node.setIconCls("icon-organ");
			node.setData(vo);
			
			findInteractiveChildNode(node, organ.getId(), start, end);
			
			nodes.add(node);
		}
		
		return nodes;
	}
	
	private void findInteractiveChildNode(TreeGridNode parentNode, Integer parentId, Date start, Date end){
		List<TreeGridNode> treeGridNodes = new ArrayList<TreeGridNode>();
		
		List<Organ> organs = organDAO.getOrganChildren(parentId);
		for (Organ organ : organs){
			InteractiveVo vo = visitDAO.findInteractive(start, end, organ.getId());
			if (vo == null) continue;
			
			TreeGridNode treeGridNode = new TreeGridNode();

			treeGridNode.setId(organ.getId());
			treeGridNode.setText(organ.getName());
			treeGridNode.setState("open");
			treeGridNode.setIconCls("icon-organ");
			treeGridNode.setData(vo);

			findInteractiveChildNode(treeGridNode, organ.getId(), start, end);
			
			treeGridNodes.add(treeGridNode);
		}
		parentNode.setChildren(treeGridNodes);
	}

	@Override
	public List<TreeGridNode> findAdvisory(String startDate, String endDate) {
		Date start = DateTimeUtil.getStringToDate(startDate);
		Date end = DateTimeUtil.getStringToDate(endDate);
		
		List<Organ> rootOrgans = organDAO.getOrganChildren(null);
		
		List<TreeGridNode> nodes = new ArrayList<TreeGridNode>();
		for (Organ organ : rootOrgans){
			InteractiveVo vo = visitDAO.findAdvisory(start, end, organ.getId());
			if (vo == null) continue;
			
			TreeGridNode node = new TreeGridNode();
			node.setId(organ.getId());
			node.setText(organ.getName());
			node.setState("open");
			node.setIconCls("icon-organ");
			node.setData(vo);
			
			findAdvisoryChildNode(node, organ.getId(), start, end);
			
			nodes.add(node);
		}
		
		return nodes;
	}
	
	private void findAdvisoryChildNode(TreeGridNode parentNode, Integer parentId, Date start, Date end){
		List<TreeGridNode> treeGridNodes = new ArrayList<TreeGridNode>();
		
		List<Organ> organs = organDAO.getOrganChildren(parentId);
		for (Organ organ : organs){
			InteractiveVo vo = visitDAO.findAdvisory(start, end, organ.getId());
			if (vo == null) continue;
			
			TreeGridNode treeGridNode = new TreeGridNode();

			treeGridNode.setId(organ.getId());
			treeGridNode.setText(organ.getName());
			treeGridNode.setState("open");
			treeGridNode.setIconCls("icon-organ");
			treeGridNode.setData(vo);

			findAdvisoryChildNode(treeGridNode, organ.getId(), start, end);
			
			treeGridNodes.add(treeGridNode);
		}
		parentNode.setChildren(treeGridNodes);
	}
}
