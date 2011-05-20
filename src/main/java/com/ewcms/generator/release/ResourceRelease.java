/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.release;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.resource.model.Resource;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.util.FileUtil;

/**
 *
 * @author wangwei
 */
public class ResourceRelease implements ResourceReleaseable {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRelease.class);

    @Override
    public void release(final GeneratorDAOable dao,final int siteId) throws ReleaseException {
        List<Resource> resources = dao.getReleaseResource(siteId);
        String siteDir = dao.getSiteServerDir(siteId);
        for (Resource resource : resources) {
            try {
                copy(resource,siteDir);
                dao.releaseResource(resource);
            } catch (IOException e) {
                if(logger.isDebugEnabled()){
                    logger.debug(e.getMessage());
                }
            }
        }
    }

    @Override
    public void releaseSingle(final GeneratorDAOable dao, int id) throws ReleaseException {
        Resource resource = dao.getResource(id);
        if(resource == null){
            if(logger.isDebugEnabled()){
                logger.debug("resource id is {} not find",id);
            }
            return ;
        }
        String siteDir = dao.getSiteServerDir(resource.getSiteId());
        try {
            copy(resource,siteDir);
            dao.releaseResource(resource);
        } catch (IOException e) {
            if(logger.isDebugEnabled()){
                logger.debug(e.getMessage());
            }
        }

    }

    void copy(final Resource res,final String siteDir) throws IOException {
        
        if(siteDir == null){
            if(logger.isDebugEnabled()){
                logger.debug("site server directory is null");
            }
            return;
        }
        
        String fileName = String.format("%s/%s", siteDir, res.getReleasePath());
        String dir = getDir(fileName);
        FileUtil.createDirs(dir, true);
        FileUtil.copyFileOrDir(res.getPath(), fileName, true);
    }

    String getDir(final String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("/"));
    }
}
