/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.robotstxt;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class HostDirectives {

	// If we fetched the directives for this host more than
	// 24 hours, we have to re-fetch it.
	private static final long EXPIRATION_DELAY = 24 * 60 * 1000L;

	private RuleSet disallows = new RuleSet();
	private RuleSet allows = new RuleSet();

	private long timeFetched;
	private long timeLastAccessed;

	public HostDirectives() {
		timeFetched = System.currentTimeMillis();
	}

	public boolean needsRefetch() {
		return (System.currentTimeMillis() - timeFetched > EXPIRATION_DELAY);
	}

	public boolean allows(String path) {
		timeLastAccessed = System.currentTimeMillis();
        return !disallows.containsPrefixOf(path) || allows.containsPrefixOf(path);
    }

	public void addDisallow(String path) {
		disallows.add(path);
	}

	public void addAllow(String path) {
		allows.add(path);
	}
	
	public long getLastAccessTime() {
		return timeLastAccessed;
	}
}