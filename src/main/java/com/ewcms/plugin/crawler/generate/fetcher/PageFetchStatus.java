/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.fetcher;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class PageFetchStatus {

	public static final int OK = 1000;

	public static final int PageTooBig = 1001;

    public static final int FatalTransportError = 1005;

	public static final int UnknownError = 1006;

	public static final int RedirectedPageIsSeen = 1010;

	public static final int NotInTextFormat = 1011;

	public static final int PageIsBinary = 1012;

	public static final int Moved = 1013;

	public static final int MovedToUnknownLocation = 1014;

}
