/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.url;

import java.io.*;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */
@Entity
public class WebURL implements Serializable {
	
	private static final long serialVersionUID = -9219968169849360566L;
	
	@PrimaryKey
	private String url;

	private int docid;
	private int parentDocid;
	private short depth;
	private String domain;
	private String subDomain;

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

		WebURL otherUrl = (WebURL) o;
		return url != null && url.equals(otherUrl.getURL());

	}

	public String toString() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;

		int domainStartIdx = url.indexOf("//") + 2;
		int domainEndIdx = url.indexOf('/', domainStartIdx);
		domain = url.substring(domainStartIdx, domainEndIdx);
		subDomain = "";
		String[] parts = domain.split("\\.");
		if (parts.length > 2) {
			domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
			int limit = 2;
			if (TLDList.contains(domain)) {
				domain = parts[parts.length - 3] + "." + domain;
				limit = 3;
			}
			for (int i = 0; i < parts.length - limit; i++) {
				if (subDomain.length() > 0) {
					subDomain += ".";
				}
				subDomain += parts[i];
			}
		}
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

	public String getDomain() {
		return domain;
	}

	public String getSubDomain() {
		return subDomain;
	}
}
