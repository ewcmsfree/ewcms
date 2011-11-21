/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.generate.service.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedAreaRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.TableOrder;

/**
 * 图型报表
 * 
 * @author 吴智俊
 */
public class ChartGenerationService {

    /**
     * 多个图表饼图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param data 数据源
     * @param order
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 多个图表饼图
     */
    public static JFreeChart createPieChart(String title, java.awt.Font titleFont, CategoryDataset data, TableOrder order, boolean legend, boolean tooltips, boolean urls, PieURLGenerator urlGenerator) {
        MultiplePiePlot plot = new MultiplePiePlot(data);
        plot.setDataExtractOrder(order);

        PiePlot pp = (PiePlot) plot.getPieChart().getPlot();
        // pp.setInsets(new Insets(0, 5, 5, 5));
        pp.setBackgroundPaint(null);
        pp.setOutlineStroke(null);
        // plot.setOutlineStroke(null);
        PieToolTipGenerator tooltipGenerator = null;
        if (tooltips) {
            tooltipGenerator = new StandardPieToolTipGenerator();
        }

        // PieURLGenerator urlGenerator = null;
        if (!urls) {
            urlGenerator = null;
        }

        pp.setToolTipGenerator(tooltipGenerator);
        pp.setLabelGenerator(null);
        pp.setURLGenerator(urlGenerator);

        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 3D饼图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param data 数据源
     * @param order
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 饼图
     */
    public static JFreeChart create3DPieChart(String title, java.awt.Font titleFont, CategoryDataset data, TableOrder order, boolean legend, boolean tooltips, boolean urls, PieURLGenerator urlGenerator) {

        MultiplePiePlot plot = new MultiplePiePlot(data);
        plot.setDataExtractOrder(order);

        // plot.setOutlineStroke(null);

        JFreeChart pieChart = new JFreeChart(new PiePlot3D(null));
        pieChart.setBackgroundPaint(null);
        plot.setPieChart(pieChart);

        PiePlot3D pp = (PiePlot3D) plot.getPieChart().getPlot();
        pp.setBackgroundPaint(null);
        // pp.setInsets(new Insets(0, 5, 5, 5));

        pp.setOutlineStroke(null);

        PieToolTipGenerator tooltipGenerator = null;
        if (tooltips) {
            tooltipGenerator = new StandardPieToolTipGenerator();
        }

        if (!urls) {
            urlGenerator = null;
        }

        pp.setToolTipGenerator(tooltipGenerator);
        pp.setLabelGenerator(null);
        pp.setURLGenerator(urlGenerator);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;
    }

    /**
     * 垂直条形图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 垂直条形图
     */
    public static JFreeChart createBarChart(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);
        BarRenderer renderer = new BarRenderer();

        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
            renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
            renderer.setBaseItemURLGenerator(urlGenerator);
        }
        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 垂直条形3D图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 垂直的三维效果条形图
     */
    public static JFreeChart createBarChart3D(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis3D(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis3D(valueAxisLabel);

        BarRenderer3D renderer = new BarRenderer3D();

        // renderer.setLabelGenerator(new StandardCategoryLabelGenerator());
        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
            renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
        	renderer.setBaseItemURLGenerator(urlGenerator);
        }

        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis, renderer);
        plot.setOrientation(orientation);
        plot.setForegroundAlpha(0.75f);

        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 堆叠垂直条形图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param domainAxisLabel 域标签
     * @param rangeAxisLabel 值域标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 堆叠垂直条形图
     */
    public static JFreeChart createStackedBarChart(String title, java.awt.Font titleFont, String domainAxisLabel, String rangeAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis(domainAxisLabel);
        ValueAxis valueAxis = new NumberAxis(rangeAxisLabel);

        // create the renderer...
        StackedBarRenderer renderer = new StackedBarRenderer();
        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
            renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
        	renderer.setBaseItemURLGenerator(urlGenerator);
        }

        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 堆叠垂直条形3D图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 堆叠垂直条形3D图
     */
    public static JFreeChart createStackedBarChart3D(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis3D(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis3D(valueAxisLabel);

        CategoryItemRenderer renderer = new StackedBarRenderer3D();
        CategoryToolTipGenerator toolTipGenerator = null;
        if (tooltips) {
            toolTipGenerator = new StandardCategoryToolTipGenerator();
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
            renderer.setBaseItemURLGenerator(urlGenerator);
        }
//        renderer.setToolTipGenerator(toolTipGenerator);
        renderer.setBaseToolTipGenerator(toolTipGenerator);

        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);

        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 线图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 线图
     */
    public static JFreeChart createLineChart(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
//        renderer.setLinesVisible(true);
        renderer.setBaseLinesVisible(true);
//        renderer.setShapesVisible(false);
        renderer.setBaseShapesVisible(false);
        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        	renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
        	renderer.setBaseItemURLGenerator(urlGenerator);
        }
        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 面积图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 面积图
     */
    public static JFreeChart createAreaChart(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        categoryAxis.setCategoryMargin(0.0);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);
        AreaRenderer renderer = new AreaRenderer();
        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
            renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
        	renderer.setBaseItemURLGenerator(urlGenerator);
        }
        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 堆叠面积图
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param categoryAxisLabel 分类标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param orientation 方向
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     * @param urlGenerator
     *
     * @return 堆叠面积图
     */
    public static JFreeChart createStackedAreaChart(String title, java.awt.Font titleFont, String categoryAxisLabel, String valueAxisLabel, CategoryDataset data, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls, CategoryURLGenerator urlGenerator) {
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        StackedAreaRenderer renderer = new StackedAreaRenderer();
        if (tooltips) {
//            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        	renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        }
        if (urls) {
//            renderer.setItemURLGenerator(urlGenerator);
        	renderer.setBaseItemURLGenerator(urlGenerator);
        }

        CategoryPlot plot = new CategoryPlot(data, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, titleFont, plot, legend);

        return chart;

    }

    /**
     * 时间序列图
     * <P>
     * 时间序列图是一个XYPlot的日期轴（横向）和一些轴（垂直） ，每个数据项是与一条线。
     * <P>
     * 注意:您可以提供TimeSeriesCollection以这种方法，因为它实现了XYDataset接口。
     *
     * @param title 标题
     * @param titleFont 标题字体
     * @param timeAxisLabel 日期标签
     * @param valueAxisLabel 值标签
     * @param data 数据源
     * @param legend 图例
     * @param tooltips 是否生成图示工具提示
     * @param urls 是否生成URL
     *
     * @return 时间序列图
     */
    public static JFreeChart createTimeSeriesChart(String title, java.awt.Font titleFont, String timeAxisLabel, String valueAxisLabel, XYDataset data, boolean legend, boolean tooltips, boolean urls) {
        ValueAxis timeAxis = new DateAxis(timeAxisLabel);
        timeAxis.setLowerMargin(0.02);

        timeAxis.setUpperMargin(0.02);
        NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
        valueAxis.setAutoRangeIncludesZero(false);
        XYPlot plot = new XYPlot(data, timeAxis, valueAxis, null);

        XYToolTipGenerator tooltipGenerator = null;
        if (tooltips) {
            tooltipGenerator = StandardXYToolTipGenerator.getTimeSeriesInstance();
            // new StandardXYToolTipGenerator(DateFormat.getDateInstance());
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }

        plot.setRenderer(new StandardXYItemRenderer(
                StandardXYItemRenderer.LINES, tooltipGenerator, urlGenerator));

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

        return chart;

    }
}
