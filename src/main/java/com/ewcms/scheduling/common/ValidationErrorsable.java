/**
 * 创建日期 2009-4-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.common;

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
