/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.io.ImageZipUtil;
import com.ewcms.content.resource.dao.ResourceDAO;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.ResourceType;
import com.ewcms.content.resource.operator.ResourceNameable;
import com.ewcms.content.resource.operator.ResourceOperator;
import com.ewcms.content.resource.operator.ResourceOperatorable;
import com.ewcms.core.site.model.Site;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 *
 * @author 吴智俊
 */
@Service
public class ResourceService implements ResourceServiceable {

    private static final ResourceOperatorable operator = new ResourceOperator();
    
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    @Autowired
    private ResourceDAO resourceDao;

    public void setResourceDao(ResourceDAO dao) {
        this.resourceDao = dao;
    }

    @Override
    public Resource addResource(final File file, final String fileName, final ResourceType type) throws IOException {
        String root = getCurrentRootDir();
        ResourceNameable rule = operator.writer(file, root, fileName);
        Resource resource = new Resource();
        resource.setNewName(rule.getNewName());
        resource.setPath(rule.getFileNewName());
        resource.setSize(file.length());
        Integer siteId = getCurrentSiteId();
        resource.setSiteId(siteId);
        resource.setName(getSingleName(fileName));
        String title = removeFileNameSuffix(fileName);
        resource.setTitle(title);
        resource.setDescription(title);
        resource.setType(type);
        resource.setReleasePath(rule.getReleaseaPath());

        if (type == ResourceType.IMAGE) {
            Boolean isZip = ImageZipUtil.compression(rule.getFileNewName(), rule.getFileNewNameZip(), 128, 128);
            if (isZip) {
                resource.setPathZip(rule.getFileNewNameZip());
                resource.setReleasePathZip(rule.getReleasePathZip());
            } else {
                resource.setPathZip(rule.getFileNewName());
                resource.setReleasePathZip(rule.getReleaseaPath());
            }
        }

        resourceDao.persist(resource);

        return resource;
    }

    private String removeFileNameSuffix(final String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);
    }

    @Override
    public void delResource(Integer id) {
        Resource resource = getResource(id);
        if (resource == null) {
            return;
        }
        try {
            String dir = getCurrentReleaseDir();
            if(dir.endsWith("/")){
                dir = dir.substring(0, dir.length()-1);
            }
            operator.delete(dir + resource.getReleasePath());
        } catch (IOException e) {
            logger.error(e.toString());
        }
        resourceDao.remove(resource);
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceDao.get(id);
    }

    @Override
    public Resource updResourceInfo(Integer id, String title, String description) {
        Resource resource = getResource(id);
        resource.setTitle(title);
        resource.setDescription(description);

        resourceDao.persist(resource);

        return resource;
    }

    private String getSingleName(final String name) {
        String[] names = name.split("/");
        return names[names.length - 1];
    }

    private String getCurrentRootDir() {
        Site site = EwcmsContextUtil.getCurrentSite();
        return site.getResourceDir();
    }

    private Integer getCurrentSiteId() {
        Site site = EwcmsContextUtil.getCurrentSite();
        return site.getId();
    }

    private String getCurrentReleaseDir(){
         Site site = EwcmsContextUtil.getCurrentSite();
        return site.getServerDir();
    }
}
