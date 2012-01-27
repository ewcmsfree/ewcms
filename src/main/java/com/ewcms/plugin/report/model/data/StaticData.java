/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 静态数据
 * 
 * <ul>
 * <li>value:值</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_report_data_static")
@PrimaryKeyJoinColumn(name = "data_id")
public class StaticData extends Data {

    private static final long serialVersionUID = 358643064266693606L;
    
    @Column(name = "value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
