/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 校验错误信息集合
 *
 * @author 吴智俊
 */
public class ValidationErrors implements ValidationErrorsable, Serializable {

    private static final long serialVersionUID = 1L;
    private final List<ValidationErrorable> errors;

    public ValidationErrors() {
        errors = new ArrayList<ValidationErrorable>();
    }

    public Boolean isError() {
        return !errors.isEmpty();
    }

    public List<ValidationErrorable> getErrors() {
        return errors;
    }

    public void add(ValidationErrorable error) {
        errors.add(error);
    }

    @Override
    public String toString() {
        if (!isError()) {
            return "没有错误";
        }

        StringBuffer sb = new StringBuffer();
//	sb.append(errors.size());
//	sb.append(" error(s)\n");
        for (Iterator<ValidationErrorable> it = errors.iterator(); it.hasNext();) {
            ValidationErrorable error = (ValidationErrorable) it.next();
            sb.append(error.toString());
            sb.append("<br>");
        }
        return sb.toString();
    }

    public void removeError(String code, String field) {
        for (Iterator<ValidationErrorable> it = errors.iterator(); it.hasNext();) {
            ValidationErrorable error = (ValidationErrorable) it.next();
            if (matches(error, code, field)) {
                it.remove();
            }
        }
    }

    protected boolean matches(ValidationErrorable error, String code, String field) {
        return code.equals(error.getErrorCode()) && field.equals(error.getField());
    }
}
