/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.service;

import java.util.List;

import com.ewcms.core.site.model.Channel;

/**
 * 频道加载接口
 * <br>
 * 生成页面时需要依赖频道。
 * 
 * @author wangwei
 */
public interface ChannelPublishServiceable {

    /**
     * 得到站点顶级频道
     * 
     * @param siteId 站点编号
     * @return
     */
    public Channel getChannelRoot(Integer siteId);
    
    /**
     * 通过频道编号得到频道对象
     * 
     * @param id 频道编号
     * @return 频道对象
     */
    public Channel getChannel(Integer id);
    
    /**
     * 通过频道编号得到频道对象
     * <br>
     * 频道必须是指定站点的频道。
     * 
     * @param siteId 站点编号
     * @param id 频道编号
     * @return 频道对象
     */
    public Channel getChannel(Integer siteId,Integer id);
    
    /**
     * 得到所属子频道
     * 
     * @param id 频道编号
     * @return
     */
    public List<Channel> getChannelChildren(Integer id);
    
    /**
     * 得到所属频道父频道编号 
     * 
     * @param id 频道编号
     * @return
     */
    public Channel getChannelParent(Integer id);
    
    /**
     * 通过频道访问链接地址和路径的频道对象
     * 
     * @param siteId 站点编号
     * @param path 频道链接地址或路径
     * @return 频道对象
     */
    public Channel getChannelByUrlOrPath(Integer siteId,String path);
}
