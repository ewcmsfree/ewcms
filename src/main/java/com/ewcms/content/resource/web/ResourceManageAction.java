/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.web;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.generator.GeneratorServiceable;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.web.util.Struts2Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author wangwei
 */
@Controller
public class ResourceManageAction {

    private int[] selections;

    @Autowired
    private GeneratorServiceable generatorService;
    
    @Autowired
    private ResourceFacable resourceFac;

    public void setResourceFac(ResourceFacable fac) {
        this.resourceFac = fac;
    }

    public void setSelections(int[] selects) {
        this.selections = selects;
    }

    public void delete() {
        for (int id : selections) {
            resourceFac.delResource(id);
        }
        Struts2Util.renderJson("{\"info\":\"删除成功\"}");
    }

    public void release() {
        for(int id : selections){
            try{
                generatorService.generatorResourceSingle(id);
            }catch(ReleaseException e){
               //TODO 错误处理
            }
        }
        Struts2Util.renderJson("{\"info\":\"重新发布成功\"}");
    }
}
