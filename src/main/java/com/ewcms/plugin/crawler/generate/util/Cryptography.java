/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.util;

import java.security.MessageDigest;

/**
 * 
 * @author wu_zhijun
 *
 */
public class Cryptography {

	private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return hexStringFromBytes(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String hexStringFromBytes(byte[] b) {
		String hex = "";
		int msb;
		int lsb;
		int i;

		// MSB maps to idx 0
		for (i = 0; i < b.length; i++) {
			msb = ((int) b[i] & 0x000000FF) / 16;
			lsb = ((int) b[i] & 0x000000FF) % 16;
			hex = hex + hexChars[msb] + hexChars[lsb];
		}
		return (hex);
	}
}
