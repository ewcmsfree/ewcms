package com.ewcms.scheduling.generate.quartz;

import org.quartz.spi.ThreadExecutor;

/**
 * 
 * @author wuzhijun
 *
 */
public class LocalThreadExecutor implements ThreadExecutor {

	private static final ThreadLocal<ThreadExecutor> localThreadExecutor = new ThreadLocal<ThreadExecutor>();
	
	public static void setLocalThreadExecutor(ThreadExecutor ThreadExecutor) {
		localThreadExecutor.set(ThreadExecutor);
	}
	
	private final ThreadExecutor ThreadExecutor;
	
	public LocalThreadExecutor() {
		this.ThreadExecutor = (ThreadExecutor) localThreadExecutor.get();
		if (this.ThreadExecutor == null) {
			throw new RuntimeException("Internal error: No local thread executor set");
		}
	}

	public void initialize() {
		ThreadExecutor.initialize();
	}
	
	public void execute(Thread thread) {
		ThreadExecutor.execute(thread);
	}

}
