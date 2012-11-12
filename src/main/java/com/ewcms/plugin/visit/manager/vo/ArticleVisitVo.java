package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

public class ArticleVisitVo implements Serializable {

	private static final long serialVersionUID = 3387503941545782436L;

	private String channelName;
	private String title;
	private String url;
	private String owner;
	private Long pageView;
	private Long stickTime;

	public ArticleVisitVo(String channelName, String title, String url, String owner, Long pageView, Long stickTime) {
		this.channelName = channelName;
		this.title = title;
		this.url = url;
		this.owner = owner;
		this.pageView = pageView;
		this.stickTime = stickTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getPageView() {
		return pageView;
	}

	public void setPageView(Long pageView) {
		this.pageView = pageView;
	}

	public Long getStickTime() {
		return stickTime;
	}

	public void setStickTime(Long stickTime) {
		this.stickTime = stickTime;
	}
}
