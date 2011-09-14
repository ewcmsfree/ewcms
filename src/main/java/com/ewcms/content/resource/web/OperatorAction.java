/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.WebPublishable;
import com.ewcms.web.JsonBaseAction;

/**
 * 资源操作Action
 *
 * @author wangwei
 */
@Controller("resource.operator.action")
public class OperatorAction extends JsonBaseAction{

    private static final Logger logger = LoggerFactory.getLogger(OperatorAction.class);
    
    private int[] selections;
    
    private Map<Integer,String> descriptions = new HashMap<Integer,String>();

    @Autowired
    private WebPublishable webpublish;
    
    @Autowired
    private ResourceFacable resourceFac;

    /**
     * 删除资源，只是标识资源为删除状态
     */
    public void delete() {
        for (int id : selections) {
            resourceFac.softDeleteResource(id);
        }
        renderSuccess();
    }

    /**
     * 发布资源
     */
    public void publish() {
        
        List<Integer> errors = new ArrayList<Integer>();
        
        for(int id : selections){
            try{
            	webpublish.publishResourceAgain(id, true);
            }catch(PublishException e){
                logger.error("resource publish fail {}",e);
                errors.add(id);
            }
        }
        
        if(errors.isEmpty()){
            renderSuccess();
        }else{
            String message =String.format("资源编号[%s]发布失败!", StringUtils.join(errors, ","));
            renderError(message);
        }
    }

    /**
     * 保存已经上传的资源
     */
    public void save() {
        List<Resource> resources = resourceFac.saveResource(descriptions);
        renderSuccess(resources);
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
