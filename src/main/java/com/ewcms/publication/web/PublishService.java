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
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现发布服务
 * 
 * @author wangwei
 */
@Service
public class PublishService extends com.ewcms.publication.scheduling.PublishService implements PublishServiceable {

    private static final Logger logger = LoggerFactory.getLogger(PublishService.class);
    
    @Autowired
    private ChannelPublishServiceable channelService;
    @Autowired
    private ArticlePublishServiceable articleService;
    @Autowired
    private TemplatePublishServiceable templateService;
    
    @Override
    public void publishTemplate(Integer id) throws PublishException {
        Template template = templateService.getTemplate(id);
        if(template == null){
            logger.warn("Template is not exist. Id = {}.",id);
            return ;
        }
        if(!isCurrentSite(template.getSite().getId())){
            //判断
        }
        Channel channel = channelService.getChannel(template.getChannelId());
        if(channel == null){
            logger.warn("Channel is not exist. Id = {}.",template.getChannelId());
            return ;
        }
        publishChannelTemplate(channel.getSite(),channel,template);
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
            return false;
        }
        return siteId == site.getId();
    }

}
