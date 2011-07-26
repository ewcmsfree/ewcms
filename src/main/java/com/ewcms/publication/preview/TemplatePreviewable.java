/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import java.io.OutputStream;

import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;

/**
 * 模板预览接口
 * 
 * @author wangwei
 */
public interface TemplatePreviewable {

    /**
     * 查看模板生成
     * 
     * @param out       
     *          输出数据流
     * @param template
     *          模板
     * @param mock
     *          是否模拟 
     * @throws PublishException
     */
    public void view(OutputStream out,Template template,Boolean mock)throws PublishException;
        
}
