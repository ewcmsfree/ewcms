/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 资源操作
 *
 * @author wangwei
 */
@Controller("resource.operator.action")
public class OperatorAction {

    private int[] selections;
    
    private Map<Integer,String> descriptions = new HashMap<Integer,String>();

    @Autowired
    private WebPublishable webpublish;
    
    @Autowired
    private ResourceFacable resourceFac;

    public void delete() {
        for (int id : selections) {
            resourceFac.deleteResource(id);
        }
        Struts2Util.renderJson("{\"info\":\"删除成功\"}");
    }

    public void publish() {
        for(int id : selections){
            try{
            	webpublish.publishResourceAgain(id, true);
            }catch(PublishException e){
               //TODO 错误处理
            }
        }
        Struts2Util.renderJson("{\"info\":\"重新发布成功\"}");
    }

    public void save() {
        List<Resource> resources = resourceFac.saveResource(descriptions);
        Struts2Util.renderJson(JSONUtil.toJSON(resources));
    }
    
    public void setSelections(int[] selects) {
        this.selections = selects;
    }
    
    public void setDescriptions(Map<Integer,String> infos) {
        this.descriptions = infos;
    }

    public Map<Integer,String> getDescriptions() {
        return descriptions;
    }
    
    public void setResourceFac(ResourceFacable fac) {
        this.resourceFac = fac;
    }

    public void setWebpublish(WebPublishable webpublish) {
        this.webpublish = webpublish;
    }
}
