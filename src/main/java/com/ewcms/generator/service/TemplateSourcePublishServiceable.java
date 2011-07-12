/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.service;

import java.util.List;

import com.ewcms.core.site.model.TemplateSource;

/**
 * 模板资源加载和操作接口
 * <br>
 * 提供发布模板资源所需要的数据，并更改以发布文章状态。
 * 
 * @author wangwei
 */
public interface TemplateSourcePublishServiceable {

    /**
     * 查询需要发布的模板资源
     * 
     * @param siteId 站点编号
     * @return 模板资源列表
     */
    List<TemplateSource> findReleaseTemplateSources(Integer siteId);
    
    /**
     * 得到模板资源
     * 
     * @param id 模板资源编号
     * @return 模板资源
     */
    TemplateSource getTemplateSource(Integer id);
    
    /**
     * 得到所属模板资源的子模板资源
     * 
     * @param id 模板资源编号
     * @return
     */
    List<TemplateSource> getTemplateSourceChildren(Integer id);
    
    /**
     * 更新模板资源为发布状态
     * 
     * @param id 模板资源编号
     */
    void releaseTemplateSource(Integer id);
}
