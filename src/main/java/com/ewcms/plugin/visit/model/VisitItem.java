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

/**
 * 
 * @author wu_zhijun
 *
 */
@Entity
@Table(name = "plugin_visit_item")
@SequenceGenerator(name = "seq_plugin_visit_item", sequenceName = "seq_plugin_visit_item_id", allocationSize = 1)
public class VisitItem implements Serializable {

	private static final long serialVersionUID = 4623306116272124214L;

	@Id
	@GeneratedValue(generator = "seq_plugin_visit_item", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "unique_id")
	private String uniqueId;
	@Temporal(TemporalType.DATE)
	@Column(name = "visit_date", nullable = false)
	private Date visitDate;
	@Temporal(TemporalType.TIME)
	@Column(name = "visit_time", nullable = false)
	private Date visitTime;
	@Column(name = "stick_time")
	private Long stickTime;
	@Column(name = "page_view")
	private Long pageView;
	@Column(name = "channel_id")
	private Integer channelId;
	@Column(name = "article_id")
	private Long articleId;
	@Column(name = "event")
	private String event;
	@Column(name = "host")
	private String host;
	@Column(name = "frequency")
	private Long frequency;
	@Column(name = "site_id")
	private Integer siteId;
	@Column(name = "url")
	private String url;
	@Column(name = "referer")
	private String referer;
	@Column(name = "depth")
	private Long depth;
	
	public VisitItem(){
		visitDate = new Date(Calendar.getInstance().getTime().getTime());
		visitTime = new Date(Calendar.getInstance().getTime().getTime());
		host = "无";
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

	public Long getStickTime() {
		return stickTime;
	}

	public void setStickTime(Long stickTime) {
		this.stickTime = stickTime;
	}

	public Long getPageView() {
		return pageView;
	}

	public void setPageView(Long pageView) {
		this.pageView = pageView;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
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

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
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
		VisitItem other = (VisitItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
