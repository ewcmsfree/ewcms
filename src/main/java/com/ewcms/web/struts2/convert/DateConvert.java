/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.struts2.convert;

import ognl.DefaultTypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 周冬初
 * 
 */
public class DateConvert extends DefaultTypeConverter {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat(DEFAULT_DATE_FORMAT),
			new SimpleDateFormat("yyyy年MM月dd日"),
			new SimpleDateFormat("yyyy/MM/dd") };

	public DateConvert() {

	}

	@SuppressWarnings("rawtypes")
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if (toType == Date.class) {
			try {
				result = convertFromString(((String[]) value)[0]);
			} catch (Exception e) {

			}
		} else if (toType == String.class) {
			result = convertToString(value);
		}
		else if(toType == java.sql.Date.class){
			return new java.sql.Date(convertFromString(((String[]) value)[0]).getTime());
		}
			
		return result;
	}

	private Date convertFromString(String value) {
		if (value == null || value.trim().equals(""))
			return null;
		for (DateFormat format : ACCEPT_DATE_FORMATS) {
			try {
				format.setLenient(false);
				return format.parse(value);
			} catch (ParseException e) {
				continue;
			} catch (RuntimeException e) {
				continue;
			}
		}
		return null;
	}

	public String convertToString(Object o) {
		if (o instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			try {
				return format.format((Date) o);
			} catch (RuntimeException e) {
				return "";
			}
		}
		return "";
	}
}
