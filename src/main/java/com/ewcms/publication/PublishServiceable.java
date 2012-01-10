/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication;

/**
 * 发布服务接口
 * 
 * @author wangwei
 */
public interface PublishServiceable {

    /**
     * 发布指定的模版资源
     * </br>
     * 如果模版资源已发布，则会重新发布
     * 
     * @param siteId 站点编号
     * @param ids 模版资源编号集合
     * @param username 用户名
     * @throws PublishException
     */
    void publishTemplateSource(int siteId,int[] ids,String username)throws PublishException;
    
    /**
     * 发布站点中的模版资源
     * 
     * @param siteId 站点编号
     * @param again 重新发布
     * @param username 用户名
     * @throws PublishException
     */
    void publishTemplateSourceBySite(int siteId,boolean again,String username)throws PublishException;
    
    /**
     * 发布指定的资源
     * </br>
    * 如果资源已发布，则会重新发布
     * 
     * @param siteId 站点编号
     * @param ids 资源编号集合
     * @param username 用户名
     * @throws PublishException
     */
    void publishResource(int siteId,int[] ids,String username)throws PublishException;
    
    /**
     * 发布站点中的资源
     * <br>
     * 发布文章内容资源
     * 
     * @param siteId 站点编号
     * @param again 重新发布
     * @param username 用户名
     * @throws PublishException
     */
    void publishResourceBySite(int siteId,boolean again,String username)throws PublishException;
    
    /**
     * 发布模版对应生成的页面
     *  
     * @param templateId 模版编号
     * @param again 重新发布
     * @param username 用户名
     * @throws PublishException
     */
    void publishTemplate(int templateId,boolean again,String username)throws PublishException;
    
    /**
     * 发布频道下生成的页面
     * </br>
     * 依赖频道下的模版
     * 
     * @param channelId 频道编号
     * @param children 发布子频道
     * @param again 重新发布
     * @param username 用户名
     * @throws PublishException
     */
    void publishChannel(int channelId,boolean children,boolean again,String username) throws PublishException;
    
    /**
     * 发布站点
     * 
     * @param siteId 站点编号
     * @param again 重新发布
     * @param username 用户名
     * @throws PublishException
     */
    void publishSite(int siteId,boolean again,String username)throws PublishException;
    
    /**
     * 发布指定的文章
     * </br>
     * 只有预发布和发布状态文章才能发布，如果文章发布会再次发布。
     * 
     * @param channelId 频道编号
     * @param ids 文章编号集合
     * @param username 用户名
     * @throws PublishException
     */
    void publishArticle(int channelId,long[] ids,String username)throws PublishException;
    
    /**
     * 删除发布任务
     * 
     * @param siteId 站点编号
     * @param id 任务编号
     * @param username 用户名
     * @throws PublishException
     */
    void removePublish(Integer siteId,String id,String username)throws PublishException;
    
    /**
     * 关闭指定的站点发布。
     * <br>
     * 当站点的发布设置发生改变时，必需关闭该站点的发布，只有这样下次发布时新的设置才能生效。
     * 
     * @param siteId 站点编号
     */
    void closeSitePublish(Integer siteId);
}
