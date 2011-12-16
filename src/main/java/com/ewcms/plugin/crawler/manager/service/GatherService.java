/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.crawler.generate.crawler4j.util.IO;
import com.ewcms.plugin.crawler.manager.dao.DomainDAO;
import com.ewcms.plugin.crawler.manager.dao.GatherDAO;
import com.ewcms.plugin.crawler.manager.web.BlockTreeGridNode;
import com.ewcms.plugin.crawler.model.Domain;
import com.ewcms.plugin.crawler.model.FilterBlock;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.model.MatchBlock;
import com.ewcms.plugin.crawler.util.CrawlerUtil;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class GatherService implements GatherServiceable {

	@Autowired
	private GatherDAO gatherDAO;
	@Autowired
	private DomainDAO domainDAO;
	@Autowired
	private ArticleMainServiceable articleMainService;
	
	public void setGatherDAO(GatherDAO gatherDAO){
		this.gatherDAO = gatherDAO;
	}
	
	@Override
	public Long addGather(Gather gather) {
		gatherDAO.persist(gather);
		return gather.getId();
	}

	@Override
	public Long updGather(Gather gather) {
		Gather oldGather = gatherDAO.get(gather.getId());
		gather.setDomains(oldGather.getDomains());
		gather.setMatchBlocks(oldGather.getMatchBlocks());
		gather.setFilterBlocks(oldGather.getFilterBlocks());
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
	public Long addAndUpdDomain(Long gatherId, Domain domain) throws BaseException{
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		if (domain.getId() == null){
			Boolean unique = domainDAO.findDomainUniqueUrlByGatherId(gatherId, domain.getUrl());
			if (!unique){
				throw new BaseException("不能设置相同的URL地址","不能设置相同的URL地址");
			}
			Long maxLevel = domainDAO.findMaxDomainByGatherId(gatherId);
			maxLevel++;
			domain.setLevel(maxLevel);
			domainDAO.persist(domain);
			domainDAO.flush(domain);
		}
		
		List<Domain> domains = gather.getDomains();
		domains.add(domain);
		gather.setDomains(domains);
		
		gatherDAO.merge(gather);
		
		return domain.getId();
	}

	@Override
	public void delDomain(Long gatherId, Long domainId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Domain domain = domainDAO.findDomainById(domainId);
		Assert.notNull(domain);
		
		List<Domain> domains = gather.getDomains();
		Assert.notEmpty(domains);
		
		domains.remove(domain);
		gather.setDomains(domains);
		
		gatherDAO.merge(gather);
	}

	@Override
	public Domain findDomain(Long domainId) {
		return domainDAO.findDomainById(domainId);
	}

	@Override
	public void upDomain(Long gatherId, Long domainId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Domain domain = domainDAO.findDomainById(domainId);
		Assert.notNull(domain);
		
		List<Domain> domains = gather.getDomains();
		Assert.notEmpty(domains);
		
		int index = domains.indexOf(domain);
		if (index > 0 && index <= domains.size() - 1){
			int targetIndex = index - 1;
			Domain targetVo = domains.get(targetIndex);
			Long tempLevel = domain.getLevel();
			
			domain.setLevel(targetVo.getLevel());
			targetVo.setLevel(tempLevel);
			
			domains.add(domain);
			domains.add(targetVo);
			
			gather.setDomains(domains);
			
			gatherDAO.merge(gather);
		}
	}

	@Override
	public void downDomain(Long gatherId, Long domainId) {
		Gather gather = gatherDAO.get(gatherId);
		Assert.notNull(gather);
		
		Domain domain = domainDAO.findDomainById(domainId);
		Assert.notNull(domain);
		
		List<Domain> domains = gather.getDomains();
		Assert.notEmpty(domains);
		
		int index = domains.indexOf(domain);
		if (index >= 0 && index < domains.size() - 1){
			int targetIndex = index + 1;
			Domain targetVo = domains.get(targetIndex);
			Long tempLevel = domain.getLevel();
			
			domain.setLevel(targetVo.getLevel());
			targetVo.setLevel(tempLevel);
			
			domains.add(domain);
			domains.add(targetVo);
			
			gather.setDomains(domains);
			
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
			List<MatchBlock> childs = gatherDAO.findChildMatchBlockByParentId(gatherId, parentId);
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
			List<MatchBlock> childs = gatherDAO.findChildMatchBlockByParentId(gatherId, parentId);
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
			List<FilterBlock> childs = gatherDAO.findChildFilterBlockByParentId(gatherId, parentId);
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
			List<FilterBlock> childs = gatherDAO.findChildFilterBlockByParentId(gatherId, parentId);
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
		childrenMatchBlock(gatherId, treeGridNodes, parents);
		return treeGridNodes;
	}
	
	private void childrenMatchBlock(Long gatherId, List<BlockTreeGridNode> treeGridNodes, List<MatchBlock> matchBlocks){
		BlockTreeGridNode node;
		for (MatchBlock matchBlock : matchBlocks){
			node = new BlockTreeGridNode();
			
			node.setId(matchBlock.getId());
			node.setRegex(matchBlock.getRegex());
			node.setState("open");
			
			List<MatchBlock> childrens = gatherDAO.findChildMatchBlockByParentId(gatherId, matchBlock.getId());
			if (!childrens.isEmpty()){
				node.setState("closed");
				List<BlockTreeGridNode> childrenNodes = new ArrayList<BlockTreeGridNode>();
				childrenMatchBlock(gatherId, childrenNodes, childrens);
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
		childrenFilterBlock(gatherId, treeGridNodes, parents);
		return treeGridNodes;
	}
	
	private void childrenFilterBlock(Long gatherId, List<BlockTreeGridNode> treeGridNodes, List<FilterBlock> filterBlocks){
		BlockTreeGridNode node;
		for (FilterBlock filterBlock : filterBlocks){
			node = new BlockTreeGridNode();
			
			node.setId(filterBlock.getId());
			node.setRegex(filterBlock.getRegex());
			node.setState("open");
			
			List<FilterBlock> childrens = gatherDAO.findChildFilterBlockByParentId(gatherId, filterBlock.getId());
			if (!childrens.isEmpty()){
				node.setState("closed");
				List<BlockTreeGridNode> childrenNodes = new ArrayList<BlockTreeGridNode>();
				childrenFilterBlock(gatherId, childrenNodes, childrens);
				node.setChildren(childrenNodes);
			}
			treeGridNodes.add(node);
		}
	}

	@Override
	public List<MatchBlock> findParentMatchBlockByGatherId(Long gatherId) {
		return gatherDAO.findParentMatchBlockByGatherId(gatherId);
	}

	@Override
	public List<MatchBlock> findChildMatchBlockByParentId(Long gatherId,
			Long parentId) {
		return gatherDAO.findChildMatchBlockByParentId(gatherId, parentId);
	}

	@Override
	public List<FilterBlock> findParentFilterBlockByGatherId(Long gatherId) {
		return gatherDAO.findParentFilterBlockByGatherId(gatherId);
	}

	@Override
	public List<FilterBlock> findChildFilterBlockByParentId(Long gatherId,
			Long parentId) {
		return gatherDAO.findChildFilterBlockByParentId(gatherId, parentId);
	}

	@Override
	public void delGatherData(Long gatherId) {
		try{
			File gatherFolder = new File(CrawlerUtil.ROOT_FOLDER + gatherId + "/frontier");
			if (gatherFolder.exists()) IO.deleteFolderContents(gatherFolder);
		
			Gather gather = gatherDAO.get(gatherId);
			Assert.notNull(gather);
			
			Integer channelId = gather.getChannelId();
			articleMainService.delArticleMainByCrawler(channelId, CrawlerUtil.USER_NAME);
		}catch(Exception e){
		}
	}
}
