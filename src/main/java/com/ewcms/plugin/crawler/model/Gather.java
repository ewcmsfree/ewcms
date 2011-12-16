/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 
 * 采集器信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:名称</li>
 * <li>description:描述</li>
 * <li>status:状态(true:启用,false:停用)</li>
 * <li>maxCount:内容页最大采集数</li>
 * <li>depth:采集深度</li>
 * <li>threadCount:采集线程数</li>
 * <li>timeOutWait:超时等待时间</li>
 * <li>dateFormat:发布日期格式</li>
 * <li>downloadFile:下载内容中的文件</li>
 * <li>removeHref:移除内容中的链接</li>
 * <li>removeHtmlTag:移除内容中的HTML标签</li>
 * <li>channelId:采集到此频道</li>
 * <li>baseURI:域名地址</li>
 * <li>domains:URL层级对象集合</li>
 * <li>matchBlocks:匹配块对象集合</li>
 * <li>filterBlocks:过滤块对象集合</li>
 * <li>htmlType:页面类型</li>
 * <li>proxy:代理服务器</li>
 * <li>proxyHost:服务器地址</li>
 * <li>proxyPort:端口</li>
 * <li>proxyUserName:用户名</li>
 * <li>proxyPassWord:密码</li>
 * <li>encoding:页面编码格式</li>
 * <li>titleExternal:外部标题(true:自定义内容标题,false:使用html内容中的title标题)</li>
 * <li>titleRegex:标题表达式</li>
 * <li>
 * </ul>
 * 
 * @author wuzhijun
 * 
 */
@Entity
@Table(name = "cwl_gather")
@SequenceGenerator(name = "seq_cwl_gather", sequenceName = "seq_cwl_gather_id", allocationSize = 1)
public class Gather implements Serializable {

	private static final long serialVersionUID = -6421132072889992004L;

	@Id
	@GeneratedValue(generator = "seq_cwl_gather", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	@Column(name = "description", columnDefinition = "text")
	private String description;
	@Column(name = "status")
	private Boolean status;
	@Column(name = "max_page")
	private Integer maxPage;
	@Column(name = "depth")
	private Integer depth;
	@Column(name = "threadcount")
	private Integer threadCount;
	@Column(name = "timeoutwait")
	private Integer timeOutWait;
	@Column(name = "downloadFile")
	private Boolean downloadFile;
	@Column(name = "removeHref")
	private Boolean removeHref;
	@Column(name = "removeHtmlTag")
	private Boolean removeHtmlTag;
	@Column(name = "channel_id")
	private Integer channelId;
	@Column(name = "base_uri")
	private String baseURI;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Domain.class, orphanRemoval = true)
	@JoinColumn(name = "gather_id")
	@OrderBy(value = "level")
	private List<Domain> domains = new ArrayList<Domain>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = MatchBlock.class, orphanRemoval = true)
	@JoinColumn(name = "gather_id")
	private List<MatchBlock> matchBlocks = new ArrayList<MatchBlock>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = FilterBlock.class, orphanRemoval = true)
	@JoinColumn(name = "gather_id")
	private List<FilterBlock> filterBlocks = new ArrayList<FilterBlock>();
	@Column(name = "html_type")
	private String htmlType;
	@Column(name = "proxy")
	private Boolean proxy;
	@Column(name = "proxy_host")
	private String proxyHost;
	@Column(name = "proxy_port")
	private Integer proxyPort;
	@Column(name = "proxy_username")
	private String proxyUserName;
	@Column(name = "proxy_password")
	private String proxyPassWord;
	@Column(name = "encoding")
	private String encoding;
	@Column(name = "title_external")
	private Boolean titleExternal;
	@Column(name = "title_regex")
	private String titleRegex;

	public Gather(){
		maxPage = -1;
		depth = -1;
		threadCount = 30;
		timeOutWait = 10;
		htmlType = "html";
		proxy = false;
		downloadFile = false;
		removeHref = false;
		removeHtmlTag = false;
		encoding = "UTF-8";
		titleExternal = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}

	public Integer getTimeOutWait() {
		return timeOutWait;
	}

	public void setTimeOutWait(Integer timeOutWait) {
		this.timeOutWait = timeOutWait;
	}

	public Boolean getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(Boolean downloadFile) {
		this.downloadFile = downloadFile;
	}

	public Boolean getRemoveHref() {
		return removeHref;
	}

	public void setRemoveHref(Boolean removeHref) {
		this.removeHref = removeHref;
	}

	public Boolean getRemoveHtmlTag() {
		return removeHtmlTag;
	}

	public void setRemoveHtmlTag(Boolean removeHtmlTag) {
		this.removeHtmlTag = removeHtmlTag;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	@JsonIgnore
	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}
	
	@JsonIgnore
	public List<MatchBlock> getMatchBlocks() {
		return matchBlocks;
	}

	public void setMatchBlocks(List<MatchBlock> matchBlocks) {
		this.matchBlocks = matchBlocks;
	}

	@JsonIgnore
	public List<FilterBlock> getFilterBlocks() {
		return filterBlocks;
	}

	public void setFilterBlocks(List<FilterBlock> filterBlocks) {
		this.filterBlocks = filterBlocks;
	}

	public String getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public Boolean getProxy() {
		return proxy;
	}

	public void setProxy(Boolean proxy) {
		this.proxy = proxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyPassWord() {
		return proxyPassWord;
	}

	public void setProxyPassWord(String proxyPassWord) {
		this.proxyPassWord = proxyPassWord;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Boolean getTitleExternal() {
		return titleExternal;
	}

	public void setTitleExternal(Boolean titleExternal) {
		this.titleExternal = titleExternal;
	}

	public String getTitleRegex() {
		return titleRegex;
	}

	public void setTitleRegex(String titleRegex) {
		this.titleRegex = titleRegex;
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
		Gather other = (Gather) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
