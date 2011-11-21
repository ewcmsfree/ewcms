/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

/**
 * @author 吴智俊
 */
public enum ChartType {

    VERTBAR("垂直柱状图"), VERTBAR3D("垂直3D柱状图"), HORIZBAR("水平柱状图"), HORIZBAR3D(
    "水平3D柱状图"), STACKEDVERTBAR("垂直堆叠柱状图"), STACKEDVERTBAR3D("垂直3D堆叠柱状图"), STACKEDHORIZBAR(
    "水平堆叠柱状图"), STACKEDHORIZBAR3D("水平3D堆叠柱状图"), VERTLINE("垂直线图"), HORIZLINE(
    "水平线图"), VERTAREA("垂直区域图"), HORIZAREA("水平区域图"), VERTSTACKEDAREA(
    "垂直堆叠区域图"), HORIZSTACKEDAREA("水平堆叠区域图"), PIEBYCOLUMN("以数据行显示饼图"), PIEBYROW(
    "以数据列显示饼图"), PIEBYCOLUMN3D("以数据行显示3D饼图"), PIEBYROW3D("以数据列显示3D饼图");
    private String description;

    private ChartType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
