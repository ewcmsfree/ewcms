/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.ewcms.plugin.datasource.manager.BaseDSFacable;
import com.ewcms.plugin.datasource.model.BaseDS;
import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.manager.util.ChartUtil;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.ChartType;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.plugin.report.model.ParametersType;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.ComboBox;

/**
 * 
 * @author wuzhijun
 * 
 */
public class ChartReportAction extends CrudBaseAction<ChartReport, Long> {

	private static final long serialVersionUID = -877919389056277148L;

	@Autowired
	private ReportFacable reportFac;
	@Autowired
	private BaseDSFacable baseDSFac;
	@Autowired
	private ChartFactoryable chartFactory;

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public ChartReport getReportChartVo() {
		return super.getVo();
	}

	public void setReportChartVo(ChartReport reportChart) {
		super.setVo(reportChart);
	}

	@Override
	protected Long getPK(ChartReport vo) {
		return vo.getId();
	}

	@Override
	protected ChartReport getOperator(Long pk) {
		return reportFac.findByChart(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.deletedChart(pk);
	}

	@Override
	protected Long saveOperator(ChartReport vo, boolean isUpdate) {
		vo.setBaseDS(baseDSFac.findByBaseDS(vo.getBaseDS().getId()));
		if (isUpdate) {
			return reportFac.updateChart(vo);
		} else {
			return reportFac.saveChart(vo);
		}
	}

	@Override
	protected ChartReport createEmptyVo() {
		return new ChartReport();
	}

	/**
	 * 数据源选择
	 * 
	 * @return 记录集
	 */
	public List<BaseDS> getBaseDSList() {
		List<BaseDS> list = new ArrayList<BaseDS>();
		try {
			list = baseDSFac.findAllBaseDS();
		} catch (Exception e) {
		}
		return list;
	}

	public void parameterType() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (ParametersType paramEnum : ParametersType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", paramEnum.name());
			map.put("text", paramEnum.getDescription());
			list.add(map);
		}

		Struts2Util.renderJson(JSONUtil.toJSON(list));
	}

	/**
	 * 图型类型选择
	 * 
	 * @return 记录集
	 */
	public List<ChartType> getChartTypeList() {
		return Arrays.asList(ChartType.values());
	}

	public Map<String, String> getFontNameMap() {
		return ChartUtil.getFontNameMap();
	}

	public Map<Integer, String> getFontStyleMap() {
		return ChartUtil.getFontStyleMap();
	}

	public Map<Integer, Integer> getFontSizeMap() {
		return ChartUtil.getFontSizeMap();
	}

	public Map<Integer, String> getRotateMap() {
		return ChartUtil.getRotateMap();
	}

	public Map<Integer, String> getPositionMap() {
		return ChartUtil.getPositionMap();
	}

	public Map<Integer, String> getAlignmentMap() {
		return ChartUtil.getAlignmentMap();
	}

	private Long chartId;

	public Long getChartId() {
		return chartId;
	}

	public void setChartId(Long chartId) {
		this.chartId = chartId;
	}

	public void preview() {
		PrintWriter pw = null;
		InputStream in = null;
		try {
			ChartReport chart = reportFac.findByChart(chartId);
			Assert.notNull(chart);

			Set<Parameter> parameters = chart.getParameters();
			Map<String, String> map = new HashMap<String, String>();
			for (Parameter param : parameters) {
				map.put(param.getEnName(), param.getDefaultValue());
			}

			HttpServletResponse response = ServletActionContext.getResponse();
			pw = response.getWriter();

			response.reset();// 清空输出
			response.setContentLength(0);
			byte[] bytes = chartFactory.export(chart, map);
			response.setContentLength(bytes.length);
			response.setHeader("Content-Type", "image/png");
			in = new ByteArrayInputStream(bytes);
			int len = 0;
			while ((len = in.read()) > -1) {
				pw.write(len);
			}
			pw.flush();
		} catch (Exception e) {
		} finally {
			if (pw != null) {
				try {
					pw.close();
					pw = null;
				} catch (Exception e) {
				}
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (Exception e) {
				}
			}
		}
	}

	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void findReportChart() {
		List<ChartReport> charts = reportFac.findAllChart();
		if (charts != null) {
			List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
			ComboBox comboBox = null;
			for (ChartReport chart : charts) {
				comboBox = new ComboBox();
				comboBox.setId(chart.getId());
				comboBox.setText(chart.getName());
				if (getCategoryId() != null) {
					Boolean isEntity = reportFac
							.findChartIsEntityByChartAndCategory(chart.getId(),
									getCategoryId());
					if (isEntity)
						comboBox.setSelected(true);
				}
				comboBoxs.add(comboBox);
			}
			Struts2Util.renderJson(JSONUtil.toJSON(comboBoxs
					.toArray(new ComboBox[0])));
		}
	}
}
