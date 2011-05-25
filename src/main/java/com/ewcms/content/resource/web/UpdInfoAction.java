/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import java.util.ArrayList;
import java.util.List;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.opensymphony.xwork2.util.CreateIfNull;
import com.opensymphony.xwork2.util.KeyProperty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
public class UpdInfoAction {

    @Autowired
    private ResourceFacable resourceFac;

    public void setResourceFac(ResourceFacable fac) {
        this.resourceFac = fac;
    }
    @KeyProperty("id")
    @CreateIfNull(true)
    private List<ResourceInfo> infos = new ArrayList<ResourceInfo>();

    public void setInfos(List<ResourceInfo> infos) {
        this.infos = infos;
    }

    public List<ResourceInfo> getInfos() {
        return infos;
    }

    public void save() {
        List<Resource> resources = new ArrayList<Resource>();
        for (ResourceInfo info : infos) {
            if (info.getTitle() == null
                    && info.getDescription() == null) {
                continue;
            }
            Resource resource = resourceFac.updResourceInfo(
                    info.getId(), info.getTitle(), info.getDescription());
            resources.add(resource);
        }
        Struts2Util.renderJson(JSONUtil.toJSON(resources));
    }
}
