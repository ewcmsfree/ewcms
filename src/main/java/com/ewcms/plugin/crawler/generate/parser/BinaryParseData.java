/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.parser;

public class BinaryParseData implements ParseData {

	private static BinaryParseData instance = new BinaryParseData();
	
	public static BinaryParseData getInstance() {
		return instance;
	}
	
	@Override
	public String toString() {
		return "[Binary parse data can not be dumped as string]";
	}
}
