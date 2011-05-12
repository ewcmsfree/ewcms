/**
 * 创建日期 2009-4-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.common;

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
