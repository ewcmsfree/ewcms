/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.TreeGridNode;

public class InteractiveAction extends VisitBaseAction {

	private static final long serialVersionUID = 1493148088613685313L;

	@Autowired
	private VisitFacable visitFac;
	
	/*========================== 政民互动统计 =================================*/
	public void interactiveTable(){
		List<TreeGridNode> nodes = visitFac.findInteractive(getStartDate(), getEndDate());
		Struts2Util.renderJson(JSONUtil.toJSON(nodes));
	}
	
	/*========================== 政民互动统计 =================================*/
	public void advisoryTable(){
		List<TreeGridNode> nodes = visitFac.findAdvisory(getStartDate(), getEndDate());
		Struts2Util.renderJson(JSONUtil.toJSON(nodes));
	}
}
