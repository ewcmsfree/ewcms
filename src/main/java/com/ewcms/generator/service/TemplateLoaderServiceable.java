/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.service;

import com.ewcms.core.site.model.Template;

/**
 * 模板加载服务
 * <br>
 * 提供生成页面所需要的模板。
 * 
 * @author wangwei
 */
public interface TemplateLoaderServiceable {

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
    String getUniquePathOfChannelTemplate(int siteId,int channelId,String name);
}
