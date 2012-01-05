/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.generate;

import com.ewcms.plugin.BaseException;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface EwcmsControllerable {
	
	public void startCrawl(Long gatherId) throws BaseException;
	
	public void interruptCrawl() throws BaseException;
}
