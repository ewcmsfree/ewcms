/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.Resource.Type;
import com.ewcms.content.resource.service.ResourceServiceable;
import com.ewcms.core.site.model.Site;

/**
 * 实现资源管理门面接口
 *
 * @author wangwei
 */
@Service
public class ResourceFac implements ResourceFacable {

    @Autowired
    private ResourceServiceable resourceService;

    @Override
    
    public Resource uploadResource(File file, String fullName, Resource.Type type) throws IOException {
        return resourceService.uplaod(file, fullName, type);
    }
    
    @Override
    public Resource uploadResource(Site site, File file, String path, Type type) throws IOException{
    	return resourceService.upload(site, file, path, type);
    }
    
    @Override
    public Resource updateResource(Integer id, File file, String fullName,Type type) throws IOException {
        return resourceService.update(id, file, fullName, type);
    }
    
    @Override
    public Resource updateThumbResource(Integer id, File file, String fullName)throws IOException {
        return resourceService.updateThumb(id, file, fullName);
    }
    
    @Override
    public List<Resource> saveResource(Map<Integer, String> descriptions) {
        return resourceService.save(descriptions);
    }
    
    @Override
    public void deleteResource(int[] ids) {
        resourceService.delete(ids);
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceService.getResource(id);
    }

    @Override
    public void softDeleteResource(int[] ids) {
        resourceService.softDelete(ids);
    }

    @Override
    public void clearSoftDeleteResource() {
        resourceService.clearSoftDelete();
    }
    
    @Override
    public void revertResource(int[] ids) {
        resourceService.revert(ids);
    }

    @Override
    public Resource updateDescriptionOfResource(Integer id, String description) {
        return resourceService.updateDescription(id, description);
    }
    
    public void setResourceService(ResourceServiceable service) {
        this.resourceService = service;
    }
}
