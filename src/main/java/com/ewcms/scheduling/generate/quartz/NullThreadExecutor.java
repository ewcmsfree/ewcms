
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
