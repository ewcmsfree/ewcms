package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

public class VisitorVo implements Serializable {

	private static final long serialVersionUID = 8239296389487291761L;

	/**
	 * 回头率
	 */
	private String name;
	private Long newVisitor;
	private Long returnVisitor;
	private Double returningRage;
	
	/**
	 * 停留时间
	 */
	private String date;
	private Long stSum;
	private Long stAvg;

	public VisitorVo(){
	}
	
	
	/**
	 * 回头率
	 */
	public VisitorVo(String name, Long newVisitor, Long returnVisitor,
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
	public VisitorVo(String date, Long stSum, Long stAvg){
		this.date = date;
		this.stSum = stSum;
		this.stAvg = stAvg;
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
