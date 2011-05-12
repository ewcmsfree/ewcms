/**
 * 
 */
package com.ewcms.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.context.EwcmsContextHolder;
import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;

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
	
	public String getUserName(){
		return getUsername();
	}
}
