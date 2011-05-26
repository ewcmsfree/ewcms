/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 转换成Date(java.sql.Date)数据类型
 * <p>缺省日期格式<code>yyyy-MM-dd</code></p>
 * 
 * @author WangWei
 */
class SqlDateConvert implements ConvertDateable<Date> {

    private final static DateFormat DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static DateFormat SHORT_DEFAULT = new SimpleDateFormat("yyyy-MM-dd");
    private final static int SHORT_LENGTH = 10;
    private SimpleDateFormat format;

    @Override
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    private boolean isShortFormat(String value) {
        return value.trim().length() <= SHORT_LENGTH ? true : false;
    }

    @Override
    public Date parse(String value) throws ConvertException {

        try {
            if (format != null) {
                return new Date(format.parse(value).getTime());
            }
            if (isShortFormat(value)) {
                return new Date(SHORT_DEFAULT.parse(value).getTime());
            } else {
                return new Date(DEFAULT.parse(value).getTime());
            }
        } catch (ParseException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Date value) {
        if (format != null) {
            return format.format(value);
        } else {
            return DEFAULT.format(value);
        }
    }
}
