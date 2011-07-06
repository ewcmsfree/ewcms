/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.util.List;

import com.ewcms.core.site.model.Template;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.output.OutputResource;

/**
 * 生成html页面接口
 * 
 * @author wangwei
 */
public interface GeneratorHtmlable {
 
    /**
     * 生成临时文件名称指定的范围
     */
    static final String FILE_NAME_CHARS = "1234567890abcdefghigklmnopqrstuvwxyz";

    /**
     * 根据模板生成频道页面
     * 
     * @param template
     *          模板设置
     *          
     * @return 生成页面的资源集合
     * @throws ReleaseException
     */
    public List<OutputResource> process(Template template)throws ReleaseException;
    
    
}
