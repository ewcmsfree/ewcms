/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.jpa.query;

/**
 *
 * @author wangwei
 */
class PageUtil {

    public static int pageCount(final int count, final int rows) {
        return (count + rows - 1) / rows;
    }

    public static Object defaultZero(final Object o) {
        if (o == null) {
            return 0;
        }
        if (o.getClass().isArray()) {
            Object[] values = (Object[]) o;
            for (int i = 0; i < values.length; ++i) {
                values[i] = (values[i] == null ? 0 : values[i]);
            }
            return values;
        }
        return o;
    }
}
