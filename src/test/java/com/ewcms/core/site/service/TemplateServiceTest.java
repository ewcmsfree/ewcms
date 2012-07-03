/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceTest.class);
	
	@Test
	public void testNameRegex(){
		String name = "test_templ.ate_2html";
		String channelId = "1";
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1){
			logger.info(name + "_app_" + channelId);
		}else{
			String newName = name.substring(0, lastIndexOf) + "_app_" + channelId + name.substring(lastIndexOf);
			logger.info(newName);
		}
	}
}
