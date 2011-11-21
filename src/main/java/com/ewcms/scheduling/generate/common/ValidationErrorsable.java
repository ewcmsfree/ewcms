/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.common;

import java.util.List;

/**
 * 校验错误信息集合接口
 *
 * @author 吴智俊
 */
public interface ValidationErrorsable {

    /**
     * 是否错误
     *
     * @return Boolean对象
     */
    public Boolean isError();

    /**
     * 错误信息对象
     *
     * @return List对象
     */
    public List<ValidationErrorable> getErrors();

    /**
     * 新增错误信息
     *
     * @param error 校验错误信息接口
     */
    public void add(ValidationErrorable error);

    /**
     * 根据代码和字段，删除错误信息
     *
     * @param code 代码
     * @param field 字段
     */
    public void removeError(String code, String field);
}
