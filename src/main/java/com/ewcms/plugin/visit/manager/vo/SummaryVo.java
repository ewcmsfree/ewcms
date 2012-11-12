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
	private Long pv;
	private Long uv;
	private Long ip;
	private Long rv;
	private String avgTime;
	private String rvRate;
	private String pvRate;
	private Long sumPv;
	
	public SummaryVo(){
		pv = 0L;
		uv = 0L;
		ip = 0L;
		rv = 0L;
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

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public Long getIp() {
		return ip;
	}

	public void setIp(Long ip) {
		this.ip = ip;
	}

	public Long getRv() {
		return rv;
	}

	public void setRv(Long rv) {
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
