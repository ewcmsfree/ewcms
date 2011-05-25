/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.ewcms.scheduling.BaseRuntimeExceptionWrapper;

/**
 * 调度器控制台
 *
 * @author 吴智俊
 */
public class QuartzSchedulerControl {

    private static final Log log = LogFactory.getLog(QuartzSchedulerControl.class);
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
                log.info("调度器已在运行.");
            }
        } catch (SchedulerException e) {
            log.error("错误开始调度", e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }
}
