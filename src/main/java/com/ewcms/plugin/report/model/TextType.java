/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

/**
 *  报表类型枚举
 *  
 * @author 吴智俊
 */
public enum TextType {

    HTML("HTML"), PDF("PDF"), XLS("XLS"), RTF("RTF"), XML("XML");
    
    private String description;

    private TextType(String description) {
        this.description = description;
    }

    /**
     * 描述状态
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }
}
