/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.document;

import java.util.List;

import org.junit.Test;

import com.ewcms.core.site.util.TemplateUtil;

public class PatternTest {
	
	@Test
	public void testRegex(){
		String content = "<div id=\"tnoticMenu2\" class=\"notice_list\" style=\"display:none;\">"
				+ "<ul class=\"box\">"
				+ "<@article_list channel=\"/zhengfugongzuo/xinwen/bianmintishi\" row=5>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel=100 row=5>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel=[100,\"/news/test/\"] row=5>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel=[1,\"/news1/test1/\"] row=5 child=true>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel=100 row=5 child=true>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel=/abc/def row=5 child=true>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>"
				+ "<@article_list channel='/hig/ddd' row=5 child=true>"
				+ "<li><a href='<@article name=\"url\"/>' title=\"<@article name=\"title\"/>\" target='_blank'>"
				+  "<@article name=\"title\" length='15'/>";

		List<String> results = TemplateUtil.associate(content);
		for (String result : results){
			System.out.println(result);
		}
	}
	
	@Test
	public void testSpearate(){
		String content = "channel=120:";
		String[] spearates = content.split(":");
		for (String spearate : spearates){
			System.out.println(spearate);
		}
	}
}
