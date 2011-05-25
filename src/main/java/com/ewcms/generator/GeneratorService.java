/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator;

import com.ewcms.core.log.dao.ReleaseLogDAO;
import com.ewcms.core.log.model.ReleaseLog;
import com.ewcms.generator.dao.GeneratorDAOable;
import com.ewcms.generator.release.ReleaseException;
import com.ewcms.generator.release.ReleaseFactory;
import com.ewcms.core.site.model.Channel;
import com.ewcms.generator.release.Releaseable;
import com.ewcms.generator.release.ResourceReleaseable;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class GeneratorService implements GeneratorServiceable {

    @Autowired
    private GeneratorDAOable generatorDao;
    @Autowired
    private Configuration cfg;
    @Autowired
    private ReleaseLogDAO releaseDao;

    public void setGeneratorDao(GeneratorDAOable dao) {
        this.generatorDao = dao;
    }

    public void setCfg(Configuration cfg) {
        this.cfg = cfg;
    }

    public void setReleaseLogDao(ReleaseLogDAO dao) {
        this.releaseDao = dao;
    }

    @Override
    public void generator(final int channelId) throws ReleaseException {
        Channel channel = generatorDao.getChannel(channelId);
        if (channel == null) {
            return;
        }
        
        generatorResource(channel.getSite().getId());
        generatorResourceTPL(channel.getSite().getId());
        
        generatorArticle(channel, false);
        generatorHome(channel, false);
        generatorList(channel, false);
        persistReleaseLog(channel.getSite().getId());
    }

    private void persistReleaseLog(final Integer siteId) {
        ReleaseLog log = new ReleaseLog();
        log.setSiteId(siteId);
        releaseDao.persist(log);
    }

    public void reGenerator(final int channelId)throws ReleaseException{
        generatorDao.cleanArticlePublish(channelId);
        generator(channelId);
    }
    
    @Override
    public void generatorDebug(final int channelId) throws ReleaseException {
        Channel channel = generatorDao.getChannel(channelId);

        generatorArticle(channel, true);
        generatorList(channel, true);
        generatorHome(channel, true);
    }

    private void generatorHome(final Channel channel, final boolean debug) throws ReleaseException {
        Releaseable homeRelease;
        if (hasHomeTemplate(channel)) {
            homeRelease = ReleaseFactory.homeRelease();
        } else {
            if(!hasListTemplate(channel)){
                return;
            }
            homeRelease = ReleaseFactory.homeListRelease();
        }
        if (debug) {
            homeRelease.releaseDebug(cfg, generatorDao, channel);
        } else {
            homeRelease.release(cfg, generatorDao, channel);
        }
    }

    private boolean hasHomeTemplate(final Channel channel) {
        return channel.getHomeTPL() != null;
    }

    private void generatorList(final Channel channel, final boolean debug) throws ReleaseException {
        if (hasListTemplate(channel)) {
            Releaseable release = ReleaseFactory.listRelease();
            if (debug) {
                release.releaseDebug(cfg, generatorDao, channel);
            } else {
                release.release(cfg, generatorDao, channel);
            }
        }
    }

    private boolean hasListTemplate(final Channel channel) {
        return channel.getListTPL() != null;
    }

    private void generatorArticle(final Channel channel, final boolean debug) throws ReleaseException {
        if (hasDetailTemplate(channel)) {
            Releaseable release = ReleaseFactory.articleRelease();
            if (debug) {
                release.releaseDebug(cfg, generatorDao, channel);
            } else {
                release.release(cfg, generatorDao, channel);
            }
        }
    }

    private boolean hasDetailTemplate(final Channel channel) {
        return channel.getDetailTPL() != null;
    }

    private void generatorResource(final int siteId) throws ReleaseException {
        ResourceReleaseable release = ReleaseFactory.resourceRelease();
        release.release(generatorDao, siteId);
    }
    
    @Override
    public void generatorResourceSingle(final int id)throws ReleaseException{
        ResourceReleaseable release = ReleaseFactory.resourceRelease();
        release.releaseSingle(generatorDao, id);
    }
    
    private void generatorResourceTPL(final int siteId)throws ReleaseException{
        ResourceReleaseable release = ReleaseFactory.resourceTPLRelease();
        release.release(generatorDao, siteId);
    }

    @Override
    public void generatorResourceTPLSingle(int id) throws ReleaseException {
        ResourceReleaseable release = ReleaseFactory.resourceTPLRelease();
        release.releaseSingle(generatorDao, id);
    }
    
}
