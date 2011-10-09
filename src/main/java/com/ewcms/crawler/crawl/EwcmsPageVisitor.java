/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl;


import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.crawler.model.Gather;

import net.vidageek.crawler.Page;
import net.vidageek.crawler.PageVisitor;
import net.vidageek.crawler.Status;
import net.vidageek.crawler.Url;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class EwcmsPageVisitor implements PageVisitor {
	
	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
			+ "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	@Autowired
	private EwcmsJsoupable ewcmsJsoup;
	
	private Gather gather;
	private String urlLevel;
	
	public Gather getGather() {
		return gather;
	}

	public void setGather(Gather gather) {
		this.gather = gather;
	}

	public String getUrlLevel() {
		return urlLevel;
	}

	public void setUrlLevel(String urlLevel) {
		this.urlLevel = urlLevel;
	}

	@Override
	public void visit(Page page) {
		ewcmsJsoup.parse(gather.getId(), gather.getChannelId(), page.getUrl(), gather.getTimeOutWait().intValue());
	}

	@Override
	public void onError(Url errorUrl, Status statusError) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean followUrl(Url url) {
		String href = url.link();
		if (filters.matcher(href).matches()) {
			return false;
		}
		if (gather.getDepth().intValue() <= -1){
			
		}
		if (url.link().startsWith(href) && url.depth() <= gather.getDepth().intValue()) {
			return true;
		}
		return false;
	}
}
