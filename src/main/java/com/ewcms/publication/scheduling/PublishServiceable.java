/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.scheduling;

import com.ewcms.publication.PublishException;


/**
 * 发布服务接口
 * 
 * @author wangwei
 */
public interface PublishServiceable {

    /**
     * 发布站点
     * <br>
     * 发布站点下所有资源、文章和频道页面。
     * 
     * @param id 站点编号
     * @throws PublishException
     */
    void publishSite(Integer id)throws PublishException;
    
    /**
     * 发布和生成频道下html页面
     * <br>
     * 只发布发生改变的频道，如频道文章或关联频道数据改变。
     * 
     * @param id 频道编号
     * @param all 发布所有的子频道
     * 
     * @throws PublishException
     */
    void publishChannel(Integer id,boolean all) throws PublishException;
    
}
