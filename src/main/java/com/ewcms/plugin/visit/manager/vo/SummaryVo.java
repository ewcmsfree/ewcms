/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wu_zhijun
 *
 */
public class SummaryVo implements Serializable {

	private static final long serialVersionUID = 9123371582577402503L;

	/**
	 * 综合报告变量
	 */
	private String name;
	private Long pv;
	private Long uv;
	private Long ip;
	private Long rv;
	private String avgTime;
	private String rvRate;
	private String pvRate;
	private Long sumPv;
	//综合报告中最高
	private String betideIp;
	private String betideUv;
	private String betidePv;
	
	/**
	 * 访问记录变量
	 */
	private String ipValue;
	private String country;
	private String province;
	private String city;
	private String url;
	private Date visitDate;
	private Date visitTime;
	private String referer;
	private String browser;
	private String os;
	private String screen;
	private String language;
	private String flashVersion;
	
	/**
	 * 入口(出口)变量
	 */
	private String eeUrl;
	private Long eeCount;
	private String eeRate;

	/**
	 * 在线人数变量
	 */
	private String period;
	private Integer five;
	private Integer ten;
	private Integer fifteen;

	
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
	
	/**
	 * 综合报告构造函数
	 * 
	 * @param name
	 * @param sumPv
	 */
	public SummaryVo(String name, Long sumPv){
		super();
		this.name = name;
		this.sumPv = sumPv;
	}
	
	/**
	 * 访问记录
	 * 
	 * @param ipValue
	 * @param country
	 * @param url
	 * @param visitDate
	 * @param visitTime
	 * @param referer
	 * @param browser
	 * @param os
	 * @param screen
	 * @param language
	 * @param flashVersion
	 */
	public SummaryVo(String ipValue, String country, String province, String city, String url, Date visitDate,
			Date visitTime, String referer, String browser, String os,
			String screen, String language, String flashVersion) {
		super();
		this.ipValue = ipValue;
		this.country = country;
		this.province = province;
		this.city = city;
		this.url = url;
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.referer = referer;
		this.browser = browser;
		this.os = os;
		this.screen = screen;
		this.language = language;
		this.flashVersion = flashVersion;
	}
	
	/**
	 * 入口/出口构造函数
	 *  
	 * @param eeUrl
	 * @param eeCount
	 * @param eeRate
	 */
	public SummaryVo(String eeUrl, Long eeCount, String eeRate) {
		super();
		this.eeUrl = eeUrl;
		this.eeCount = eeCount;
		this.eeRate = eeRate;
	}
	

	/**
	 * 在线人数构造函数
	 * 
	 * @param period
	 * @param five
	 * @param ten
	 * @param fifteen
	 */
	public SummaryVo(String period, Integer five, Integer ten, Integer fifteen) {
		super();
		this.period = period;
		this.five = five;
		this.ten = ten;
		this.fifteen = fifteen;
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

	public String getIpValue() {
		return ipValue;
	}

	public void setIpValue(String ipValue) {
		this.ipValue = ipValue;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFlashVersion() {
		return flashVersion;
	}

	public void setFlashVersion(String flashVersion) {
		this.flashVersion = flashVersion;
	}

	public String getEeUrl() {
		return eeUrl;
	}

	public void setEeUrl(String eeUrl) {
		this.eeUrl = eeUrl;
	}

	public Long getEeCount() {
		return eeCount;
	}

	public void setEeCount(Long eeCount) {
		this.eeCount = eeCount;
	}

	public String getEeRate() {
		return eeRate;
	}

	public void setEeRate(String eeRate) {
		this.eeRate = eeRate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getBetideIp() {
		return betideIp;
	}

	public void setBetideIp(String betideIp) {
		this.betideIp = betideIp;
	}

	public String getBetideUv() {
		return betideUv;
	}

	public void setBetideUv(String betideUv) {
		this.betideUv = betideUv;
	}

	public String getBetidePv() {
		return betidePv;
	}

	public void setBetidePv(String betidePv) {
		this.betidePv = betidePv;
	}
	
	
}
