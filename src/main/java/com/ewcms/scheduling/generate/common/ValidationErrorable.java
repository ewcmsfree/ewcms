/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.common;

/**
 * 校验错误信息接口
 *
 * @author 吴智俊
 */
public interface ValidationErrorable {

    /**
     * 错误代码
     *
     * @return String
     */
    public String getErrorCode();

    /**
     * 错误理由
     *
     * @return Object[]对象
     */
    public Object[] getErrorArguments();

    /**
     * 默认信息
     *
     * @return String
     */
    public String getDefaultMessage();

    /**
     * 字段
     *
     * @return String
     */
    public String getField();
}
