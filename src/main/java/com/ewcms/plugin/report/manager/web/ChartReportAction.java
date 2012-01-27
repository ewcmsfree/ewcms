/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.externalds.manager.BaseDSFacable;
import com.ewcms.plugin.externalds.model.BaseDS;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.manager.util.ChartUtil;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.Parameter;
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

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public ChartReport getChartReportVo() {
		return super.getVo();
	}

	public void setChartReportVo(ChartReport chartReport) {
		super.setVo(chartReport);
	}

	@Override
	protected Long getPK(ChartReport vo) {
		return vo.getId();
	}

	@Override
	protected ChartReport getOperator(Long pk) {
		return reportFac.findChartReportById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		reportFac.delChartReport(pk);
	}

	@Override
	protected Long saveOperator(ChartReport vo, boolean isUpdate) {
		vo.setBaseDS(baseDSFac.findByBaseDS(vo.getBaseDS().getId()));
		if (isUpdate) {
			return reportFac.updChartReport(vo);
		} else {
			return reportFac.addChartReport(vo);
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

		for (Parameter.Type paramEnum : Parameter.Type.values()) {
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
	public List<ChartReport.Type> getChartTypeList() {
		return Arrays.asList(ChartReport.Type.values());
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

	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void findChartReport() {
		List<ChartReport> charts = reportFac.findAllChartReport();
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
