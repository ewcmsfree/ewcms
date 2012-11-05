/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.web.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ewcms.common.lang.EmptyUtil;
import com.ewcms.plugin.visit.manager.VisitFacable;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * @author wuzhijun
 */
public class VisitServlet extends HttpServlet {

	private static final long serialVersionUID = 8986742704742468271L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long current = System.currentTimeMillis();
    	
    	Visit v = new Visit();
    	
    	String IP = VisitUtil.getIP(request);
    	v.setIp(IP);
    	if (EmptyUtil.isStringNotEmpty(IP) && IP.indexOf(",") > 0){
    		String arr[] = IP.split("\\,");
    		for (int i=0; i < arr.length; i++){
    			if (!EmptyUtil.isStringNotEmpty(arr[i]) || arr[i].trim().startsWith("10.") || arr[i].trim().startsWith("192."))
    				continue;
    			v.setIp(arr[i].trim());
    			break;
    		}
    	}
    	
    	String userAgent = request.getHeader("User-Agent");
    	if (EmptyUtil.isStringEmpty(userAgent)){
    		userAgent = "Unknow";
    	}
    	v.setUserAgent(userAgent);

    	try{
	    	Long siteID = Long.parseLong(request.getParameter("siteId"));
	    	v.setSiteID(siteID);
    	}catch(Exception e){
    		
    	}

    	String uniqueID = VisitUtil.getCookieValue(request, "UniqueID");
    	v.setUniqueID(uniqueID);
    	
    	String event = request.getParameter("event");
    	v.setEvent(event);
    	
    	if ("KeepAlive".equalsIgnoreCase(event)){
    		//访问历史记录，并返回
    		return;
    	}
    	
    	Integer channelId = Integer.parseInt(request.getParameter("channelId"));
    	v.setChannelId(channelId);
    	
    	String type = request.getParameter("type");
    	if (EmptyUtil.isStringEmpty(type)){
    		v.setType("Other");
    	}else{
    		v.setType(type);
    	}
    	
    	
    	Long articleId = EmptyUtil.isStringNotEmpty(request.getParameter("articleId")) ? Long.parseLong(request.getParameter("articleId")) : 0L;
    	v.setLeafID(articleId);
    	
    	v.setVisitTime(current);
    	
    	String referer = request.getParameter("Referer");
    	if (EmptyUtil.isStringNotEmpty(referer)){
    		referer = referer.replace('\'', '0').replace('\\', '0');
    	}
    	v.setReferer(referer);
    	
    	String url = request.getParameter("URL");
    	if (EmptyUtil.isStringNotEmpty(url)){
    		url = url.replace('\'', '0').replace('\\', '0');
    		String prefix = url.substring(0 , 8);
    		String tail = url.substring(8);
    		tail = tail.replace("/+", "/");
    		url = prefix + tail;
    	}
    	v.setUrl(url);
    	
    	if (!"Unload".equalsIgnoreCase(request.getParameter("Event"))){
	    	if (EmptyUtil.isStringEmpty(url)) return;
	    	try{
	    		String sites = VisitUtil.getCookieValue(request, "Sites");
	    		if (EmptyUtil.isStringEmpty(v.getUniqueID())){
	    			v.setUniqueID(VisitUtil.getUniqueID());
	    			v.setRvFlag(false);
	    			VisitUtil.setCookieValue(request, response, "UniqueID", -1, v.getUniqueID());
	    			VisitUtil.setCookieValue(request, response, "Sites", -1, "_" + v.getSiteID());
	    		}else if (EmptyUtil.isStringNotEmpty(sites) && sites.indexOf("_" + v.getSiteID()) >=0){
	    			v.setRvFlag(true);
	    		}else{
	    			v.setRvFlag(false);
	    			VisitUtil.setCookieValue(request, response, "Sites", -1, sites + "_" + v.getSiteID());
	    		}
	    		v.setHost(request.getParameter("Host"));
	    		if (EmptyUtil.isStringNotEmpty(v.getHost())){
	    			v.setHost(v.getHost().toLowerCase());
	    		}else{
	    			v.setHost("无");
	    		}
	    		v.setCookieEnabled("1".equals(request.getParameter("ce")));
	    		v.setFlashVersion(request.getParameter("fv"));
	    		v.setFlashEnabled(EmptyUtil.isNotNull(v.getFlashVersion()));
	    		v.setJavaEnabled("1".equals(request.getParameter("je")));
	    		v.setLanguage(VisitUtil.getLanguage(request.getParameter("la")));
	    		if (v.getLanguage().equals("其他")){
	    			v.setLanguage(VisitUtil.getLanguage("; " + request.getHeader("accept-language") + ";"));
	    		}
	    		v.setOs(VisitUtil.getOS(v.getUserAgent()));
	    		v.setBrowser(VisitUtil.getBrowser(v.getUserAgent()));
	    		v.setReferer(request.getParameter("Referer"));
	    		v.setScreen(request.getParameter("sr"));
	    		v.setScreen(VisitUtil.getScreen(v.getScreen()));
	    		v.setColorDepth(request.getParameter("cd"));
	    		v.setDistrict(VisitUtil.getDistrictCode(v.getIp()));
	    		//v.setIpFlag(ipFlag)
	    		v.setFrequency(Long.parseLong(request.getParameter("vq")));
	    	}catch(Exception e){
	    		
	    	}
    	}else{
    		try{
    			v.setStickTime(new Double(Math.ceil(Double.parseDouble(request.getParameter("StickTime").trim()))).longValue());
    		}catch(Exception e){
    			
    		}
    		if (v.getStickTime() <= 0){
    			v.setStickTime(1L);
    		}
    	}
    	
    	ServletContext application = getServletContext(); 
    	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
    	VisitFacable visitFac = (VisitFacable) wac.getBean("visitFac");
    	
    	visitFac.addAndUpdVisit(v);
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
