/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.parser;

import java.util.List;

import com.ewcms.plugin.crawler.generate.url.WebURL;

public class HtmlParseData implements ParseData {

	private String html;
	private String text;
	private String title;

	private List<WebURL> outgoingUrls;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<WebURL> getOutgoingUrls() {
		return outgoingUrls;
	}

	public void setOutgoingUrls(List<WebURL> outgoingUrls) {
		this.outgoingUrls = outgoingUrls;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
