/**
 * 创建日期 2009-4-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.validator;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.model.AlqcJob;

/**
 * 调度任务时间表达式效验接口
 *
 * @author 吴智俊
 */
public interface AlqcJobValidatorable {

    public ValidationErrorsable validateJob(AlqcJob job) throws BaseException;
}
