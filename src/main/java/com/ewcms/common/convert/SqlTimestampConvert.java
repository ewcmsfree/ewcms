/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 转换成Timestamp数据类型的值
 * <p>
 * 日期格式<code>yyyy-MM-dd HH:mm:ss</code>
 * 
 * @author 王伟
 */
class SqlTimestampConvert implements ConvertDate<Timestamp> {

    private final static DateFormat DEFAULT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat format;

    @Override
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    @Override
    public Timestamp parse(String value) throws ConvertException {

        try {
           if(format != null){
                return new Timestamp(format.parse(value).getTime());
            }
            return new Timestamp(DEFAULT.parse(value).getTime());
        } catch (ParseException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Timestamp value) {
       if (format != null) {
            return format.format(value);
        } else {
            return DEFAULT.format(value);
        }
    }
}
