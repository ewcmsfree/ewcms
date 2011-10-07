/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.crawler.dao.GatherDAO;
import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.crawler.model.UrlLevel;
import com.ewcms.crawler.web.BlockTreeGridNode;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class GatherService implements GatherServiceable {

	@Autowired
	private GatherDAO gatherDAO;
	
	@Override
	public Long addGather(Gather gather) {
		gatherDAO.persist(gather);
		return gather.getId();
	}

	@Override
	public Long updGather(Gather gather) {
		gatherDAO.merge(gather);
		return gather.getId();
	}

	@Override
	public void delGather(Long gatherId) {
		gatherDAO.removeByPK(gatherId);
	}

	@Override
	public Gather findGather(Long gatherId) {
		return gatherDAO.get(gatherId);
	}

	@Override
	public Long addAndUpdUrlLevel(Long gatherId, UrlLevel urlLevel) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Long maxLevel = gatherDAO.findMaxUrlLevel(gatherId);
		maxLevel++;
		urlLevel.setLevel(maxLevel);
		
		List<UrlLevel> urlLevels = gather.getUrlLevels();
		urlLevels.add(urlLevel);
		gather.setUrlLevels(urlLevels);
		
		gatherDAO.merge(gather);
		gatherDAO.flush(gather);
		
		return urlLevel.getId();
	}

	@Override
	public void delUrlLevel(Long gatherId, Long urlLevelId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		UrlLevel urlLevel = gatherDAO.findUrlLevelById(urlLevelId);
		Assert.notNull(urlLevel);
		
		List<UrlLevel> urlLevels = gather.getUrlLevels();
		Assert.notEmpty(urlLevels);
		
		urlLevels.remove(urlLevel);
		gather.setUrlLevels(urlLevels);
		
		gatherDAO.merge(gather);
	}

	@Override
	public UrlLevel findUrlLevel(Long urlLevelId) {
		return gatherDAO.findUrlLevelById(urlLevelId);
	}

	@Override
	public void upUrlLevel(Long gatherId, Long urlLevelId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		UrlLevel urlLevel = gatherDAO.findUrlLevelById(urlLevelId);
		Assert.notNull(urlLevel);
		
		List<UrlLevel> urlLevels = gather.getUrlLevels();
		Assert.notEmpty(urlLevels);
		
		int index = urlLevels.indexOf(urlLevel);
		if (index > 0 && index <= urlLevels.size() - 1){
			int targetIndex = index - 1;
			UrlLevel targetVo = urlLevels.get(targetIndex);
			Long tempLevel = urlLevel.getLevel();
			
			urlLevel.setLevel(targetVo.getLevel());
			targetVo.setLevel(tempLevel);
			
			urlLevels.add(urlLevel);
			urlLevels.add(targetVo);
			
			gather.setUrlLevels(urlLevels);
			
			gatherDAO.merge(gather);
		}
	}

	@Override
	public void downUrlLevel(Long gatherId, Long urlLevelId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		UrlLevel urlLevel = gatherDAO.findUrlLevelById(urlLevelId);
		Assert.notNull(urlLevel);
		
		List<UrlLevel> urlLevels = gather.getUrlLevels();
		Assert.notEmpty(urlLevels);
		
		int index = urlLevels.indexOf(urlLevel);
		if (index >= 0 && index < urlLevels.size() - 1){
			int targetIndex = index + 1;
			UrlLevel targetVo = urlLevels.get(targetIndex);
			Long tempLevel = urlLevel.getLevel();
			
			urlLevel.setLevel(targetVo.getLevel());
			targetVo.setLevel(tempLevel);
			
			urlLevels.add(urlLevel);
			urlLevels.add(targetVo);
			
			gather.setUrlLevels(urlLevels);
			
			gatherDAO.merge(gather);
		}
	}

	@Override
	public Long addAndUpdMatchBlock(Long gatherId, Long parentId, MatchBlock matchBlock) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Long maxSort = gatherDAO.findMaxMatchBlock(gatherId, parentId);
		maxSort++;
		matchBlock.setSort(maxSort);
		
		MatchBlock parent = null;
		if (parentId != null && parentId.longValue() > 0){
			parent = gatherDAO.findMatchBlockById(parentId);
		}
		matchBlock.setParent(parent);
		
		List<MatchBlock> matchBlocks = gather.getMatchBlocks();
		matchBlocks.add(matchBlock);
		gather.setMatchBlocks(matchBlocks);
		
		gatherDAO.merge(gather);
		gatherDAO.flush(gather);
		
		return matchBlock.getId();
	}

	@Override
	public void delMatchBlock(Long gatherId, Long matchBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		List<MatchBlock> matchBlocks = gather.getMatchBlocks();
		Assert.notEmpty(matchBlocks);
		
		MatchBlock matchBlock = gatherDAO.findMatchBlockById(matchBlockId);
		Assert.notNull(matchBlock);
		
		matchBlocks.remove(matchBlock);
		gather.setMatchBlocks(matchBlocks);
		
		gatherDAO.merge(gather);
	}

	@Override
	public MatchBlock findMatchBlock(Long matchBlockId) {
		return gatherDAO.findMatchBlockById(matchBlockId);
	}

	@Override
	public void upMatchBlock(Long gatherId, Long matchBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		MatchBlock matchBlock = gatherDAO.findMatchBlockById(matchBlockId);
		Assert.notNull(matchBlock);
		
		MatchBlock parent = matchBlock.getParent();
		if (parent != null){
			Long parentId = parent.getId();
			List<MatchBlock> childs = gatherDAO.findChildMatchBlockByParentId(parentId);
			Assert.notEmpty(childs);
			
			int index = childs.indexOf(matchBlock);
			if (index > 0 && index <= childs.size() - 1){
				int targetIndex = index - 1;
				MatchBlock targetVo = childs.get(targetIndex);
				Long tempSort = matchBlock.getSort();
				gatherDAO.updMatchBlockByIdSetSort(matchBlockId, targetVo.getSort());
				gatherDAO.updMatchBlockByIdSetSort(targetVo.getId(), tempSort);
			}
		}
	}

	@Override
	public void downMatchBlock(Long gatherId, Long matchBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		MatchBlock matchBlock = gatherDAO.findMatchBlockById(matchBlockId);
		Assert.notNull(matchBlock);
		
		MatchBlock parent = matchBlock.getParent();
		if (parent != null){
			Long parentId = parent.getId();
			List<MatchBlock> childs = gatherDAO.findChildMatchBlockByParentId(parentId);
			Assert.notEmpty(childs);
			
			int index = childs.indexOf(matchBlock);
			if (index >=0 && index < childs.size() - 1){
				int targetIndex = index + 1;
				MatchBlock targetVo = childs.get(targetIndex);
				Long tempSort = matchBlock.getSort();
				gatherDAO.updMatchBlockByIdSetSort(matchBlockId, targetVo.getSort());
				gatherDAO.updMatchBlockByIdSetSort(targetVo.getId(), tempSort);
			}
		}
	}
	@Override
	public Long addAndUpdFilterBlock(Long gatherId, Long parentId, FilterBlock filterBlock) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Long maxSort = gatherDAO.findMaxFilterBlock(gatherId, parentId);
		maxSort++;
		filterBlock.setSort(maxSort);
		
		FilterBlock parent = null;
		if (parentId != null && parentId.longValue() > 0){
			parent = gatherDAO.findFilterBlockById(parentId);
		}
		filterBlock.setParent(parent);
		
		List<FilterBlock> filterBlocks = gather.getFilterBlocks();
		filterBlocks.add(filterBlock);
		gather.setFilterBlocks(filterBlocks);
		
		gatherDAO.merge(gather);
		gatherDAO.flush(gather);
		
		return filterBlock.getId();
	}

	@Override
	public void delFilterBlock(Long gatherId, Long filterBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		List<FilterBlock> filterBlocks = gather.getFilterBlocks();
		Assert.notEmpty(filterBlocks);
		
		FilterBlock filterBlock = gatherDAO.findFilterBlockById(filterBlockId);
		Assert.notNull(filterBlock);
		
		filterBlocks.remove(filterBlock);
		gather.setFilterBlocks(filterBlocks);
		
		gatherDAO.merge(gather);
		
	}

	@Override
	public FilterBlock findFilterBlock(Long filterBlockId) {
		return gatherDAO.findFilterBlockById(filterBlockId);
	}

	@Override
	public void upFilterBlock(Long gatherId, Long filterBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		FilterBlock filterBlock = gatherDAO.findFilterBlockById(filterBlockId);
		Assert.notNull(filterBlock);
		
		FilterBlock parent = filterBlock.getParent();
		if (parent != null){
			Long parentId = parent.getId();
			List<FilterBlock> childs = gatherDAO.findChildFilterBlockByParentId(parentId);
			Assert.notEmpty(childs);
			
			int index = childs.indexOf(filterBlock);
			if (index > 0 && index <= childs.size() - 1){
				int targetIndex = index - 1;
				FilterBlock targetVo = childs.get(targetIndex);
				Long tempSort = filterBlock.getSort();
				gatherDAO.updFilterBlockByIdSetSort(filterBlockId, targetVo.getSort());
				gatherDAO.updFilterBlockByIdSetSort(targetVo.getId(), tempSort);
			}
		}
	}

	@Override
	public void downFilterBlock(Long gatherId, Long filterBlockId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		FilterBlock filterBlock = gatherDAO.findFilterBlockById(filterBlockId);
		Assert.notNull(filterBlock);
		
		FilterBlock parent = filterBlock.getParent();
		if (parent != null){
			Long parentId = parent.getId();
			List<FilterBlock> childs = gatherDAO.findChildFilterBlockByParentId(parentId);
			Assert.notEmpty(childs);
			
			int index = childs.indexOf(filterBlock);
			if (index >=0 && index < childs.size() - 1){
				int targetIndex = index + 1;
				FilterBlock targetVo = childs.get(targetIndex);
				Long tempSort = filterBlock.getSort();
				gatherDAO.updFilterBlockByIdSetSort(filterBlockId, targetVo.getSort());
				gatherDAO.updFilterBlockByIdSetSort(targetVo.getId(), tempSort);
			}
		}
	}

	@Override
	public List<BlockTreeGridNode> findMatchBlockTransformTreeGrid(Long gatherId) {
		Assert.notNull(gatherId);
		List<MatchBlock> parents = gatherDAO.findParentMatchBlockByGatherId(gatherId);
		Assert.notEmpty(parents);
		
		List<BlockTreeGridNode> treeGridNodes = new ArrayList<BlockTreeGridNode>();		
		childrenMatchBlock(treeGridNodes, parents);
		return treeGridNodes;
	}
	
	private void childrenMatchBlock(List<BlockTreeGridNode> treeGridNodes, List<MatchBlock> matchBlocks){
		BlockTreeGridNode node;
		for (MatchBlock matchBlock : matchBlocks){
			node = new BlockTreeGridNode();
			
			node.setId(matchBlock.getId());
			node.setRegex(matchBlock.getRegex());
			node.setState("open");
			
			List<MatchBlock> childrens = gatherDAO.findChildMatchBlockByParentId(matchBlock.getId());
			if (!childrens.isEmpty()){
				node.setState("closed");
				List<BlockTreeGridNode> childrenNodes = new ArrayList<BlockTreeGridNode>();
				childrenMatchBlock(childrenNodes, childrens);
				node.setChildren(childrenNodes);
			}
			treeGridNodes.add(node);
		}
	}

	@Override
	public List<BlockTreeGridNode> findFilterBlockTransformTreeGrid(Long gatherId) {
		List<FilterBlock> parents = gatherDAO.findParentFilterBlockByGatherId(gatherId);
		Assert.notEmpty(parents);
		
		List<BlockTreeGridNode> treeGridNodes = new ArrayList<BlockTreeGridNode>();
		childrenFilterBlock(treeGridNodes, parents);
		return treeGridNodes;
	}
	
	private void childrenFilterBlock(List<BlockTreeGridNode> treeGridNodes, List<FilterBlock> filterBlocks){
		BlockTreeGridNode node;
		for (FilterBlock filterBlock : filterBlocks){
			node = new BlockTreeGridNode();
			
			node.setId(filterBlock.getId());
			node.setRegex(filterBlock.getRegex());
			node.setState("open");
			
			List<FilterBlock> childrens = gatherDAO.findChildFilterBlockByParentId(filterBlock.getId());
			if (!childrens.isEmpty()){
				node.setState("closed");
				List<BlockTreeGridNode> childrenNodes = new ArrayList<BlockTreeGridNode>();
				childrenFilterBlock(childrenNodes, childrens);
				node.setChildren(childrenNodes);
			}
			treeGridNodes.add(node);
		}
	}
}
