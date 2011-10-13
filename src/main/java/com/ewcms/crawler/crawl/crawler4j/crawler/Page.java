/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.crawler.crawl.crawler4j.crawler;

import java.io.InputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

import com.ewcms.crawler.crawl.crawler4j.url.WebURL;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class Page {

	private WebURL url;

	private String html;

	// Data for textual content
	private String text;
	private String title;

	// binary data (e.g, image content)
	// It's null for html pages
	private byte[] binaryData;

	private List<WebURL> urls;

	private ByteBuffer bBuf;

	private String defaultEncoding = Configurations.getStringProperty("crawler.default_encoding", "UTF-8");

	public boolean load(final InputStream in, final int totalsize,
			final boolean isBinary) {
		if (totalsize > 0) {
			this.bBuf = ByteBuffer.allocate(totalsize + 1024);
		} else {
			this.bBuf = ByteBuffer.allocate(PageFetcher.MAX_DOWNLOAD_SIZE);
		}
		final byte[] b = new byte[1024];
		int len;
		double finished = 0;
		try {
			while ((len = in.read(b)) != -1) {
				if (finished + b.length > this.bBuf.capacity()) {
					break;
				}
				this.bBuf.put(b, 0, len);
				finished += len;
			}
		} catch (final BufferOverflowException boe) {
			System.out.println("Page size exceeds maximum allowed.");
			return false;
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return false;
		}

		this.bBuf.flip();
		if (isBinary) {
			binaryData = new byte[bBuf.limit()];
			bBuf.get(binaryData);
		} else {
			this.html = "";
			this.html += Charset.forName(defaultEncoding).decode(this.bBuf);
			this.bBuf.clear();
			if (this.html.length() == 0) {
				return false;
			}
		}
		return true;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public Page(WebURL url) {
		this.url = url;
	}

	public String getHTML() {
		return this.html;
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

	public List<WebURL> getURLs() {
		return urls;
	}

	public void setURLs(List<WebURL> urls) {
		this.urls = urls;
	}

	public WebURL getWebURL() {
		return url;
	}

	public void setWebURL(WebURL url) {
		this.url = url;
	}

	// Image or other non-textual pages
	public boolean isBinary() {
		return binaryData != null;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

}
