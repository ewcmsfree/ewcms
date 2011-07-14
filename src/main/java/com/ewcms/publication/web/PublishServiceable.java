/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.web;

import com.ewcms.publication.PublishException;


/**
 * 发布服务接口
 * 
 * @author wangwei
 */
public interface PublishServiceable {

    /**
     * 发布和生成频道下html页面
     * <br>
     * 只发布发生改变的频道，如频道文章或关联频道数据改变。
     * 
     * @param id 频道编号
     * @throws PublishException
     */
    void publishChannel(Integer id) throws PublishException;

    /**
     * 重新发布和生成频道下html页面
     * <br>
     * 频道下的内容都会重新生成和发布。
     * 
     * @param id 频道编号
     * @throws PublishException
     */
    void publishChannelAgain(Integer id) throws PublishException;
    
    /**
     * 发布和生成单模板的html页面
     * <br>
     * 模板对应的页面都会从新生成和发布。
     *     
     * @param id 模板编号
     * @throws PublishException
     */
    void publishTemplate(Integer id) throws PublishException;
    
    /**
     * 发布站点资源
     * <br>
     * 发布站点下未发布所有资源。
     * 
     * @param id 站点编号
     * @throws PublishException
     */
    void publishSiteResource(Integer id)throws PublishException;
    
    /**
     * 重新发布站点资源
     * <br>
     * 重新发布站点所有资源，不管是否已经发布。
     * 
     * @param id 站点编号
     * @throws PublishException
     */
    void publishSiteResourceAgain(Integer id)throws PublishException;

    /**
     * 发布单个资源
     * <br>
     * 发布单个资源，不管它是否已经发布。
     * 
     * @param id 资源编号
     * @param templateSource 模板资源
     * @throws PublishException
     */
    void publishResourceAgain(Integer id,Boolean templateSource)throws PublishException;
}
