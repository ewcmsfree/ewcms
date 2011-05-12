/**
 * 创建日期 2009-4-2
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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
