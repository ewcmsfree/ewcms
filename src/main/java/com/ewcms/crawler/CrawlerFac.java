/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.crawler.model.Domain;
import com.ewcms.crawler.service.GatherServiceable;
import com.ewcms.crawler.web.BlockTreeGridNode;

/**
 * 
 * @author wuzhijun
 *
 */
@Service
public class CrawlerFac implements CrawlerFacable {
	
	@Autowired
	private GatherServiceable gatherService;

	@Override
	public Long addGather(Gather gather) {
		return gatherService.addGather(gather);
	}

	@Override
	public Long updGather(Gather gather) {
		return gatherService.updGather(gather);
	}

	@Override
	public void delGather(Long gatherId) {
		gatherService.delGather(gatherId);
	}

	@Override
	public Gather findGather(Long gatherId) {
		return gatherService.findGather(gatherId);
	}

	@Override
	public Long addAndUpdDomain(Long gatherId, Domain urlLevel) throws BaseException{
		return gatherService.addAndUpdDomain(gatherId, urlLevel);
	}

	@Override
	public void delDomain(Long gatherId, Long urlLevelId) {
		gatherService.delDomain(gatherId, urlLevelId);
	}

	@Override
	public Domain findDomain(Long urlLevelId) {
		return gatherService.findDomain(urlLevelId);
	}

	@Override
	public void upDomain(Long gatherId, Long urlLevelId) {
		gatherService.upDomain(gatherId, urlLevelId);
	}

	@Override
	public void downDomain(Long gatherId, Long urlLevelId) {
		gatherService.downDomain(gatherId, urlLevelId);
	}

	@Override
	public Long addAndUpdMatchBlock(Long gatherId, Long parentId,
			MatchBlock matchBlock) {
		return gatherService.addAndUpdMatchBlock(gatherId, parentId, matchBlock);
	}

	@Override
	public void delMatchBlock(Long gatherId, Long matchBlockId) {
		gatherService.delMatchBlock(gatherId, matchBlockId);
	}

	@Override
	public MatchBlock findMatchBlock(Long matchBlockId) {
		return gatherService.findMatchBlock(matchBlockId);
	}

	@Override
	public void upMatchBlock(Long gatherId, Long matchBlockId) {
		gatherService.upMatchBlock(gatherId, matchBlockId);
	}

	@Override
	public void downMatchBlock(Long gatherId, Long matchBlockId) {
		gatherService.downMatchBlock(gatherId, matchBlockId);
	}

	@Override
	public Long addAndUpdFilterBlock(Long gatherId, Long parentId,
			FilterBlock filterBlock) {
		return gatherService.addAndUpdFilterBlock(gatherId, parentId, filterBlock);
	}

	@Override
	public void delFilterBlock(Long gatherId, Long filterBlockId) {
		gatherService.delFilterBlock(gatherId, filterBlockId);
	}

	@Override
	public FilterBlock findFilterBlock(Long filterBlockId) {
		return gatherService.findFilterBlock(filterBlockId);
	}

	@Override
	public void upFilterBlock(Long gatherId, Long filterBlockId) {
		gatherService.upFilterBlock(gatherId, filterBlockId);
	}

	@Override
	public void downFilterBlock(Long gatherId, Long filterBlockId) {
		gatherService.downFilterBlock(gatherId, filterBlockId);
	}

	@Override
	public List<BlockTreeGridNode> findMatchBlockTransformTreeGrid(Long gatherId) {
		return gatherService.findMatchBlockTransformTreeGrid(gatherId);
	}

	@Override
	public List<BlockTreeGridNode> findFilterBlockTransformTreeGrid(Long gatherId) {
		return gatherService.findFilterBlockTransformTreeGrid(gatherId);
	}

	@Override
	public List<MatchBlock> findParentMatchBlockByGatherId(Long gatherId) {
		return gatherService.findParentMatchBlockByGatherId(gatherId);
	}

	@Override
	public List<MatchBlock> findChildMatchBlockByParentId(Long gatherId,
			Long parentId) {
		return gatherService.findChildMatchBlockByParentId(gatherId, parentId);
	}

	@Override
	public List<FilterBlock> findParentFilterBlockByGatherId(Long gatherId) {
		return gatherService.findParentFilterBlockByGatherId(gatherId);
	}

	@Override
	public List<FilterBlock> findChildFilterBlockByParentId(Long gatherId,
			Long parentId) {
		return gatherService.findChildFilterBlockByParentId(gatherId, parentId);
	}

	@Override
	public void delGatherData(Long gatherId) {
		gatherService.delGatherData(gatherId);
	}
}
