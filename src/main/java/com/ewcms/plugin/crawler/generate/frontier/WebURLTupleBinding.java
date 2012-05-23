/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.frontier;

import com.ewcms.plugin.crawler.generate.url.WebURL;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class WebURLTupleBinding extends TupleBinding<WebURL> {

	@Override
	public WebURL entryToObject(TupleInput input) {
		WebURL webURL = new WebURL();
		webURL.setURL(input.readString());
		webURL.setDocid(input.readInt());
		webURL.setParentDocid(input.readInt());
		webURL.setParentUrl(input.readString());
		webURL.setDepth(input.readShort());
		webURL.setPriority(input.readByte());
		return webURL;
	}

	@Override
	public void objectToEntry(WebURL url, TupleOutput output) {		
		output.writeString(url.getURL());
		output.writeInt(url.getDocid());
		output.writeInt(url.getParentDocid());
		output.writeString(url.getParentUrl());
		output.writeShort(url.getDepth());
		output.writeByte(url.getPriority());
	}
}
