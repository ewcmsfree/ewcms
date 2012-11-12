package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 
 * @author wu_zhijun
 *
 */
public class SummaryVo implements Serializable {

	private static final long serialVersionUID = 9123371582577402503L;

	private String name;
	private Integer pv;
	private Integer uv;
	private Integer ip;
	private Integer rv;
	private String avgTime;
	private String rvRate;
	private String pvRate;
	private Long sumPv;
	
	public SummaryVo(){
		pv = 0;
		uv = 0;
		ip = 0;
		rv = 0;
		avgTime = "";
		rvRate = "";
		pvRate = "";
		sumPv = 0L;
	}
	
	public SummaryVo(String name, Long sumPv){
		this.name = name;
		this.sumPv = sumPv;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getUv() {
		return uv;
	}

	public void setUv(Integer uv) {
		this.uv = uv;
	}

	public Integer getIp() {
		return ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
	}

	public Integer getRv() {
		return rv;
	}

	public void setRv(Integer rv) {
		this.rv = rv;
	}

	public String getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(String avgTime) {
		this.avgTime = avgTime;
	}

	public String getRvRate() {
		return rvRate;
	}

	public void setRvRate(String rvRate) {
		this.rvRate = rvRate;
	}

	public String getPvRate() {
		return pvRate;
	}

	public void setPvRate(String pvRate) {
		this.pvRate = pvRate;
	}

	public Long getSumPv() {
		return sumPv;
	}

	public void setSumPv(Long sumPv) {
		this.sumPv = sumPv;
	}
}
