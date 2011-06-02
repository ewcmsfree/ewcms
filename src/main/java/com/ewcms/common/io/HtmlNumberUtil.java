/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.io;

import java.util.regex.Pattern;

/**
 * 数字操作
 * 
 * @author 吴智俊
 */
public class HtmlNumberUtil {
	private static Pattern numberPatter = Pattern.compile("^[\\d\\.E\\,]*$");

	public static boolean isNumber(String str) {
		return numberPatter.matcher(str).find();
	}

}
