/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular;

import org.junit.Test;

import com.ewcms.content.particular.model.ProjectBasic.Nature;

public class EnumTest {
	public enum NatureTest {
		NEW("新建"),EXPANSION("扩建"),TRANSFORM("改建和技术改造"),FACILITY("单纯建造生活设施"),RESTORATION("迁建恢复"),PURCHASE("单纯购置");
		
		private String description;
		
		private NatureTest(String description){
			this.description = description;
		}
		
		public String getDescription(){
			return description;
		}
	}
	
	@Test
	public void testValueOf(){
		String value = "新建";
		for (Nature nature : Nature.values()){
			if (nature.getDescription().trim().equals(value.trim())){
				System.out.println(nature.name());
			}
		}
	}
}
