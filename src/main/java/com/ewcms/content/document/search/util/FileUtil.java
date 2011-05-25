/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.search.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang.ArrayUtils;

/**
 * 文件操作
 * 
 * @author 吴智俊
 */
public class FileUtil {
	public static String normalizePath(String path) {
		path = path.replace('\\', '/');
		path = StringUtil.replaceEx(path, "../", "/");
		path = StringUtil.replaceEx(path, "./", "/");
		if (path.endsWith(".."))
			path = path.substring(0, path.length() - 2);
		path = path.replaceAll("/+", "/");
		return path;
	}

	public static File normalizeFile(File f) {
		String path = f.getAbsolutePath();
		path = normalizePath(path);
		return new File(path);
	}

	public static String readText(File f) {
		f = normalizeFile(f);
		return readText(f, "UTF-8");
	}

	public static String readText(File f, String encoding) {
		try {
			f = normalizeFile(f);
			InputStream is = new FileInputStream(f);
			String str = readText(is, encoding);
			is.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readText(InputStream is, String encoding) {
		try {
			byte bs[] = readByte(is);
			if (encoding.equalsIgnoreCase("UTF-8")
					&& StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3))
							.equals("efbbbf"))
				bs = ArrayUtils.subarray(bs, 3, bs.length);
			return new String(bs, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] readByte(String fileName) {
		try {
			fileName = normalizePath(fileName);
			FileInputStream fis = new FileInputStream(fileName);
			byte r[] = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] readByte(File f) {
		try {
			f = normalizeFile(f);
			FileInputStream fis = new FileInputStream(f);
			byte r[] = readByte(((InputStream) (fis)));
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] readByte(InputStream is) {
		try {
			byte r[] = new byte[is.available()];
			is.read(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
