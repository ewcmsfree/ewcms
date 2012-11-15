package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 客户端情况
 * 
 * @author wu_zhijun
 * 
 */
public class ClientVo implements Serializable {

	private static final long serialVersionUID = -7540940161691154477L;

	private String name;
	private Long pvCount;
	private String pvRate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPvCount() {
		return pvCount;
	}

	public void setPvCount(Long pvCount) {
		this.pvCount = pvCount;
	}

	public String getPvRate() {
		return pvRate;
	}

	public void setPvRate(String pvRate) {
		this.pvRate = pvRate;
	}
}
