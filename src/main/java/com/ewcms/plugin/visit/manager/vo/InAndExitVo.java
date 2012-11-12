package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 入口(出口)对象
 * 
 * @author wu_zhijun
 * 
 */
public class InAndExitVo implements Serializable {
	
	private static final long serialVersionUID = -9076439039318486609L;
	
	private String url;
	private Long count;
	private String rate;

	public InAndExitVo() {
		url = "";
		count = 0L;
		rate = "100.00%";
	}

	public InAndExitVo(String url, Long count) {
		this.url = url;
		this.count = count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
