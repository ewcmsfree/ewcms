/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler;

import static org.junit.Assert.*;
import java.util.regex.Pattern;

import org.junit.Test;

public class PatternTest {
	
	@Test
	public void testRegex(){
		String[] keys = new String[]{"浔阳区","吴智俊|王伟"};
		String content = "我是在测试浔阳区的正测表达式，测试用例是吴智俊写的,也由王伟的加入";
		Boolean isKeyEntity = true;
		for (int i = 0 ; i < keys.length ; i++){
			Boolean result = Pattern.compile(keys[i]).matcher(content).find();
			if (!result){
				isKeyEntity = false;
				break;
			}
		}
		assertTrue(isKeyEntity);
	}
}
