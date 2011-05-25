/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.common;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 校验错误信息
 *
 * @author 吴智俊
 */
public class ValidationError implements ValidationErrorable, Serializable {

    private static final long serialVersionUID = 4792857608533411343L;
    private final String errorCode;
    private final Object[] arguments;
    private final String defaultMessage;
    private final String field;

    public ValidationError(String errorCode, Object[] arguments, String defaultMessage, String field) {
        this.errorCode = errorCode;
        this.arguments = arguments;
        this.defaultMessage = defaultMessage;
        this.field = field;
    }

    public ValidationError(String errorCode, Object[] arguments, String defaultMessage) {
        this(errorCode, arguments, defaultMessage, null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getErrorArguments() {
        return arguments;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        if (getDefaultMessage() != null) {
            return MessageFormat.format(getDefaultMessage(), getErrorArguments());
        }

        if (getField() == null) {
            return getErrorCode();
        }

        return getErrorCode() + "." + getField();
    }
}
