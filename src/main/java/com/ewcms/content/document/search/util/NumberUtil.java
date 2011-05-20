/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.content.document.search.util;

import java.util.regex.Pattern;

/**
 * 数字操作
 * 
 * @author 吴智俊
 */
public class NumberUtil {
	private static Pattern numberPatter = Pattern.compile("^[\\d\\.E\\,]*$");

	public static boolean isNumber(String str) {
		return numberPatter.matcher(str).find();
	}

}
