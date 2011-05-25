/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  转换成日期(java.util.Date)
 * 日期格式为<code>yyyy-MM-dd OR yyyy-MM-dd HH:mm:ss</code>
 *
 * @author 王伟
 */
class DateConvert implements ConvertDate<Date> {

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
                return format.parse(value);
            }
            if (isShortFormat(value)) {
                return SHORT_DEFAULT.parse(value);
            } else {
                return DEFAULT.parse(value);
            }
        } catch (ParseException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Date value) {
        if(format != null){
            return format.format(value);
        }else{
            return DEFAULT.format(value);
        }
    }
}
