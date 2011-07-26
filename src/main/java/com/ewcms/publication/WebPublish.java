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
import com.ewcms.core.site.model.TemplateSource;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 实现web发布服务
 * 
 * @author wangwei
 */
public class WebPublish extends SchedulingPublish implements WebPublishable {

    private static final Logger logger = LoggerFactory.getLogger(WebPublish.class);
    
    /**
     * 得到当前站点编号
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
    
    @Override
    public void publishSite() throws PublishException {
        super.publishSite(getCurrentSiteId());
    }

    @Override
    public void publishSiteAgain() throws PublishException {
        Integer id = getCurrentSiteId();
        Channel root = channelService.getChannelRoot(id);
        publishChannelAgain(root.getId(),true);
    }
    
    @Override
    public void publishSite(Integer id)throws PublishException{
        throw new PublishException("It's forbidden");
    }
    
    /**
     * 判断发布的信息是否当前站点信息
     * 
     * @param siteId 站点编号
     * @return
     */
    private boolean isCurrentSite(Integer siteId)throws PublishException{
        return siteId == getCurrentSiteId();
    }
    
    /**
     * 验证频道发布是否有效
     * <br>
     * 防止恶意发布
     * 
     * @param channel 
     *            频道
     * @throws PublishException
     */
    private void publishChannelEnable(Channel channel)throws PublishException{
        if(channel == null){
            logger.error("channel is null");
            throw new PublishException("Channel is not exits");
        }
        
        int siteId = channel.getSite().getId();
        if(!isCurrentSite(siteId)){
            logger.error("Channel is not current site's it");
            throw new PublishException("Channel is not current site's it");
        }
        
        if(isPublishingNow(channel)){
            logger.error("Channel is still publishing");
            throw new PublishException("Channel is still publishing");
        }
    }
    
    @Override
    public void publishChannel(Integer id,boolean all) throws PublishException {
        publishChannelEnable(channelService.getChannel(id));
        super.publishChannel(id, all);
    }
    
    @Override
    public void publishChannelAgain(Integer id,boolean all) throws PublishException {
        Channel channel = channelService.getChannel(id);
        publishChannelEnable(channel);
         
        resourcePublish.publishSite(id);
        if(all){
            publishChannelAll(channel,true);
        }else{
            publishChannel(channel,true);
        }
    }

    @Override
    public void publishSiteResource() throws PublishException {
        Integer siteId = getCurrentSiteId();
        resourcePublish.publishSite(siteId);
    }
    
    @Override
    public void publishSiteResourceAgain() throws PublishException {
        Integer siteId = getCurrentSiteId();
        resourcePublish.publishSiteAgain(siteId);
    }

    /**
     * 验证模板资源发布是否有效
     * <br>
     * 防止恶意发布
     * 
     * @param source 
     *            模板资源
     * @throws PublishException
     */
    private void publishTemplateSourceEnable(TemplateSource source)throws PublishException{
        if(source == null){
            logger.error("TemplateSource is null");
            throw new PublishException("TemplateSource is not exits");
        }
        if(!isCurrentSite(source.getSite().getId())){
            logger.error("TemplateSource do not current site's it");
            throw new PublishException("TemplateSource do not current site's it");
        }
    }
    
    @Override
    public void publishResourceAgain(Integer id, Boolean templateSource)throws PublishException {
        TemplateSource source = templateSourceService.getTemplateSource(id);
        publishTemplateSourceEnable(source);
        resourcePublish.publishAgain(id, templateSource);
    }
    
}
