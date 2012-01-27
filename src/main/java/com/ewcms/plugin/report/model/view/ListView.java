/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model.view;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 下拉列表视图
 * 
 * <ul>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "plugin_report_view_list")
@PrimaryKeyJoinColumn(name = "view_id")
public class ListView extends ComponentView {

    private static final long serialVersionUID = -8292541693497269685L;
}
