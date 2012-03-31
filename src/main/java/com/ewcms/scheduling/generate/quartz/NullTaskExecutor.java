/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.quartz;

import org.springframework.core.task.TaskExecutor;

/**
 * @author wuzhijun
 */
public class NullTaskExecutor implements TaskExecutor {

	@Override
	public void execute(Runnable task) {
		throw new UnsupportedOperationException("This class can only be used as a placeholder for null");
	}

}
