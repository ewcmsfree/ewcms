/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.history.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 由二进制转换成实体对象
 * 
 * @author 吴智俊
 */
public class ByteToObject {
	protected static final Log log = LogFactory.getLog(ByteToObject.class);

	/**
	 * 转换操作
	 * 
	 * @param bytes 二进制对象
	 * @return 实体对象
	 */
	public static Object conversion(byte[] bytes) {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (IOException e) {
			log.error(e.toString());
		} catch (ClassNotFoundException e) {
			log.error(e.toString());
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
			bis = null;
			ois = null;
		}
		return obj;
	}
}
