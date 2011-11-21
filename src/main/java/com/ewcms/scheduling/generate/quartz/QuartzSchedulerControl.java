/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.scheduling.BaseRuntimeExceptionWrapper;

/**
 * 调度器控制台
 *
 * @author 吴智俊
 */
public class QuartzSchedulerControl {

    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerControl.class);
    
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start() {
        try {
            if (getScheduler().isInStandbyMode()) {
                getScheduler().start();
            } else {
                logger.info("调度器已在运行中...");
            }
        } catch (SchedulerException e) {
            logger.error("调度器运行错误", e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }
}
