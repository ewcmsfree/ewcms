/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.service;

import java.util.List;

import com.ewcms.core.site.model.Template;

/**
 * 模板加载服务
 * <br>
 * 提供生成页面所需要的模板。
 * 
 * @author wangwei
 */
public interface TemplatePublishServiceable {

    /**
     * 得到模板对象
     * 
     * @param id b模板编号
     * @return 
     */
    Template getTemplate(Integer id);
    
    /**
     * 得到频道下所有模板
     * <br>
     * 得到已经发布模板，如有文章模板必需排在第一个
     * 
     * @param id 频道频道编号
     * @return 模板对象
     */
    List<Template> getTemplatesInChannel(Integer id);
    
    /**
     * 通过UniquePath得到模板，模板不存在返回null值
     * 
     * @param path 模板唯一路径
     * @return
     */
    Template getTemplateByUniquePath(String path);
    
    /**
     * 得到频道模板唯一路径
     * 
     * @param siteId 站点编号
     * @param path 路径
     * @param name 模板名称
     * @return
     */
    String getUniquePathOfChannelTemplate(Integer siteId,Integer channelId,String name);
}
