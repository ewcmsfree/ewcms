
package com.ewcms.scheduling.generate.quartz;

import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.SchedulingAwareRunnable;

/**
 * 
 * @author wuzhijun
 *
 */
public class TaskExecutorThreadExecutor implements ThreadExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorThreadExecutor.class);
	
	private TaskExecutor taskExecutor;

	@Override
	public void initialize() {
		// NOP
	}
	
	@Override
	public void execute(final Thread thread) {
		taskExecutor.execute(new SchedulingAwareRunnable() {
			public boolean isLongLived() {
				return true;
			}

			public void run() {
				thread.run();
			}
		});
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}
