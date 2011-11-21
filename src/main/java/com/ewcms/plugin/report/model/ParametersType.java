/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

/**
 * 报表参数类型
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
public enum ParametersType {

    BOOLEAN("布尔型"), TEXT("文本型"), LIST("列表型"), CHECK("选择型"), DATE("日期型"), SESSION("登录用户型"), SQL("SQL语句");
    
    private String description;

    private ParametersType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
