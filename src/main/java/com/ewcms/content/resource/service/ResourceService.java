/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.resource.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.io.ImageUtil;
import com.ewcms.content.resource.dao.ResourceDAO;
import com.ewcms.content.resource.dao.ResourceDAOable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.content.resource.model.Resource.State;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.resource.operator.FileOperator;
import com.ewcms.publication.resource.operator.ResourceOperatorable;
import com.ewcms.publication.uri.UriRules;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现资源管理
 *
 * @author 吴智俊 王伟
 */
@Service
public class ResourceService implements ResourceServiceable {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    private static final String DEFAULT_RESOUCE_CONTEXT = "pub_res";
    private static final String DEFAULT_THUMB_SUFFIX="_thumb";
    
    @Autowired
    private ResourceDAOable resourceDao;
    
    private Site getCurrentSite() {
        Site site = EwcmsContextUtil.getCurrentSite();
        logger.debug("Current site is {}",site);
        return site;
    }
    
    private String getResourceContext(){
        //TODO Get resource context
        return DEFAULT_RESOUCE_CONTEXT;
    }
    
    private String getThumbSuffix(){
        return DEFAULT_THUMB_SUFFIX;
    }
    
    /**
     * 通过路径得到文件名
     * 
     * @param path 文件路径
     * @return
     */
    String getFilename(String path) {
        String[] names = path.split("/");
        return names[names.length - 1];
    }
 
    /**
     * 得到引导图Uri
     * 
     * @param uri 统一资源资源地址
     * @return
     */
    String getThumbUri(String uri){
        int index = StringUtils.lastIndexOf(uri, '.');
        if(index == -1){
            return uri+getThumbSuffix();
        }else{
            return StringUtils.overlay(uri, getThumbSuffix(), index,index);
        }
    }
    
    /**
     * 上传图片压缩
     * 
     * @param site 站点
     * @param uri  图片地址
     * @param path 图片地址
     * @return     压缩图片地址
     */
    private String imageCompression(Site site,String uri,String path){
        String thumbUri = getThumbUri(uri);
        String imagePath = Resource.resourcePath(site, thumbUri);
        boolean success = ImageUtil.compression(path, imagePath, 128, 128);
        return success ? thumbUri : uri;
    }
    
    /**
     * 得到文件后缀名
     * 
     * @param name 文件名
     * @return
     */
    String getSuffix(String name) {
        if (StringUtils.contains(name, ".")) {
            return StringUtils.substringAfterLast(name, ".");
        }else{
            return "";
        }
    }
    
    @Override
    public Resource uplaod(File file, String path, Resource.Type type) throws IOException {
        Site site = getCurrentSite();
        ResourceOperatorable operator = new FileOperator(site.getResourceDir());
        String name = getFilename(path);
        String uri = operator.write(new FileInputStream(file), UriRules.newResource(getResourceContext()),getSuffix(name));
        Resource resource = new Resource();
        resource.setUri(uri);
        resource.setSize(file.length());
        resource.setName(name);
        resource.setDescription(name);
        resource.setType(type);
        resource.setSite(site);
        if (type == Resource.Type.IMAGE) {
            resource.setThumbUri(imageCompression(site,uri,file.getPath()));
        }
        resourceDao.persist(resource);

        return resource;
    }
    
    @Override
    public Resource update(Integer id, File file, String path, Resource.Type type)throws IOException {
        
        Resource resource = resourceDao.get(id);
        Assert.notNull(resource,"Resourc is not exist,id = " + id);
        
        FileUtils.copyFile(file, new File(resource.getPath()));
        
        if (type == Resource.Type.IMAGE) {
            if(resource.getPath().equals(resource.getThumbPath())){
                String thumbUri = getThumbUri(resource.getUri());
                String thumbPath = Resource.resourcePath(resource.getSite(), thumbUri);
                if(ImageUtil.compression(file.getPath(), thumbPath, 128, 128)){
                    resource.setThumbUri(thumbUri);
                }
            }else{
                if(!ImageUtil.compression(file.getPath(), resource.getThumbPath(), 128, 128)){
                    FileUtils.forceDeleteOnExit(new File(resource.getThumbPath()));
                    resource.setThumbUri(resource.getUri());
                }
            }
        }
        
        resource.setState(State.NORMAL);
        resource.setUpdateTime(new Date(System.currentTimeMillis()));
        resource.setName(getFilename(path));
        resourceDao.persist(resource);
        return resource;
    }
    
