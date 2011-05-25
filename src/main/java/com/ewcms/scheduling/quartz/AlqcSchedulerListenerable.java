/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.quartz;

import com.ewcms.scheduling.BaseException;

/**
 * 调度器监听接口
 * 在管理调度器时实现此接口
 *
 * @author 吴智俊
 */
public interface AlqcSchedulerListenerable {

    public void alqcJobFinalized(Integer jobId) throws BaseException;
}
