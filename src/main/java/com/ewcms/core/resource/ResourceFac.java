/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.core.resource;

import com.ewcms.core.resource.model.Resource;
import com.ewcms.core.resource.model.ResourceType;
import com.ewcms.core.resource.service.ResourceServiceable;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
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
    public Resource addResource(final File file, final String fileName, final ResourceType type) throws IOException {
        return resourceService.addResource(file, fileName, type);
    }

    @Override
    public void delResource(Integer id) {
        resourceService.delResource(id);
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceService.getResource(id);
    }

    @Override
    public Resource updResourceInfo(Integer id, String title, String description) {
        return resourceService.updResourceInfo(id, title, description);
    }
}
