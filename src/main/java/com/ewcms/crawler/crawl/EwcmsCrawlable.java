/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl;

import com.ewcms.crawler.BaseException;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface EwcmsCrawlable {
	public void crawl(Long gatherId) throws BaseException;
}
