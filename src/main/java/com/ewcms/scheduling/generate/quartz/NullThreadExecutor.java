/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.quartz;

import org.quartz.spi.ThreadExecutor;

/**
 * 
 * @author wuzhijun
 *
 */
public class NullThreadExecutor implements ThreadExecutor {

	@Override
	public void initialize() {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

	@Override
	public void execute(Thread thread) {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

}
