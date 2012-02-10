/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication;

import java.util.List;

import com.ewcms.publication.task.Taskable;

/**
 * 管理平台发布服务接口
 * 
 * @author wangwei
 */
public interface WebPublishFacable{

    /**
     * 发布站点
     *<br>
     * 重新发布时(again=true)，全站所有资源和内容重新发布。
     * 不重新发布时只发布未发布的资源和内容。
     * 
     * @param again true:重新发布
     * @throws PublishException
     */
    void publishSite(boolean again)throws PublishException;
    
    /**
     * 发布指定频道内容
     * <br>
     *  重新发布时(again=true)，生成频道下的所有内容重新发布。
     *  不重新发布时只发布未发布的内容。可以关联发布子频道。
     *  
     * @param id 频道编号
     * @param again true:重新发布
     * @param children  true:关联发布子频道
     * 
     * @throws PublishException
     */
    void publishChannel(int id,boolean again,boolean children) throws PublishException;
    
    /**
     * 发布站点资源
     * <br>
     *  重新发布时(again=true)，全站所有资源重新发布。
     *  不重新发布时只发布未发布的资源。
     * 
     * @param again true:重新发布
     * @throws PublishException
     */
    void publishSiteResource(boolean again)throws PublishException;

    /**
     * 发布指定的资源
     * <br>
     * 发布指定的资源，不管资源是否发布都会重新发布。
     * 
     * @param ids 资源编号集合
     * @throws PublishException
     */
    void publishResources(int[] ids)throws PublishException;
    
    /**
     * 发布站点模版资源
     * <br>
     *  重新发布时(again=true)，全站所有模版资源重新发布。
     *  不重新发布时只发布未发布的模版资源。
     *  
     * @param again true:重新发布
     * @throws PublishException
     */
    void publishSiteTemplateSource(boolean again) throws PublishException;
    
    /**
     * 发布指定的模版资源
     * <br>
     * 发布指定的模版资源，不管模版资源是否发布都会重新发布。
     * 
     * @param ids 模版资源编号集合
     * @throws PublishException
     */
    void publishTemplateSources(int[] ids)throws PublishException;
    
    /**
     * 发布指定的模版生成的内容
     * <br>
     * 重新发布时(again=true)，指定的模版生成内容重新发布。
     *  不重新发布时只有文章内类型模版发布未发布的文章，其它都会重新发布。
     *  
     * @param id 模版编号
     * @param again 重新发布
     * @throws PublishException
     */
    void publishTemplateContent(int id,boolean again)throws PublishException;
    
    /**
     * 发布指定的文章
     * <br>
     * 只有在预发布状态下的文章才能发布
     * 
     * @param channelId 频道编号
     * @param ids 文章编号集合
     * @throws PublishException
     */
    void publishArticles(int channelId,long[] ids)throws PublishException;
    
    /**
     * 关闭当前的站点发布。
     * <br>
     * 当站点的发布设置发生改变时，必需关闭该站点的发布，只有这样下次发布时新的设置才能生效。
     * 
     * @throws PublishException
     */
    void closePublish()throws PublishException;
    
    /**
     * 得到站点的发布任务
     * 
     * @param siteId 站点编号
     * @return
     */
    List<Taskable> getSitePublishTasks(Integer siteId) ;
}
