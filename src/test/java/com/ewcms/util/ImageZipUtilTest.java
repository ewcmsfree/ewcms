/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.util;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.common.io.ImageZipUtil;

/**
 * 
 * @author 吴智俊
 */
public class ImageZipUtilTest {

	private final static String PATH = "../ewcms/src/test/resources/com/ewcms/util/";

	/**
	 * @param args
	 */
	@Test
	public void testImageZip() {
		try {
			Boolean isZip = ImageZipUtil.compression(PATH + "1.bmp", PATH + "2.bmp", 64, 64);
			Assert.assertEquals(Boolean.TRUE,isZip);
			
			isZip = ImageZipUtil.compression(PATH + "1.png", PATH + "2.png", 64, 64);
			Assert.assertEquals(Boolean.TRUE,isZip);
			
			isZip = ImageZipUtil.compression(PATH + "1_0.jpg", PATH + "2_0.jpg", 64, 64);
			Assert.assertEquals(Boolean.TRUE,isZip);
			
			isZip = ImageZipUtil.compression(PATH + "1_1.jpg", PATH + "2_1.jpg", 64, 64);
			Assert.assertEquals(Boolean.FALSE, isZip);
			
			isZip = ImageZipUtil.compression(PATH + "1.gif", PATH + "2.gif", 64, 64);
			Assert.assertEquals(Boolean.TRUE, isZip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