    @Override
    public Resource updateThumb(Integer id,File file, String path) throws IOException {
        
        Resource resource = resourceDao.get(id);
        Assert.notNull(resource,"Resourc is not exist,id = " + id);

        String oldThumbPath = resource.getThumbPath();
        Site site = getCurrentSite();
        ResourceOperatorable operator = new FileOperator(site.getResourceDir());
        String name = getFilename(path);
        String uri = operator.write(new FileInputStream(file), UriRules.newResourceThumb(getResourceContext()),getSuffix(name));
        resource.setThumbUri(uri);
        resourceDao.persist(resource);
        
        if(!StringUtils.isBlank(oldThumbPath)){
            FileUtils.forceDeleteOnExit(new File(oldThumbPath));    
        }
        
        return resource;
    }

    @Override
    public List<Resource> save(Map<Integer, String> descriptions) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Integer id : descriptions.keySet()) {
            Resource resource = resourceDao.get(id);
            if(resource == null || resource.getState() == State.DELETE){
                continue;
            }
            String desc = descriptions.get(id);
            resource.setState(State.NORMAL);
            resource.setDescription(desc);
            resourceDao.persist(resource);
            resources.add(resource);
        }
        return resources;
    }
    
    /**
     * 删除属与资源的文件
     * 
     * @param resource
     */
    private void deleteResourceFile(Resource resource){
        File f = new File(resource.getPath());
        f.deleteOnExit();
        if(StringUtils.isNotBlank(resource.getThumbPath())){
            f = new File(resource.getThumbPath());
            f.deleteOnExit();
        }
    }
    
    @Override
    public void delete(int[] ids) {
        for(int id : ids){
            Resource resource = getResource(id);
            deleteResourceFile(resource);
            resourceDao.remove(resource);
            
            if(resource.getState() == Resource.State.RELEASED){
              //TODO 写入删除日志，下架该资源
            }    
        }
    }

    @Override
    public Resource getResource(Integer id) {
        return resourceDao.get(id);
    }

    @Override
    public Resource updateDescription(Integer id, String description) {
        Resource resource = getResource(id);
        resource.setDescription(description);
        
        resourceDao.persist(resource);
        return resource;
    }
    
    @Override
    public void softDelete(int[] ids) {
        for(int id : ids){
            Resource resource = getResource(id);
            resource.setState(Resource.State.DELETE);
            
            resourceDao.persist(resource);
        }
    }

    @Override
    public void clearSoftDelete() {
        Integer siteId = getCurrentSite().getId();
        List<Resource> resources = resourceDao.findSoftDeleteResources(siteId);
        for(Resource res : resources){
            deleteResourceFile(res);
            resourceDao.remove(res);
        }
    }
    
    @Override
    public void revert(int[] ids) {
        for(int id : ids){
            Resource resource = getResource(id);
            if(resource.getState() == Resource.State.DELETE){
                resource.setState(Resource.State.NORMAL);
                resourceDao.persist(resource);
            }
        }
    }
    
    @Override
    public List<Resource> findNotReleaseResources(Integer siteId) {
        return resourceDao.findNotReleaseResources(siteId);
    }

    @Override
    public void publishResource(Integer id) {
        Resource resource = resourceDao.get(id);
        resource.setState(Resource.State.RELEASED);
        resource.setPublishTime(new Date(System.currentTimeMillis()));
        resourceDao.persist(resource);
    }

    @Override
    public void updateNotRelease(Integer siteId) {
        resourceDao.updateNotRelease(siteId);
    }
    
    @Override
    public Resource getResourceByUri(String uri) {
        Site site = getCurrentSite();
        Integer siteId = site.getId();
        return resourceDao.getResourceByUri(siteId, uri);
    }
    
    public void setResourceDao(ResourceDAO dao) {
        this.resourceDao = dao;
    }
}
