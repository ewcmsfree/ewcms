/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wuzhijun
 *
 */
public class MatchBlockQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = -3735199825373099763L;

	@Autowired
	private CrawlerFacable crawlerFac;
	
	private Long gatherId;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
		return null;
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		return null;
	}
	
	@Override
	public String query() {
		List<BlockTreeGridNode> nodes = crawlerFac.findMatchBlockTransformTreeGrid(getGatherId());
		Struts2Util.renderJson(JSONUtil.toJSON(nodes));
		return NONE;
	}
}
