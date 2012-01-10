/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现管理平台发布服务
 * 
 * @author wangwei
 */
public class WebPublishFac  implements WebPublishFacable {

    private static final Logger logger = LoggerFactory.getLogger(WebPublishFac.class);
    
    private PublishServiceable publishService;
    private ChannelPublishServiceable channelService;
    private TemplatePublishServiceable templateService;
    
    /**
     * 得到操作站点编号
     * 
     * @return 站点编号
     * @throws PublishException
     */
    private Integer getCurrentSiteId()throws PublishException{
        Site site = EwcmsContextUtil.getCurrentSite();
        if(site == null || site.getId() == null){
            logger.debug("Current Site is not exist");
            throw new PublishException("Current Site is not exist");
        }
        return site.getId();
    }
    
    /**
     * 得到操作用户名
     * 
     * @return 用户名
     * @throws PublishException
     */
    private String getCurrentUsername(){
        return EwcmsContextUtil.getUserName();
    }
    
  

    @Override
    public void publishSite(boolean again) throws PublishException {
        publishService.publishSite(getCurrentSiteId(), again, getCurrentUsername());
    }

    /**
     * 验证频道发布是否有效
     * <br>
     * 防止恶意发布
     * 
     * @param id  频道编号
     * @throws PublishException
     */
    private Channel publishChannelEnable(int id)throws PublishException{
        Channel channel = channelService.getChannel(id);
        if(channel == null){
            logger.error("channel is null");
            throw new PublishException("Channel is not exits");
        }
        int siteId = channel.getSite().getId();
        if(siteId != getCurrentSiteId().intValue()){
            logger.error("Channel is not current site's it");
            throw new PublishException("Channel is not current site's it");
        }
        if(!channel.getPublicenable()){
            logger.error("Channel was not publish");
            throw new PublishException("Channel was not publish");
        }
        return channel;
    }
    
    @Override
    public void publishChannel(int id, boolean again, boolean children)throws PublishException {
        publishChannelEnable(id);
        publishService.publishChannel(id, children, again, getCurrentUsername());
        
    }

    @Override
    public void publishSiteResource(boolean again) throws PublishException {
        publishService.publishResourceBySite(getCurrentSiteId(), again, getCurrentUsername());
    }

    @Override
    public void publishResources(int[] ids) throws PublishException {
        publishService.publishResource(getCurrentSiteId(), ids, getCurrentUsername());
    }

    @Override
    public void publishSiteTemplateSource(boolean again)throws PublishException {
        publishService.publishTemplateSourceBySite(getCurrentSiteId(), again, getCurrentUsername());
    }

    @Override
    public void publishTemplateSources(int[] ids) throws PublishException {
        publishService.publishTemplateSource(getCurrentSiteId(), ids, getCurrentUsername());
    }

    @Override
    public void publishTemplateContent(int id, boolean again)throws PublishException {
        Template template = templateService.getTemplate(id);
        publishChannelEnable(template.getChannelId());
        publishService.publishTemplate(id, again, getCurrentUsername());
    }

    @Override
    public void publishArticles(int channelId,long[] ids) throws PublishException {
        publishChannelEnable(channelId);
        publishService.publishArticle(channelId, ids, getCurrentUsername());
    }
    
    @Override
    public void closePublish()throws PublishException {
        Integer siteId = getCurrentSiteId();
        publishService.closeSitePublish(siteId);
    }
    
    public void setPublishService(PublishServiceable publishService){
        this.publishService = publishService;
    }
    
    public void setChannelService(ChannelPublishServiceable channelService){
        this.channelService = channelService;
    }
    
    public void setTemplateService(TemplatePublishServiceable templateService){
        this.templateService = templateService;
    }
}
