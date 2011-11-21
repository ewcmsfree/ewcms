/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.factory;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.TableOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.common.convert.ConvertException;
import com.ewcms.common.convert.ConvertFactory;
import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.datasource.generate.factory.DataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.factory.init.EwcmsDataSourceFactoryable;
import com.ewcms.plugin.datasource.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.report.generate.service.chart.ChartGenerationService;
import com.ewcms.plugin.report.generate.util.ParamConversionPage;
import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.ChartType;
import com.ewcms.plugin.report.model.Parameter;

/**
 * @author 吴智俊
 */
@Service
public class ChartFactory implements ChartFactoryable {

	private static final Logger logger = LoggerFactory.getLogger(ChartFactory.class);
    @Autowired
    private EwcmsDataSourceFactoryable ewcmsDataSourceFactory;
    @Autowired
    private DataSource dataSource;

    public void setAlqcDataSourceFactory(EwcmsDataSourceFactoryable alqcDataSourceFactory) {
        this.ewcmsDataSourceFactory = alqcDataSourceFactory;
    }

    public EwcmsDataSourceFactoryable getAlqcDataSourceFactory() {
        return this.ewcmsDataSourceFactory;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 生成图型报表上显示的数据
     */
    static class LabelGenerator extends AbstractCategoryItemLabelGenerator
            implements CategoryItemLabelGenerator {

		private static final long serialVersionUID = -4769271989622322803L;
		
		/** 阈值 */
        private double threshold;

        /**
         * 创建一个新的生成器，只能显示大于或等于阈值的标签。
         *
         * @param threshold 阈值
         */
        public LabelGenerator(double threshold) {
            super("", NumberFormat.getInstance());
            this.threshold = threshold;
        }

        /**
         * 生成一个指定标签。标签通常是格式化的数据值，但任何文字都可以使用。
         *
         * @param dataset 数据源 (<code>null</code> 不合法).
         * @param series
         * @param category
         *
         * @return 标签 (可能 <code>null</code>).
         */
        public String generateLabel(CategoryDataset dataset, int series, int category) {
            String result = null;
            Number value = dataset.getValue(series, category);
            if (value != null) {
                double v = value.doubleValue();
                if (v > this.threshold) {
                    result = value.toString(); // 这里可以使用格式
                }
            }
            return result;
        }
    }

    public byte[] export(ChartReport report, Map<String, String> pageParams) throws Exception {
    	if (report == null){
    		return null;
    	}
        DefaultCategoryDataset dataset = buildDataset(report, pageParams);
        java.awt.Font titleFont = new java.awt.Font(report.getFontName(), report.getFontStyle(), report.getFontSize());
        String chartTitle = report.getChartTitle();
        chartTitle = replaceParam(pageParams, report.getParameters(), chartTitle, false);
        String horizAxisLabel = report.getHorizAxisLabel();
        horizAxisLabel = replaceParam(pageParams, report.getParameters(), horizAxisLabel, false);
        String vertAxisLabel = report.getVertAxisLabel();
        vertAxisLabel = replaceParam(pageParams, report.getParameters(), vertAxisLabel, false);
        Boolean showLegend = report.getShowLegend();
        Boolean showTooltips = report.getShowTooltips();
        Boolean drillThroughEnabled = false;
        ChartType chartType = report.getChartType();

        CategoryURLGenerator urlGenerator = null;

        JFreeChart chart = null;
        switch (chartType) {
            case VERTBAR:
                chart = ChartGenerationService.createBarChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case VERTBAR3D:
                chart = ChartGenerationService.createBarChart3D(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case HORIZBAR:
                chart = ChartGenerationService.createBarChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case HORIZBAR3D:
                chart = ChartGenerationService.createBarChart3D(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case STACKEDVERTBAR:
                chart = ChartGenerationService.createStackedBarChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case STACKEDVERTBAR3D:
                chart = ChartGenerationService.createStackedBarChart3D(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case STACKEDHORIZBAR:
                chart = ChartGenerationService.createStackedBarChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case STACKEDHORIZBAR3D:
                chart = ChartGenerationService.createStackedBarChart3D(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case VERTLINE:
                chart = ChartGenerationService.createLineChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case HORIZLINE:
                chart = ChartGenerationService.createLineChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case VERTAREA:
                chart = ChartGenerationService.createAreaChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case HORIZAREA:
                chart = ChartGenerationService.createAreaChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case VERTSTACKEDAREA:
                chart = ChartGenerationService.createStackedAreaChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.VERTICAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case HORIZSTACKEDAREA:
                chart = ChartGenerationService.createStackedAreaChart(chartTitle, titleFont, horizAxisLabel, vertAxisLabel, dataset, PlotOrientation.HORIZONTAL, showLegend, showTooltips, drillThroughEnabled, urlGenerator);
                break;
            case PIEBYCOLUMN:
                chart = ChartGenerationService.createPieChart(chartTitle, titleFont, dataset, TableOrder.BY_COLUMN, showLegend, showTooltips, drillThroughEnabled, null);
                break;
            case PIEBYROW:
                chart = ChartGenerationService.createPieChart(chartTitle, titleFont, dataset, TableOrder.BY_ROW, showLegend, showTooltips, drillThroughEnabled, null);
                break;
            case PIEBYCOLUMN3D:
                chart = ChartGenerationService.create3DPieChart(chartTitle, titleFont, dataset, TableOrder.BY_COLUMN, showLegend, showTooltips, drillThroughEnabled, null);

                break;
            case PIEBYROW3D:
                chart = ChartGenerationService.create3DPieChart(chartTitle, titleFont, dataset, TableOrder.BY_ROW, showLegend, showTooltips, drillThroughEnabled, null);
                break;
            default:
                throw new BaseException("一个未定义的图表类型", "一个未定义的图表类型");
        }
        try {
            Integer bgColorR = report.getBgColorR();
            Integer bgColorG = report.getBgColorG();
            Integer bgColorB = report.getBgColorB();
            chart.setBackgroundPaint(new java.awt.Color(bgColorR, bgColorG, bgColorB));

            String axisFontName = report.getAxisFontName();
            Integer axisFontStyle = report.getAxisFontStyle();
            Integer axisFontSize = report.getAxisFontSize();
            java.awt.Font axisFont = new java.awt.Font(axisFontName, axisFontStyle, axisFontSize);

            String axisTickFontName = report.getAxisTickFontName();
            Integer axisTickFontStyle = report.getAxisTickFontStyle();
            Integer axisTickFontSize = report.getAxisTickFontSize();
            java.awt.Font axisTickFont = new java.awt.Font(axisTickFontName, axisTickFontStyle, axisTickFontSize);

            String legendFontName = report.getLegendFontName();
            Integer legendFontStyle = report.getLegendFontStyle();
            Integer legendFontSize = report.getLegendFontSize();
            java.awt.Font legendFont = new java.awt.Font(legendFontName, legendFontStyle, legendFontSize);
            Integer tickLabelRotate = report.getTickLabelRotate();

            String dataFontName = report.getDataFontName();
            Integer dataFontStyle = report.getDataFontStyle();
            Integer dataFontSize = report.getDataFontSize();
            java.awt.Font dataFont = new java.awt.Font(dataFontName, dataFontStyle, dataFontSize);

            Plot plot = chart.getPlot();
            if (!(plot instanceof MultiplePiePlot)) {
                CategoryPlot categoryPlot = chart.getCategoryPlot();
                CategoryItemRenderer renderer = categoryPlot.getRenderer();
                renderer.setBaseItemLabelGenerator(new LabelGenerator(0.0));
                renderer.setBaseItemLabelFont(dataFont);
                renderer.setBaseItemLabelsVisible(true);
                if (chartType == ChartType.VERTLINE || chartType == ChartType.HORIZLINE) {
                    LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
                    lineRenderer.setBaseShapesVisible(true);
                    lineRenderer.setDrawOutlines(true);
                    lineRenderer.setUseFillPaint(true);
                }
            }

            if (plot instanceof CategoryPlot) {
                CategoryPlot catPlot = (CategoryPlot) plot;
                catPlot.getDomainAxis().setLabelFont(axisFont);
                catPlot.getRangeAxis().setLabelFont(axisFont);
                catPlot.getDomainAxis().setTickLabelFont(axisTickFont);
                catPlot.getRangeAxis().setTickLabelFont(axisTickFont);
                catPlot.getDomainAxis().setMaximumCategoryLabelWidthRatio(100.0f);
                double angle = -2.0 * Math.PI / 360.0 * (double) tickLabelRotate;
                CategoryLabelPositions oldp = catPlot.getDomainAxis().getCategoryLabelPositions();
                CategoryLabelPositions newp = new CategoryLabelPositions(oldp.getLabelPosition(RectangleEdge.TOP), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT, angle, CategoryLabelWidthType.RANGE, 0.0f), oldp.getLabelPosition(RectangleEdge.LEFT), oldp.getLabelPosition(RectangleEdge.RIGHT));
                catPlot.getDomainAxis().setCategoryLabelPositions(newp);
            } else if (plot instanceof PiePlot3D) {
                PiePlot3D piePlot = (PiePlot3D) plot;
                piePlot.setLabelFont(axisFont);
                piePlot.setDirection(org.jfree.util.Rotation.CLOCKWISE);
                piePlot.setForegroundAlpha(0.5f);
                piePlot.setNoDataMessage("无数据显示");
            } else if (plot instanceof PiePlot) {
                PiePlot piePlot = (PiePlot) plot;
                piePlot.setLabelFont(axisFont);
            } 
            LegendTitle legend = (LegendTitle) chart.getLegend();
            if (legend != null) {
                legend.setItemFont(legendFont);
                RectangleEdge legendRectEdge = RectangleEdge.BOTTOM;
                Integer legendPosition = report.getLegendPosition();
                switch (legendPosition) {
                    case 0:
                        legendRectEdge = RectangleEdge.LEFT;
                        break;
                    case 1:
                        legendRectEdge = RectangleEdge.TOP;
                        break;
                    case 2:
                        legendRectEdge = RectangleEdge.RIGHT;
                        break;
                    case 3:
                        legendRectEdge = RectangleEdge.BOTTOM;
                        break;
                }
                legend.setPosition(legendRectEdge);
            }
        } catch (Exception e) {
            logger.error("Chart Export Exception",e);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsPNG(out, chart, report.getChartWidth(), report.getChartHeight());
        return out.toByteArray();
    }

    public List<PageShowParam> chartParameters(ChartReport report) {
        Assert.notNull(report);
        Set<Parameter> paramSet = report.getParameters();
        return ParamConversionPage.conversion(paramSet);
    }

    /**
     * 构建数据
     *
     * @param reportDataSet 数据源
     * @param sql SQL语句
     * @return DefaultCategoryDataset
     * @throws BaseException
     */
    @SuppressWarnings("rawtypes")
	private DefaultCategoryDataset buildDataset(ChartReport report, Map<String, String> pageParams) throws BaseException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Connection con = null;
        EwcmsDataSourceServiceable service = null;
        int colCount;
        try {
            BaseDS dataSet = report.getBaseDS();
            String executableSQL = report.getChartSql();

			executableSQL = replaceParam(pageParams, report.getParameters(), executableSQL, true);

            if (dataSet == null) {
                con = dataSource.getConnection();
            } else {
                DataSourceFactoryable factory = (DataSourceFactoryable) getAlqcDataSourceFactory().getBean(dataSet.getClass());
                service = factory.createService(dataSet);
                con = service.openConnection();
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(executableSQL);

            colCount = rs.getMetaData().getColumnCount();
            
            if (colCount == 2) {
                while (rs.next()) {
                    try {
                    	try{
                    		dataset.addValue(rs.getDouble(1), "", (Comparable) rs.getObject(2));
                    	}catch(Exception e){
                    		dataset.addValue(rs.getDouble(2), "", (Comparable) rs.getObject(1));
                    	}
//                    	if (rs.getMetaData().getColumnType(1) == Types.NUMERIC){
//                            dataset.addValue(rs.getDouble(1), "", (Comparable) rs.getObject(2));
//                    	}else if (rs.getMetaData().getColumnType(2) == Types.NUMERIC) {
//                            dataset.addValue(rs.getDouble(2), "", (Comparable) rs.getObject(1));
//                    	}
                    } catch (Exception e) {
                    	logger.error("定义的SQL表达式有错误",e);
                    	throw new BaseException("定义的SQL表达式有错误","定义的SQL表达式有错误");
                    }
                }
            } else if (colCount == 3) {
                while (rs.next()) {
                    try {
//                    	log.info(rs.getMetaData().getColumnType(1));
//                    	log.info(rs.getMetaData().getColumnType(2));
//                    	log.info(rs.getMetaData().getColumnType(3));
//                    	if (rs.getMetaData().getColumnType(1) == Types.NUMERIC){
//                            dataset.addValue(rs.getDouble(1), (Comparable) rs.getObject(2), (Comparable) rs.getObject(3));
//                    	}else if (rs.getMetaData().getColumnType(2) == Types.NUMERIC){
//                    		dataset.addValue(rs.getDouble(2), (Comparable) rs.getObject(1), (Comparable) rs.getObject(3));
//                    	}else if (rs.getMetaData().getColumnType(3) == Types.NUMERIC){
//                    		dataset.addValue(rs.getDouble(3), (Comparable) rs.getObject(1), (Comparable) rs.getObject(2));
//                    	}
                    	try{
                    		dataset.addValue(rs.getDouble(3), (Comparable) rs.getObject(1), (Comparable) rs.getObject(2));
                    	}catch(Exception e){
                    		try{
                    			dataset.addValue(rs.getDouble(2), (Comparable) rs.getObject(1), (Comparable) rs.getObject(3));
                    		}catch(Exception ex){
                    			dataset.addValue(rs.getDouble(1), (Comparable) rs.getObject(2), (Comparable) rs.getObject(3));
                    		}
                    	}
                    } catch (Exception e) {
                    	logger.error("定义的SQL表达式有错误", e);
                    	throw new BaseException("定义的SQL表达式有错误","定义的SQL表达式有错误");
                    }
                }
            } else {
                logger.error("SQL表达式小于1或大于2列");
                throw new BaseException("图表需要1或2维的结果", "图表需要1或2维的结果");
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
        	throw new BaseException(e.toString(),e.toString());
        } catch (ConvertException e){
        	throw new BaseException(e.toString(),e.toString());
        } catch (ClassNotFoundException e){
        	throw new BaseException(e.toString(),e.toString());
		} finally {
            if (service != null) {
                service.closeConnection();
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
                con = null;
            }
        }
        return dataset;
    }

    /**
     * 用值替换参数
     *
     * @param chartParam
     * @param sql
     * @return
     * @throws ConvertException 
     * @throws ClassNotFoundException 
     * @throws TypeHandlerException
     */
    @SuppressWarnings("unchecked")
	private String replaceParam(Map<String, String> pageParams, Set<Parameter> paramSets, String expression, Boolean isSql) throws ConvertException, ClassNotFoundException {
        if (paramSets == null || paramSets.size() == 0) {
            return expression;
        }
        Map<String, Object> chartParam = new HashMap<String, Object>();
        if (pageParams == null || pageParams.size() == 0){
	        for (Parameter param : paramSets) {
	            String value = param.getDefaultValue();
	            if (value == null) {
	                continue;
	            }
	            
	            String className = param.getClassName();
	            Class<Object> forName = (Class<Object>)Class.forName(className);
	            Object paramValue = ConvertFactory.instance.convertHandler(forName).parse(value);
	            chartParam.put(param.getEnName(), paramValue);
	        }
        }else{
	        for (Parameter param : paramSets) {
	            String value = pageParams.get(param.getEnName());
	            if (value == null) {
	                continue;
	            }
	            String className = param.getClassName();
	            Class<Object> forName = (Class<Object>)Class.forName(className);
	            Object paramValue = ConvertFactory.instance.convertHandler(forName).parse(value);
	            chartParam.put(param.getEnName(), paramValue);
	        }
        }
        int beginIndex = 0;
        int endIndex = 0;
        StringBuffer sb = new StringBuffer(expression);
        for (int i = 0; i < sb.length(); i++) {
            String p = sb.substring(i, i + 1);
            if (p.equals("$")) {
                beginIndex = i;
                continue;
            }
            if (p.equals("}")) {
                endIndex = i;
            }
            if (endIndex > beginIndex) {
                String temp = sb.substring(beginIndex, endIndex + 1);
                Iterator<Entry<String, Object>> iterator = chartParam.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> e = (Map.Entry<String, Object>) iterator.next();
                    if (temp.indexOf(e.getKey(), 0) != -1) {
                        Object value = e.getValue();
                        if (isSql) {
                            if (value instanceof String) {
                                sb = sb.replace(beginIndex, endIndex + 1, "'" + value + "'");
                            } else if (value instanceof Date){
                            	sb = sb.replace(beginIndex, endIndex + 1, "'" + value + "'");
                            } else {
                                sb = sb.replace(beginIndex, endIndex + 1, "" + value + "");
                            }
                        } else {
                            sb = sb.replace(beginIndex, endIndex + 1, "" + value + "");
                        }
                    }
                }
                beginIndex = endIndex;
            }
        }
        return sb.toString();
    }
}
