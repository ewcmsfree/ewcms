/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.scheduling.SchedulingPublish;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现web发布服务
 * 
 * @author wangwei
 */
@Service
public class WebPublish extends SchedulingPublish implements WebPublishable {

    private static final Logger logger = LoggerFactory.getLogger(WebPublish.class);
    
    @Autowired
    private ArticlePublishServiceable articleService;
    @Autowired
    protected ChannelPublishServiceable channelService;
    
    @Override
    public void publishChannel(Integer id,boolean all) throws PublishException {
        publishChannelEnable(channelService.getChannel(id));
    }
    
    @Override
    public void publishChannelAgain(Integer id,boolean all) throws PublishException {
        articleService.againPublishArticle(id);
        publishChannel(id,all);
    }

    @Override
    public void publishSiteResource(Integer id) throws PublishException {
        resourcePublish.publishSite(id);
    }
    
    @Override
    public void publishSiteResourceAgain(Integer id) throws PublishException {
        resourcePublish.publishSiteAgain(id);
    }

    @Override
    public void publishResourceAgain(Integer id, Boolean templateSource)throws PublishException {
        resourcePublish.publishAgain(id, templateSource);
    }
    
    /**
     * 验证发布是否有效
     * 
     * @param siteId
     *            站点编号
     * @param channelId
     *            频道编号
     * @throws PublishException
     */
    private void publishChannelEnable(Channel channel)throws PublishException{
        if(channel == null){
            logger.error("channel is null");
            throw new PublishException("Channel is not exits");
        }
        int siteId = channel.getSite().getId();
        if(!isCurrentSite(siteId)){
            logger.error("Not current site");
            throw new PublishException("Not current site");
        }
        if(isPublishing(channel)){
            logger.error("Channel is still publishing");
            throw new PublishException("Channel is still publishing");
        }
    }
    
    /**
     * 判断发布的信息是否当前站点信息
     * <br>
     * 防止恶意发布
     * 
     * @param siteId 站点编号
     * @return
     */
    private boolean isCurrentSite(Integer siteId){
        Site site = EwcmsContextUtil.getCurrentSite();
        if(site == null || site.getId() == null){
            logger.debug("current site id {} ,but site id {}",site.getId() ,siteId);
            return false;
        }
        return siteId == site.getId();
    }

}
