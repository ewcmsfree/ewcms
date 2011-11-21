/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.plugin.datasource.model.BaseDS;

/**
 * 图表
 * <ul>
 * <li>id:编号</li>
 * <li>name:名称</li>
 * <li>sql:SQL表达式</li>
 * <li>chartType:类型</li>
 * <li>alqcDataSource:连接数据源对象（与AlqcDataSource对象一对一关联）</li>
 * <li>showTooltips:工具提示</li>
 * <li>chartTitle:标题</li>
 * <li>fontName:标题字体名</li>
 * <li>fontStyle:标题字型</li>
 * <li>fontSize:标题字号</li>
 * <li>dataFontName:数据字体名</li>
 * <li>dataFontStyle:数据字型</li>
 * <li>dataFontSize:数据字号</li>
 * <li>horizAxisLabel:横坐标轴标题</li>
 * <li>vertAxisLabel:纵坐标轴标题</li>
 * <li>axisFontName:坐标轴字体名称</li>
 * <li>axisFontStyle:坐标轴字型</li>
 * <li>axisFontSize:坐标轴字号</li>
 * <li>axisTickFontName:坐标轴尺值字体名</li>
 * <li>axisTickFontStyle:坐标轴尺值字型</li>
 * <li>axisTickFontSize:坐标轴尺值字号</li>
 * <li>tickLabelRotate:数据轴数据标签角度</li>
 * <li>showLegend:图示说明</li>
 * <li>legendPosition:图示上下对齐方式</li>
 * <li>legendFontName:图示字体名</li>
 * <li>legendFontStyle:图示字型</li>
 * <li>legendFontSize:图示字号</li>
 * <li>chartHeight:高度</li>
 * <li>chartWidth:宽度</li>
 * <li>bgColorR:背景红</li>
 * <li>bgColorG:背景绿</li>
 * <li>bgColorB:背景蓝</li>
 * <li>remakrs:备注</li>
 * <li>categories:分类集合</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "repo_chart")
@SequenceGenerator(name = "seq_repo_chart", sequenceName = "seq_repo_chart_id", allocationSize = 1)
public class ChartReport implements Serializable {

    private static final long serialVersionUID = -2358576914939775115L;
    
	@Id
    @GeneratedValue(generator = "seq_repo_chart",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;
    @Column(name = "chartsql", columnDefinition = "text")
    private String chartSql;
    @Column(name = "chartType")
    @Enumerated(EnumType.STRING)
    private ChartType chartType;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = BaseDS.class)
    @JoinColumn(name = "base_ds_id")
    private BaseDS baseDS;
    @Column(name = "showTooltips")
    private Boolean showTooltips;
    @Column(name = "chartTitle")
    private String chartTitle;
    @Column(name = "fontName")
    private String fontName;
    @Column(name = "fontStyle")
    private Integer fontStyle;
    @Column(name = "fontSize")
    private Integer fontSize;
    @Column(name = "datafontname")
    private String dataFontName;
    @Column(name = "datafontstyle")
    private Integer dataFontStyle;
    @Column(name = "datafontsize")
    private Integer dataFontSize;
    @Column(name = "horizAxisLabel")
    private String horizAxisLabel;
    @Column(name = "vertAxisLabel")
    private String vertAxisLabel;
    @Column(name = "axisFontName")
    private String axisFontName;
    @Column(name = "axisFontStyle")
    private Integer axisFontStyle;
    @Column(name = "axisFontSize")
    private Integer axisFontSize;
    @Column(name = "axisTickFontName")
    private String axisTickFontName;
    @Column(name = "axisTickFontStyle")
    private Integer axisTickFontStyle;
    @Column(name = "axisTickFontSize")
    private Integer axisTickFontSize;
    @Column(name = "tickLabelRotate")
    private Integer tickLabelRotate;
    @Column(name = "showlegend")
    private Boolean showLegend;
    @Column(name = "legendPosition")
    private Integer legendPosition;
    @Column(name = "legendFontName")
    private String legendFontName;
    @Column(name = "legendFontStyle")
    private Integer legendFontStyle;
    @Column(name = "legendFontSize")
    private Integer legendFontSize;
    @Column(name = "chartheight")
    private Integer chartHeight;
    @Column(name = "chartwidth")
    private Integer chartWidth;
    @Column(name = "bgColorR")
    private Integer bgColorR;
    @Column(name = "bgColorG")
    private Integer bgColorG;
    @Column(name = "bgColorB")
    private Integer bgColorB;
    @Column(name = "remarks",columnDefinition = "text")
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Parameter.class, orphanRemoval = true)
    @JoinColumn(name = "chart_id")
    @OrderBy("id")
    private Set<Parameter> parameters = new LinkedHashSet<Parameter>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChartSql() {
        return chartSql;
    }

    public void setChartSql(String chartSql) {
        this.chartSql = chartSql;
    }

    public ChartType getChartType() {
        return chartType;
    }
    
	public String getTypeDescription(){
		if (chartType != null){
			return chartType.getDescription();
		}else{
			return ChartType.VERTBAR.getDescription();
		}
	}

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public BaseDS getBaseDS() {
        return baseDS;
    }

    public void setBaseDS(BaseDS baseDS) {
        this.baseDS = baseDS;
    }

    public Boolean getShowTooltips() {
        return showTooltips == null ? false : showTooltips;
    }

    public void setShowTooltips(Boolean showTooltips) {
        this.showTooltips = showTooltips;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getFontName() {
        return fontName == null ? "宋体" : fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Integer getFontStyle() {
        return fontStyle == null ? java.awt.Font.BOLD : fontStyle;
    }

    public void setFontStyle(Integer fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Integer getFontSize() {
        return fontSize == null ? 18 : fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getDataFontName() {
        return dataFontName == null ? "宋体" : dataFontName;
    }

    public void setDataFontName(String dataFontName) {
        this.dataFontName = dataFontName;
    }

    public Integer getDataFontStyle() {
        return dataFontStyle == null ? java.awt.Font.PLAIN : dataFontStyle;
    }

    public void setDataFontStyle(Integer dataFontStyle) {
        this.dataFontStyle = dataFontStyle;
    }

    public Integer getDataFontSize() {
        return dataFontSize == null ? 12 : dataFontSize;
    }

    public void setDataFontSize(Integer dataFontSize) {
        this.dataFontSize = dataFontSize;
    }

    public String getHorizAxisLabel() {
        return horizAxisLabel == null ? "" : horizAxisLabel;
    }

    public void setHorizAxisLabel(String horizAxisLabel) {
        this.horizAxisLabel = horizAxisLabel;
    }

    public String getVertAxisLabel() {
        return vertAxisLabel == null ? "" : vertAxisLabel;
    }

    public void setVertAxisLabel(String vertAxisLabel) {
        this.vertAxisLabel = vertAxisLabel;
    }

    public String getAxisFontName() {
        return axisFontName == null ? "宋体" : axisFontName;
    }

    public void setAxisFontName(String axisFontName) {
        this.axisFontName = axisFontName;
    }

    public Integer getAxisFontStyle() {
        return axisFontStyle == null ? java.awt.Font.PLAIN : axisFontStyle;
    }

    public void setAxisFontStyle(Integer axisFontStyle) {
        this.axisFontStyle = axisFontStyle;
    }

    public Integer getAxisFontSize() {
        return axisFontSize == null ? 12 : axisFontSize;
    }

    public void setAxisFontSize(Integer axisFontSize) {
        this.axisFontSize = axisFontSize;
    }

    public String getAxisTickFontName() {
        return axisTickFontName == null ? "宋体" : axisTickFontName;
    }

    public void setAxisTickFontName(String axisTickFontName) {
        this.axisTickFontName = axisTickFontName;
    }

    public Integer getAxisTickFontStyle() {
        return axisTickFontStyle == null ? java.awt.Font.PLAIN
                : axisTickFontStyle;
    }

    public void setAxisTickFontStyle(Integer axisTickFontStyle) {
        this.axisTickFontStyle = axisTickFontStyle;
    }

    public Integer getAxisTickFontSize() {
        return axisTickFontSize == null ? 12 : axisTickFontSize;
    }

    public void setAxisTickFontSize(Integer axisTickFontSize) {
        this.axisTickFontSize = axisTickFontSize;
    }

    public Integer getTickLabelRotate() {
        return tickLabelRotate == null ? 30 : tickLabelRotate;
    }

    public void setTickLabelRotate(Integer tickLabelRotate) {
        this.tickLabelRotate = tickLabelRotate;
    }

    public Boolean getShowLegend() {
        return showLegend == null ? true : showLegend;
    }

    public void setShowLegend(Boolean showLegend) {
        this.showLegend = showLegend;
    }

    public Integer getLegendPosition() {
        return legendPosition == null ? 3 : legendPosition;
    }

    public void setLegendPosition(Integer legendPosition) {
        this.legendPosition = legendPosition;
    }

    public String getLegendFontName() {
        return legendFontName == null ? "宋体" : legendFontName;
    }

    public void setLegendFontName(String legendFontName) {
        this.legendFontName = legendFontName;
    }

    public Integer getLegendFontStyle() {
        return legendFontStyle == null ? java.awt.Font.PLAIN : legendFontStyle;
    }

    public void setLegendFontStyle(Integer legendFontStyle) {
        this.legendFontStyle = legendFontStyle;
    }

    public Integer getLegendFontSize() {
        return legendFontSize == null ? 12 : legendFontSize;
    }

    public void setLegendFontSize(Integer legendFontSize) {
        this.legendFontSize = legendFontSize;
    }

    public Integer getChartHeight() {
        return chartHeight == null ? 300 : chartHeight;
    }

    public void setChartHeight(Integer chartHeight) {
        this.chartHeight = chartHeight;
    }

    public Integer getChartWidth() {
        return chartWidth == null ? 500 : chartWidth;
    }

    public void setChartWidth(Integer chartWidth) {
        this.chartWidth = chartWidth;
    }

    public Integer getBgColorR() {
        return bgColorR == null ? 255 : bgColorR;
    }

    public void setBgColorR(Integer bgColorR) {
        this.bgColorR = bgColorR;
    }

    public Integer getBgColorG() {
        return bgColorG == null ? 255 : bgColorG;
    }

    public void setBgColorG(Integer bgColorG) {
        this.bgColorG = bgColorG;
    }

    public Integer getBgColorB() {
        return bgColorB == null ? 255 : bgColorB;
    }

    public void setBgColorB(Integer bgColorB) {
        this.bgColorB = bgColorB;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonIgnore
    public Set<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChartReport other = (ChartReport) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
