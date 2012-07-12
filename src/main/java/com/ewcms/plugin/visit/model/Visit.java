/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.model;

import java.io.Serializable;
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

/**
 * 访问统计
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>url:</li>
 * <li>referer:</li>
 * <li>ip:IP地址</li>
 * <li>uvFlag:UV标志</li>
 * <li>rvFlag:RV标志</li>
 * <li>uniqueID:编号</li>
 * <li>ipFlag:IP标志</li>
 * <li>screen:分辨率</li>
 * <li>colorDepth:色彩深度</li>
 * <li>language:语言</li>
 * <li>userAgent:用户代理</li>
 * <li>os:操作系统</li>
 * <li>javaEnabled:java启用</li>
 * <li>flashEnabled:flash启用</li>
 * <li>flashVersion:flash版本</li>
 * <li>cookieEnable:cookie启用</li>
 * <li>type:类型</li>
 * <li>catalogInnerCode:</li>
 * <li>leafID:</li>
 * <li>siteID:</li>
 * <li>visitTime:访问时间</li>
 * <li>host:主机</li>
 * <li>event:</li>
 * <li>browser:浏览器</li>
 * <li>district:</li>
 * <li>stickTime:停留时间</li>
 * <li>frequency:频率</li>
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
	@GeneratedValue(generator = "seq_plugin_visit", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "url")
	private String url;
	@Column()
	private String referer;
	@Column()
	private String ip;
	@Column()
	private Boolean uvFlag;
	@Column()
	private Boolean rvFlag;
	@Column()
	private String uniqueID;
	@Column()
	private Boolean ipFlag;
	@Column()
	private String screen;
	@Column()
	private String colorDepth;
	@Column()
	private String language;
	@Column()
	private String userAgent;
	@Column()
	private String os;
	@Column()
	private Boolean javaEnabled;
	@Column()
	private Boolean flashEnabled;
	@Column()
	private String flashVersion;
	@Column()
	private Boolean cookieEnabled;
	@Column()
	private String type;
	@Column()
	private Integer channelId;
	@Column()
	private Long leafID;
	@Column()
	private Long siteID;
	@Column()
	private Long visitTime;
	@Column()
	private String host;
	@Column()
	private String event;
	@Column()
	private String browser;
	@Column()
	private String district;
	@Column()
	private Long stickTime;
	@Column()
	private Long frequency;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getUvFlag() {
		return uvFlag;
	}

	public void setUvFlag(Boolean uvFlag) {
		this.uvFlag = uvFlag;
	}

	public Boolean getRvFlag() {
		return rvFlag;
	}

	public void setRvFlag(Boolean rvFlag) {
		this.rvFlag = rvFlag;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public Boolean getIpFlag() {
		return ipFlag;
	}

	public void setIpFlag(Boolean ipFlag) {
		this.ipFlag = ipFlag;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Long getLeafID() {
		return leafID;
	}

	public void setLeafID(Long leafID) {
		this.leafID = leafID;
	}

	public Long getSiteID() {
		return siteID;
	}

	public void setSiteID(Long siteID) {
		this.siteID = siteID;
	}

	public Long getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Long visitTime) {
		this.visitTime = visitTime;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getStickTime() {
		return stickTime;
	}

	public void setStickTime(Long stickTime) {
		this.stickTime = stickTime;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
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
