/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.crawler.crawl.crawler4j.url;

import java.io.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */


@Entity
public final class WebURL implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
	private String url;
	private int docid;
	private int parentDocid;
	private short depth;
	
	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public String getURL() {
		return url;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		WebURL url2 = (WebURL) o;
		if (url == null) {
			return false;
		}
		return url.equals(url2.getURL());

	}

	public String toString() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}
	
	public int getParentDocid() {
		return parentDocid;
	}

	public void setParentDocid(int parentDocid) {
		this.parentDocid = parentDocid;
	}
	
	public short getDepth() {
		return depth;
	}

	public void setDepth(short depth) {
		this.depth = depth;
	}
}
