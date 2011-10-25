/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.vo.DataGrid;

/**
 * 站点切换Action
 * 
 * @author wangwei
 */
@Controller("siteswitch")
public class SiteSwitchAction extends EwcmsBaseAction {

    private Integer siteId;
    private Integer page = 1;
    private Integer rows = 5;
    
    @Autowired
    private SiteFac siteFac;
    
    public String execute(){
        siteId = getCurrentSite().getId();
        return SUCCESS;
    }
    
    public void query(){
        List<Site> list = siteFac.getSiteListByOrgans(new Integer[]{}, true);
        JsonBaseAction json = new JsonBaseAction();
        if(list.isEmpty()){
            json.renderObject(new DataGrid(0,list));
            return;
        }
        int size = list.size();
        int fromIndex = (page -1) * rows;
        int toIndex = Math.min(size, fromIndex + rows);
        json.renderObject(new DataGrid(list.size(),list.subList(fromIndex, toIndex)));
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public SiteFac getSiteFac() {
        return siteFac;
    }
}
