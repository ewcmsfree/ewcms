/* 
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.convert;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * 转换成Time数据类型的值
 * <p>
 *时间格式<code>hh:mm:ss</code>
 * 
 * @author 王伟
 */
class SqlTimeConvert implements ConvertDate<Time> {

    private final static DateFormat DEFAULT = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat format;

    @Override
    public void setFormat(String format) {
        this.format = new SimpleDateFormat(format);
    }

    @Override
    public Time parse(String value)throws ConvertException {

        try {
            if(format != null){
                return new Time(format.parse(value).getTime());
            }
            return new Time(DEFAULT.parse(value).getTime());
        } catch (ParseException e) {
            throw new ConvertException(e);
        }
    }

    @Override
    public String parseString(Time value) {
        if (format != null) {
            return format.format(value);
        } else {
            return DEFAULT.format(value);
        }
    }


}
