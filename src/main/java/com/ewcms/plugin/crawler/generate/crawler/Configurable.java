/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.crawler;

/**
 * Several core components of crawler4j extend this class
 * to make them configurable.
 *
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public abstract class Configurable {

	protected CrawlConfig config;
	
	protected Configurable(CrawlConfig config) {
		this.config = config;
	}
	
	public CrawlConfig getConfig() {
		return config;
	}
}
