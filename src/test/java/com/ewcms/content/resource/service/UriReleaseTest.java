/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.resource.service;

import org.junit.Test;

/**
 * @author wuzhijun
 */
public class UriReleaseTest {
	 @Test
	 public void releaseAll(){
		 String uri = "\\pub_res\\2012-07-12\\80812492521377832906438523070312_thumb.jpg";
		 System.out.println(uri);
		 uri = uri.replace("\\", "/");
		 System.out.println(uri);
		 
		 String path = "/e:/www/\\pub_res\\2012-07-12\\80812492521377832906438523070312.jpg";
		 System.out.println(path);
		 path = path.replace("\\", "/").replace("//", "/");
		 System.out.println(path);
	 }
}
