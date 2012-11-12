package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

public class OnlineVo implements Serializable {

	private static final long serialVersionUID = 5557909574983685425L;

	private String name;
	private Integer five;
	private Integer ten;
	private Integer fifteen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFive() {
		return five;
	}

	public void setFive(Integer five) {
		this.five = five;
	}

	public Integer getTen() {
		return ten;
	}

	public void setTen(Integer ten) {
		this.ten = ten;
	}

	public Integer getFifteen() {
		return fifteen;
	}

	public void setFifteen(Integer fifteen) {
		this.fifteen = fifteen;
	}
}
