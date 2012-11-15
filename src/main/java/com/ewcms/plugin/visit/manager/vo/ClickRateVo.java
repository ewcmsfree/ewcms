package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 点击量来源
 * 
 * @author wu_zhijun
 *
 */
public class ClickRateVo implements Serializable {

	private static final long serialVersionUID = 226978324493678214L;

	/**
	 * 来源组成供DAO使用
	 */
	private String referer;
	private Long refererCount;
	
	/**
	 * 供 来源组成 前台页面使用
	 */
	private String date;
	private Long directCount;
	private Long searchCount;
	private Long otherCount;
	
	/**
	 * 供 搜索引擎 前台页面使用
	 */
	private String domain;
	private String domainName;
	private Long domainCount;
	private String domainRate;
	
	/**
	 * 供 来源网站 前台使用
	 */
	private String webSite;
	private Long webSiteCount;
	private String webSiteRate;
	
	public ClickRateVo() {
	}

	/**
	 * 供 来源组成 DAO使用
	 */
	public ClickRateVo(String referer, Long refererCount) {
		super();
		this.referer = referer;
		this.refererCount = refererCount;
	}
	
	/**
	 * 供 来源组成 前台页面使用
	 */
	public ClickRateVo(String date, Long directCount, Long searchCount,
			Long otherCount) {
		super();
		this.date = date;
		this.directCount = directCount;
		this.searchCount = searchCount;
		this.otherCount = otherCount;
	}

	/**
	 * 供 搜索引擎 前台页面使用
	 */
	public ClickRateVo(String domain, String domainName, Long domainCount, String domainRate) {
		super();
		this.domain = domain;
		this.domainName = domainName;
		this.domainCount = domainCount;
		this.domainRate = domainRate;
	}
	
	/**
	 * 供 来源网站 前台使用
	 */
	public ClickRateVo(String webSite, Long webSiteCount, String webSiteRate) {
		super();
		this.webSite = webSite;
		this.webSiteCount = webSiteCount;
		this.webSiteRate = webSiteRate;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Long getRefererCount() {
		return refererCount;
	}

	public void setRefererCount(Long refererCount) {
		this.refererCount = refererCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getDirectCount() {
		return directCount;
	}

	public void setDirectCount(Long directCount) {
		this.directCount = directCount;
	}

	public Long getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(Long searchCount) {
		this.searchCount = searchCount;
	}

	public Long getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Long otherCount) {
		this.otherCount = otherCount;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Long getDomainCount() {
		return domainCount;
	}

	public void setDomainCount(Long domainCount) {
		this.domainCount = domainCount;
	}

	public String getDomainRate() {
		return domainRate;
	}

	public void setDomainRate(String domainRate) {
		this.domainRate = domainRate;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Long getWebSiteCount() {
		return webSiteCount;
	}

	public void setWebSiteCount(Long webSiteCount) {
		this.webSiteCount = webSiteCount;
	}

	public String getWebSiteRate() {
		return webSiteRate;
	}

	public void setWebSiteRate(String webSiteRate) {
		this.webSiteRate = webSiteRate;
	}
}
