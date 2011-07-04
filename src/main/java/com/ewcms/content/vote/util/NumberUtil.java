/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.util;

import java.util.regex.Pattern;

/**
 * 
 * @author wu_zhijun
 *
 */
public class NumberUtil {
	public static Boolean isNumber(String value){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(value).matches();   
	}
}
