/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 * @author 吴智俊
 */
public class ReviewProcessQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 8224277064008669403L;
	
	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory,	String cacheKey, int rows, int page, Order order) {
    	return null;
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
    	return null;
	}

	public String query() {
		List<Map<String,Object>> listValues = new ArrayList<Map<String,Object>>();
		ReviewProcess reviewProcess = documentFac.findFirstReviewProcessByChannel(channelId);
		Long count = documentFac.findReviewProcessCountByChannel(channelId);
		for (int i = 0; i < count; i++){
			if (reviewProcess == null) break;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", reviewProcess.getId());
			map.put("name", reviewProcess.getName());
			map.put("userGroup", reviewProcess.getUserGroup());
			map.put("userName", reviewProcess.getUserName());
			listValues.add(map);
			reviewProcess = reviewProcess.getNextProcess();
		}
		DataGrid data = new DataGrid(listValues.size(), listValues);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
		return NONE;
	}
}
