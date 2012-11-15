package com.ewcms.plugin.visit.manager.web;

import com.ewcms.web.EwcmsBaseAction;

public class VisitBaseAction extends EwcmsBaseAction {

	private static final long serialVersionUID = -5897929783524090700L;

	private String startDate;
	private String endDate;
	private Integer labelCount = 8;
	private Integer siteId = getCurrentSite().getId();

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getLabelCount() {
		return labelCount;
	}

	public void setLabelCount(Integer labelCount) {
		this.labelCount = labelCount;
	}

	public Integer getSiteId(){
		return siteId;
	}
}
