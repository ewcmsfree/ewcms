/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler;

import java.util.List;

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
public interface CrawlerFacable {
	public Long addGather(Gather gather);
	public Long updGather(Gather gather);
	public void delGather(Long gatherId);
	public Gather findGather(Long gatherId);
	
	public Long addAndUpdUrlLevel(Long gatherId, UrlLevel urlLevel);
	public void delUrlLevel(Long gatherId, Long urlLevelId);
	public UrlLevel findUrlLevel(Long urlLevelId);
	public void upUrlLevel(Long gatherId, Long urlLevelId);
	public void downUrlLevel(Long gatherId, Long urlLevelId);
	
	public Long addAndUpdMatchBlock(Long gatherId, Long parentId, MatchBlock matchBlock);
	public void delMatchBlock(Long gatherId, Long matchBlockId);
	public MatchBlock findMatchBlock(Long matchBlockId);
	public void upMatchBlock(Long gatherId, Long matchBlockId);
	public void downMatchBlock(Long gatherId, Long matchBlockId);
	public List<MatchBlock> findParentMatchBlockByGatherId(Long gatherId);
	public List<MatchBlock> findChildMatchBlockByParentId(Long gatherId, Long parentId);
	
	public Long addAndUpdFilterBlock(Long gatherId, Long parentId, FilterBlock filterBlock);
	public void delFilterBlock(Long gatherId, Long filterBlockId);
	public FilterBlock findFilterBlock(Long filterBlockId);
	public void upFilterBlock(Long gatherId, Long filterBlockId);
	public void downFilterBlock(Long gatherId, Long filterBlockId);
	public List<FilterBlock> findParentFilterBlockByGatherId(Long gatherId);
	public List<FilterBlock> findChildFilterBlockByParentId(Long gatherId, Long parentId);
	
	public List<BlockTreeGridNode> findMatchBlockTransformTreeGrid(Long gatherId);
	public List<BlockTreeGridNode> findFilterBlockTransformTreeGrid(Long gatherId);
}
