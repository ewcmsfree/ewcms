/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 搜索引擎工具
 * 
 * @author wu_zhijun
 * 
 */
public class SourceUtil {

	private static Pattern domainPattern = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
	
	public static Map<String,String> searchEngineMap = null;
	
	static{
		searchEngineMap = new LinkedHashMap<String, String>();
		searchEngineMap.put("baidu.com", "百度");
		searchEngineMap.put("google.com", "谷歌");
		searchEngineMap.put("yahoo.com","雅虎");
		searchEngineMap.put("bing.com","必应");
		searchEngineMap.put("sogou.com","搜狗");
		searchEngineMap.put("soso.com","搜搜");
		searchEngineMap.put("youdao.com", "有道");
	}
	
	public static String getDomain(String referer){
		Matcher matcher = domainPattern.matcher(referer);
		matcher.find();
		String domain = null;
		try{
			domain = matcher.group();
		}catch(Exception e){
		}
		return domain;
	}
	
	public static String getDomainName(String referer){
		String domain = getDomain(referer);
		if (domain == null) return null;
		return searchEngineMap.get(domain);
	}
	
	public static String getWebSiteUrl(String referer){
		if (referer == null || referer.length() < 8) return null;
		referer = referer.substring(7);
		int firstSlash = referer.indexOf("/");
		if (firstSlash > 0){
			referer = referer.substring(0, firstSlash);
		}
		return referer;
	}
	
	public static void main(String[] args){
		String referer = "http://www.wzj.com";
		System.out.println(getDomain(referer));
		System.out.println(getWebSiteUrl(referer));
		referer = "http://127.0.0.1";
		System.out.println(getDomainName(referer));
	}
}
