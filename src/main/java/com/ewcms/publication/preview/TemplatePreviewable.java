/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.preview;

import java.io.OutputStream;

import com.ewcms.core.site.model.Template;

/**
 * 模板预览接口
 * 
 * @author wangwei
 */
public interface TemplatePreviewable {

    public void view(OutputStream out,Template template);
        
}
