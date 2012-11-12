package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 最近访问记录对象
 * 
 * @author wu_zhijun
 *
 */
public class LastVisitVo implements Serializable {

	private static final long serialVersionUID = -3678303069641484340L;

	private String ip;
	private String country;
	private String url;
	private Date visitDate;
	private Date visitTime;
	private String referer;
	private String browser;
	private String os;
	private String screen;
	private String language;
	private String flashVersion;

	public LastVisitVo(String ip, String country, String url, Date visitDate,
			Date visitTime, String referer, String browser, String os,
			String screen, String language, String flashVersion) {
		super();
		this.ip = ip;
		this.country = country;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
}
