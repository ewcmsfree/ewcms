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
import com.ewcms.content.resource.service.ResourceServiceable;

/**
 * 实现资源管理门面接口
 *
 * @author wangwei
 */
@Service
public class ResourceFac implements ResourceFacable {

    @Autowired
    private ResourceServiceable resourceService;

    public void setResourceService(ResourceServiceable service) {
        this.resourceService = service;
    }

    @Override
    
    public Resource uploadResource(File file, String fullName, Resource.Type type) throws IOException {
        return resourceService.uplaod(file, fullName, type);
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
    public void deleteResource(Integer id) {
        resourceService.delete(id);
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceService.getResource(id);
    }

    @Override
    public void softDeleteResource(Integer id) {
        resourceService.softDelete(id);
    }

    @Override
    public void revertResource(Integer id) {
        resourceService.revert(id);
    }

    @Override
    public Resource updateDescriptionOfResource(Integer id, String description) {
        return resourceService.updateDescription(id, description);
    }
}
