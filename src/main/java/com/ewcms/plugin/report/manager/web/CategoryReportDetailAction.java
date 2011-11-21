package com.ewcms.plugin.report.manager.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.CategoryReport;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author wuzhijun
 * 
 */
public class CategoryReportDetailAction extends ActionSupport {

	private static final long serialVersionUID = 2270713965369157617L;

	@Autowired
	private ReportFacable reportFac;

	private Long categoryId;
	private Long[] reportTextIds;
	private Long[] reportChartIds;
	private CategoryReport reportCategoryVo;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long[] getReportTextIds() {
		return reportTextIds;
	}

	public void setReportTextIds(Long[] reportTextIds) {
		this.reportTextIds = reportTextIds;
	}

	public Long[] getReportChartIds() {
		return reportChartIds;
	}

	public void setReportChartIds(Long[] reportChartIds) {
		this.reportChartIds = reportChartIds;
	}

	public CategoryReport getReportCategoryVo() {
		return reportCategoryVo;
	}

	public void setReportCategoryVo(CategoryReport reportCategoryVo) {
		this.reportCategoryVo = reportCategoryVo;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public String input() {
		CategoryReport vo = reportFac.findByCategory(getCategoryId());

		Set<ChartReport> reportCharts = vo.getCharts();
		List<Long> chartIds = new ArrayList<Long>();
		for (ChartReport reportChart : reportCharts) {
			chartIds.add(reportChart.getId());
		}
		setReportChartIds(chartIds.toArray(new Long[0]));

		Set<TextReport> reportTexts = vo.getTexts();
		List<Long> textIds = new ArrayList<Long>();
		for (TextReport reportText : reportTexts) {
			textIds.add(reportText.getId());
		}
		setReportTextIds(textIds.toArray(new Long[0]));

		setReportCategoryVo(vo);
		return INPUT;
	}

	public String save() {
		reportFac.saveChartToCategories(getCategoryId(), getReportChartIds());
		reportFac.saveTextToCategories(getCategoryId(), getReportTextIds());
		return SUCCESS;
	}

	public Map<Long, String> getReportText() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<TextReport> texts = reportFac.findAllText();
		for (TextReport text : texts) {
			map.put(text.getId(), text.getTextName());
		}
		return map;
	}

	public Map<Long, String> getReportChart() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<ChartReport> charts = reportFac.findAllChart();
		for (ChartReport chart : charts) {
			map.put(chart.getId(), chart.getName());
		}
		return map;
	}
}
