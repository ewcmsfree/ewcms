/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

/**
 * 访问统计
 * 
 * <ul>
 * <li>uniqueID:编号</li>
 * <li>screen:分辨率</li>
 * <li>colorDepth:色彩深度</li>
 * <li>language:语言</li>
 * <li>userAgent:用户代理</li>
 * <li>os:操作系统</li>
 * <li>javaEnabled:java是否启用</li>
 * <li>flashEnabled:flash是否启用</li>
 * <li>flashVersion:flash版本</li>
 * <li>cookieEnable:cookie是否启用</li>
 * <li>browser:浏览器</li>
 * <li>ip:ip</li>
 * <li>country:国家</li>
 * <li>province:省份</li>
 * <li>city:城市</li>
 * <li>siteId:站点编号</li>
 * <li>addDate:访问日期</li>
 * <li>rvFlag:回头标志</li>
 * </ul>
 * 
 * @author wuzhijun
 * 
 */
@Entity
@Table(name = "plugin_visit")
@SequenceGenerator(name = "seq_plugin_visit", sequenceName = "seq_plugin_visit_id", allocationSize = 1)
public class Visit implements Serializable {

	private static final long serialVersionUID = -4173049848036627669L;

	@Id
    @GeneratedValue(generator = "seq_plugin_visit",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "unique_id")
	@Index(name = "plugin_visit_unique_id_idx")
	private String uniqueId;
	@Column(name = "screen")
	private String screen;
	@Column(name = "color_depth")
	private String colorDepth;
	@Column(name = "language")
	private String language;
	@Column(name = "user_agent")
	private String userAgent;
	@Column(name = "os")
	private String os;
	@Column(name = "java_enabled")
	private Boolean javaEnabled;
	@Column(name = "flash_enabled")
	private Boolean flashEnabled;
	@Column(name = "falsh_version")
	private String flashVersion;
	@Column(name = "cookie_enabled")
	private Boolean cookieEnabled;
	@Column(name = "browser")
	private String browser;
	@Column(name = "ip")
	private String ip;
	@Column(name = "country")
	private String country;
	@Column(name = "province")
	private String province;
	@Column(name = "city")
	private String city;
	@Column(name = "site_id")
	private Integer siteId;
	@Temporal(TemporalType.DATE)
	@Column(name = "add_date")
	private Date addDate;
	@Column(name = "rv_flag")
	private Boolean rvFlag;

	public Visit(){
		addDate = new Date(Calendar.getInstance().getTime().getTime());
		rvFlag = false;
		country = "未知";
		province = "未知";
		city = "未知";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getColorDepth() {
		return colorDepth;
	}

	public void setColorDepth(String colorDepth) {
		this.colorDepth = colorDepth;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Boolean getJavaEnabled() {
		return javaEnabled;
	}

	public void setJavaEnabled(Boolean javaEnabled) {
		this.javaEnabled = javaEnabled;
	}

	public Boolean getFlashEnabled() {
		return flashEnabled;
	}

	public void setFlashEnabled(Boolean flashEnabled) {
		this.flashEnabled = flashEnabled;
	}

	public String getFlashVersion() {
		return flashVersion;
	}

	public void setFlashVersion(String flashVersion) {
		this.flashVersion = flashVersion;
	}

	public Boolean getCookieEnabled() {
		return cookieEnabled;
	}

	public void setCookieEnabled(Boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
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

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Boolean getRvFlag() {
		return rvFlag;
	}

	public void setRvFlag(Boolean rvFlag) {
		this.rvFlag = rvFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visit other = (Visit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
