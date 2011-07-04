/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.release.html;

import java.util.List;

import com.ewcms.core.site.model.Template;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.ResourceInfo;

import freemarker.template.Configuration;

/**
 * 生成html页面接口
 * 
 * @author wangwei
 */
public interface GeneratorHtmlable {
 
    /**
     * 根据模板生成频道页面
     * 
     * @param cfg
     *          Freemarker配置对象
     * @param template
     *          系统设置模板
     * @param channelId
     *          频道编号
     * @return 生成页面的资源对象
     * @throws ReleaseException
     */
    public List<ResourceInfo> process(Configuration cfg,Template template,int channelId)throws ReleaseException;
    
    
}
