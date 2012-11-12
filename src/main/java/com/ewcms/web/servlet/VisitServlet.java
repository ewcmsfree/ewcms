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
import com.ewcms.plugin.visit.model.VisitItem;
import com.ewcms.plugin.visit.util.VisitUtil;

/**
 * @author wuzhijun
 */
public class VisitServlet extends HttpServlet {

	private static final long serialVersionUID = 8986742704742468271L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Visit v = new Visit();
		VisitItem item = new VisitItem();
		try {
			Integer siteID = Integer.parseInt(request.getParameter("siteId"));
			v.setSiteId(siteID);
			item.setSiteId(siteID);
			Integer channelId = Integer.parseInt(request.getParameter("channelId"));
			item.setChannelId(channelId);
		} catch (Exception e) {
			return;
		}
        Long articleId =(EmptyUtil.isStringNotEmpty(request.getParameter("articleId")) ? Long.parseLong(request.getParameter("articleId")) : 0L);
		item.setArticleId(articleId);
		
		String uniqueID = VisitUtil.getCookieValue(request, "UniqueID");
		v.setUniqueId(uniqueID);
		item.setUniqueId(uniqueID);

		String IP = VisitUtil.getIP(request);
		v.setIp(IP);
		if (EmptyUtil.isStringNotEmpty(IP) && IP.indexOf(",") > 0) {
			String arr[] = IP.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (!EmptyUtil.isStringNotEmpty(arr[i])
						|| arr[i].trim().startsWith("10.")
						|| arr[i].trim().startsWith("192."))
					continue;
				v.setIp(arr[i].trim());
				break;
			}
		}

		String url = request.getParameter("URL");
		if (EmptyUtil.isStringNotEmpty(url)) {
			url = url.replace('\'', '0').replace('\\', '0');
			String prefix = url.substring(0, 8);
			String tail = url.substring(8);
			tail = tail.replace("/+", "/");
			url = prefix + tail;
		}
		item.setUrl(url);
		if (EmptyUtil.isStringEmpty(url)) return;
		
		Long stickTime = 10L;
		try {
			stickTime = new Double(Math.ceil(Double.parseDouble(request.getParameter("stickTime").trim()))).longValue();
		} catch (Exception e) {
		}
		item.setStickTime(Math.abs(stickTime));
		
		if ("KeepAlive".equalsIgnoreCase(request.getParameter("event"))) {
			findVisitFacable().addVisitByKeepAliveEvent(v, item);
			return;
		}
		
		String userAgent = request.getHeader("User-Agent");
		if (EmptyUtil.isStringEmpty(userAgent)) {
			userAgent = "Unknow";
		}
		v.setUserAgent(userAgent);

		String referer = request.getParameter("Referer");
		if (EmptyUtil.isStringNotEmpty(referer)) {
			referer = referer.replace('\'', '0').replace('\\', '0');
		}
		item.setReferer(referer);
		
		if (!"Unload".equalsIgnoreCase(request.getParameter("event"))) {
			try {
				String sites = VisitUtil.getCookieValue(request, "Sites");
				if (EmptyUtil.isStringEmpty(v.getUniqueId())) {
					v.setUniqueId(VisitUtil.getUniqueID());
					v.setRvFlag(false);
					VisitUtil.setCookieValue(request, response, "UniqueID", -1, v.getUniqueId());
					VisitUtil.setCookieValue(request, response, "Sites", -1, "_" + v.getSiteId());
				} else if (EmptyUtil.isStringNotEmpty(sites) && sites.indexOf("_" + v.getSiteId()) >= 0) {
					v.setRvFlag(true);
				} else {
					v.setRvFlag(false);
					VisitUtil.setCookieValue(request, response, "Sites", -1, sites + "_" + v.getSiteId());
				}
				item.setHost(request.getParameter("Host"));
				if (EmptyUtil.isStringNotEmpty(item.getHost())) {
					item.setHost(item.getHost().toLowerCase());
				} else {
					item.setHost("无");
				}
				v.setCookieEnabled("1".equals(request.getParameter("ce")));
				v.setFlashVersion(request.getParameter("fv"));
				v.setFlashEnabled(EmptyUtil.isNotNull(v.getFlashVersion()));
				v.setJavaEnabled("1".equals(request.getParameter("je")));
				v.setLanguage(VisitUtil.getLanguage(request.getParameter("la")));
				if (v.getLanguage().equals("其他")) {
					v.setLanguage(VisitUtil.getLanguage("; "
							+ request.getHeader("accept-language") + ";"));
				}
				v.setOs(VisitUtil.getOS(v.getUserAgent()));
				v.setBrowser(VisitUtil.getBrowser(v.getUserAgent()));
				item.setReferer(request.getParameter("Referer"));
				v.setScreen(request.getParameter("sr"));
				v.setScreen(VisitUtil.getScreen(v.getScreen()));
				v.setColorDepth(request.getParameter("cd"));
				
				item.setFrequency(Long.parseLong(request.getParameter("vq")));
				
				findVisitFacable().addVisitByLoadEvent(v, item);
			} catch (Exception e) {
			}
		}else{
			findVisitFacable().addVisitByUnloadEvent(v, item);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private VisitFacable findVisitFacable(){
		ServletContext application = getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
		VisitFacable visitFac = (VisitFacable) wac.getBean("visitFac");
		return visitFac;
	}
}
