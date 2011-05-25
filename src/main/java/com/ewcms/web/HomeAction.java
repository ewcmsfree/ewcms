/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.context.EwcmsContextHolder;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 周冬初
 * 
 */
public class HomeAction extends EwcmsBaseAction {
    
    private Integer siteId;
    private String username;
    
	@Autowired
	private SiteFac siteFac;
	
	@Autowired
	private UserServiceable userService;

	
	public String execute() {
		Site site;
		if (siteId == null) {
			site = getSiteList()==null|| getSiteList().size()==0?new Site():getSiteList().get(0);
			setSiteId(site.getId());
		} else {
			site = siteFac.getSite(siteId);
		}
		EwcmsContextHolder.getContext().setSite(site);
		username = userService.getUserRealName();
		return SUCCESS;
	}
	
	public String getUsername(){
	    return username;
	}
	
	private List<Site> getSiteList() {
		return siteFac.getSiteListByOrgans(new Integer[]{}, true);
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public void siteLoad(){
		Struts2Util.renderJson(JSONUtil.toJSON(getSiteList()));		
	}
}
