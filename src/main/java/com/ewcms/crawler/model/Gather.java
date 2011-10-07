/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * 采集器基本信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:名称</li>
 * <li>description:描述</li>
 * <li>status:状态(true:启用,false:停用)</li>
 * <li>maxContent:内容页最大采集数</li>
 * <li>depth:采集深度</li>
 * <li>threadCount:采集线程数</li>
 * <li>timeOutWait:超时等待时间</li>
 * <li>errorCount:发生错误时重试次数</li>
 * <li>dateFormat:发布日期格式</li>
 * <li>option:采集选项</li>
 * <li>channelId:采集到此频道</li>
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
	@Column(name = "maxcontent")
	private Long maxContent;
	@Column(name = "depth")
	private Long depth;
	@Column(name = "threadcount")
	private Long threadCount;
	@Column(name = "timeoutwait")
	private Long timeOutWait;
	@Column(name = "errorcount")
	private Long errorCount;
	@Column(name = "dateformat")
	private String dateFormat;
	@Column(name = "option")
	@Enumerated(EnumType.STRING)
	private CaptureOptions option;
	@Column(name = "channel_id")
	private Integer channelId;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = UrlLevel.class)
	@JoinColumn(name = "gather_id")
	@OrderBy(value = "level")
	private List<UrlLevel> urlLevels = new ArrayList<UrlLevel>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = MatchBlock.class)
	@JoinColumn(name = "gather_id")
	private List<MatchBlock> matchBlocks = new ArrayList<MatchBlock>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = FilterBlock.class)
	@JoinColumn(name = "gather_id")
	private List<FilterBlock> filterBlocks = new ArrayList<FilterBlock>();

	public Gather(){
		maxContent = -1L;
		depth = -1L;
		threadCount = 1L;
		timeOutWait = 30L;
		errorCount = 2L;
		dateFormat = "yyyy-MM-dd";
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

	public Long getMaxContent() {
		return maxContent;
	}

	public void setMaxContent(Long maxContent) {
		this.maxContent = maxContent;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	public Long getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(Long threadCount) {
		this.threadCount = threadCount;
	}

	public Long getTimeOutWait() {
		return timeOutWait;
	}

	public void setTimeOutWait(Long timeOutWait) {
		this.timeOutWait = timeOutWait;
	}

	public Long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public CaptureOptions getOption() {
		return option;
	}

	public void setOption(CaptureOptions option) {
		this.option = option;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@JsonIgnore
	public List<UrlLevel> getUrlLevels() {
		return urlLevels;
	}

	public void setUrlLevels(List<UrlLevel> urlLevels) {
		this.urlLevels = urlLevels;
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
