package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 忠诚度分析
 * 
 * @author wu_zhijun
 *
 */
public class LoyaltyVo implements Serializable {

	private static final long serialVersionUID = -2225902827138838824L;

	/**
	 * 访问深度/访问频率变量
	 */
	private Long frequency;
	private Long freqCount;
	private String rate;

	/**
	 * 回头率变量
	 */
	private String name;
	private Long newVisitor;
	private Long returnVisitor;
	private Double returningRage;
	
	/**
	 * 停留时间变量
	 */
	private String date;
	private Long stSum;
	private Long stAvg;
	
	public LoyaltyVo(){
	}
	
	/**
	 * 构造函数
	 * 
	 * @param frequency
	 * @param freqCount
	 */
	public LoyaltyVo(Long frequency, Long freqCount){
		super();
		this.frequency = frequency;
		this.freqCount = freqCount;
	}
	
	/**
	 * 回头率
	 */
	public LoyaltyVo(String name, Long newVisitor, Long returnVisitor,
			Double returningRage) {
		super();
		this.name = name;
		this.newVisitor = newVisitor;
		this.returnVisitor = returnVisitor;
		this.returningRage = returningRage;
	}
	
	/**
	 * 停留时间
	 */
	public LoyaltyVo(String date, Long stSum, Long stAvg){
		super();
		this.date = date;
		this.stSum = stSum;
		this.stAvg = stAvg;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNewVisitor() {
		return newVisitor;
	}

	public void setNewVisitor(Long newVisitor) {
		this.newVisitor = newVisitor;
	}

	public Long getReturnVisitor() {
		return returnVisitor;
	}

	public void setReturnVisitor(Long returnVisitor) {
		this.returnVisitor = returnVisitor;
	}

	public Double getReturningRage() {
		return returningRage;
	}

	public void setReturningRage(Double returningRage) {
		this.returningRage = returningRage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getStSum() {
		return stSum;
	}

	public void setStSum(Long stSum) {
		this.stSum = stSum;
	}

	public Long getStAvg() {
		return stAvg;
	}

	public void setStAvg(Long stAvg) {
		this.stAvg = stAvg;
	}
}
