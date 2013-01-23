/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.manager.vo.PublishedVo;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;
import com.ewcms.web.vo.TreeGridNode;

/**
 * 发布统计
 * 
 * @author wu_zhijun
 *
 */
public class PublishedAction extends VisitBaseAction {

	private static final long serialVersionUID = -6995811900374861809L;

	@Autowired
	private VisitFacable visitFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/*========================== 人员发布统计 =================================*/
	public void staffReleasedTable(){
		List<PublishedVo> list = visitFac.findStaffReleased(getStartDate(), getEndDate(), getSiteId(), getChannelId());
		DataGrid data = new DataGrid(list.size(), list);
		Struts2Util.renderJson(JSONUtil.toJSON(data));
	}

	/*========================== 栏目发布统计 =================================*/
	public void channelReleasedTable(){
		List<TreeGridNode> nodes = visitFac.findChannelRelease(getStartDate(), getEndDate(), getSiteId());
		Struts2Util.renderJson(JSONUtil.toJSON(nodes));
	}
	
	/*========================== 政民互动统计 =================================*/
	public void organReleasedTable(){
		List<TreeGridNode> nodes = visitFac.findOrganReleased(getStartDate(), getEndDate(), getSiteId());
		Struts2Util.renderJson(JSONUtil.toJSON(nodes));
	}

}
