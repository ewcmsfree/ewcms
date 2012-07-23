/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.web.context.EwcmsContextHolder;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.util.XMLUtil;

/**
 * 
 * @author wuzhijun
 * 
 */
@Controller
public class FcfAction extends EwcmsBaseAction {

	private static final long serialVersionUID = -892021953564132878L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer yearCreate;
	
	public Integer getYearCreate() {
		return yearCreate;
	}

	public void setYearCreate(Integer yearCreate) {
		this.yearCreate = yearCreate;
	}

	public void createArticle() throws Exception {
		if (EwcmsContextHolder.getContext().getSite() == null) return;
		Integer siteId = EwcmsContextHolder.getContext().getSite().getId();
		
		XMLUtil xml = new XMLUtil();
		Element graph = xml.addRoot("graph");
		// xml.addAttribute(graph, "caption", "文章编辑数");
		//xml.addAttribute(graph, "subCaption", getYear().toString());
		xml.addAttribute(graph, "basefontsize", "12");
		// xml.addAttribute(graph, "xAxisName", "月份");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");// 小数精确度，0为精确到个位
		// xml.addAttribute(graph, "showValues", "0");// 在报表上不显示数值
		xml.addAttribute(graph, "formatNumberScale", "0");
		Map<Integer, Long> map = documentFac.findCreateArticleFcfChart(getYearCreate(), siteId);
		Iterator<Entry<Integer, Long>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Long> m = (Map.Entry<Integer, Long>)it.next();
			Integer key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", String.format("%02d",key) + "月");
			set.addAttribute("value", total.toString());
			//set.addAttribute("color", Integer.toHexString((int) (Math.random() * 255 * 255 * 255)).toUpperCase());
			set.addAttribute("color", "FF0000");
		}
		
		Struts2Util.renderHtml(xml.getXML(), "encoding:UTF-8","no-cache:false");	
	}
	
	private Integer yearRelease;
	
	public Integer getYearRelease() {
		return yearRelease;
	}

	public void setYearRelease(Integer yearRelease) {
		this.yearRelease = yearRelease;
	}
	
	public void releaseArticle() throws Exception{
		if (EwcmsContextHolder.getContext().getSite() == null) return;
		Integer siteId = EwcmsContextHolder.getContext().getSite().getId();
		
		XMLUtil xml = new XMLUtil();
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		Map<Integer, Long> map = documentFac.findReleaseArticleFcfChart(getYearRelease(), siteId);
		Iterator<Entry<Integer, Long>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Long> m = (Map.Entry<Integer, Long>)it.next();
			Integer key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", String.format("%02d",key) + "月");
			set.addAttribute("value", total.toString());
			//set.addAttribute("color", Integer.toHexString((int) (Math.random() * 255 * 255 * 255)).toUpperCase());
			set.addAttribute("color", "FF0000");
		}
		
		Struts2Util.renderHtml(xml.getXML(), "encoding:UTF-8","no-cache:false");	
	}
	
	private Integer yearPerson;
	
	public Integer getYearPerson() {
		return yearPerson;
	}

	public void setYearPerson(Integer yearPerson) {
		this.yearPerson = yearPerson;
	}

	public void releaseArticlePerson() throws Exception{
		if (EwcmsContextHolder.getContext().getSite() == null) return;
		Integer siteId = EwcmsContextHolder.getContext().getSite().getId();

		XMLUtil xml = new XMLUtil();
		Element graph = xml.addRoot("graph");
		xml.addAttribute(graph, "basefontsize", "12");
		xml.addAttribute(graph, "showNames", "1");
		xml.addAttribute(graph, "decimalPrecision", "0");
		xml.addAttribute(graph, "formatNumberScale", "0");
		Map<String, Long> map = documentFac.findReleaseArticlePersonFcfChart(getYearPerson(), siteId);
		Iterator<Entry<String, Long>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Long> m = (Map.Entry<String, Long>)it.next();
			String key = m.getKey();
			Long total = (Long)map.get(key);
			Element set = xml.addNode(graph, "set");
			set.addAttribute("name", key);
			set.addAttribute("value", total.toString());
			set.addAttribute("color", Integer.toHexString((int) (Math.random() * 255 * 255 * 255)).toUpperCase());
		}
		
		Struts2Util.renderHtml(xml.getXML(), "encoding:UTF-8","no-cache:false");	
	}
}
