package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 访问频率 
 * 
 * @author wu_zhijun
 *
 */
public class LoyaltyVo implements Serializable {

	private static final long serialVersionUID = -2225902827138838824L;

	private Long frequency;
	private Long freqCount;
	private String rate;

	public LoyaltyVo(){
	}
	
	public LoyaltyVo(Long frequency, Long freqCount){
		super();
		this.frequency = frequency;
		this.freqCount = freqCount;
	}
	
	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Long getFreqCount() {
		return freqCount;
	}

	public void setFreqCount(Long freqCount) {
		this.freqCount = freqCount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}
