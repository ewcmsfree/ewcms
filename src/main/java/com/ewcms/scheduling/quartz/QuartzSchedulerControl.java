/**
 * 创建日期 2009-4-2
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
