/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.url;

import org.junit.Test;

import junit.framework.TestCase;

public class TLDListTest extends TestCase {
	
	private WebURL webUrl = new WebURL();
	
	private void setUrl(String url) {
		webUrl.setURL(URLCanonicalizer.getCanonicalURL(url));
	}

	@Test
	public void testTLD() {
		setUrl("http://example.com");
		assertEquals("example.com", webUrl.getDomain());
		assertEquals("", webUrl.getSubDomain());
		
		setUrl("http://test.example.com");
		assertEquals("example.com", webUrl.getDomain());
		assertEquals("test", webUrl.getSubDomain());
		
		setUrl("http://test2.test.example.com");
		assertEquals("example.com", webUrl.getDomain());
		assertEquals("test2.test", webUrl.getSubDomain());
		
		setUrl("http://test3.test2.test.example.com");
		assertEquals("example.com", webUrl.getDomain());
		assertEquals("test3.test2.test", webUrl.getSubDomain());
		
		setUrl("http://www.example.ac.jp");
		assertEquals("example.ac.jp", webUrl.getDomain());
		assertEquals("www", webUrl.getSubDomain());
		
		setUrl("http://example.ac.jp");
		assertEquals("example.ac.jp", webUrl.getDomain());
		assertEquals("", webUrl.getSubDomain());
		
	}
	
}
