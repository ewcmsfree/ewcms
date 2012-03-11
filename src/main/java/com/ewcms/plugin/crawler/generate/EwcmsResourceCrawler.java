/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;

import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.content.resource.model.Resource;
import com.ewcms.core.site.model.Site;
import com.ewcms.plugin.crawler.generate.crawler.Page;
import com.ewcms.plugin.crawler.generate.crawler.WebCrawler;
import com.ewcms.plugin.crawler.generate.parser.BinaryParseData;
import com.ewcms.plugin.crawler.generate.url.WebURL;
import com.ewcms.plugin.crawler.generate.util.Cryptography;
import com.ewcms.plugin.crawler.generate.util.IO;

/**
 * 
 * @author wu_zhijun
 *
 */
public class EwcmsResourceCrawler extends WebCrawler {
	
	private static final Pattern IMG_PATTERNS = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");
	private static final Pattern FLASH_PATTERNS = Pattern.compile(".*(\\.(swf|flv))$");
	private static final Pattern VIDEO_PATTERNS = Pattern.compile(".*(\\.(mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|rm|smil|wmv|wma))$");
	
	private String[] crawlDomains;
	private String storageFolderName;
	private Boolean isImage = false;
	private Boolean isFlash = false;
	private Boolean isVideo = false;
	private Boolean isAnnex = false;
	private Pattern annex_patterns;
	private Site site;
	private ResourceFacable resourceFac;
	
	private File storageFolder;
	
	@Override
	public void onStart() {
		super.onStart();
		crawlDomains = (String[]) myController.getCustomData();
		
		resourceFac = (ResourceFacable)getPassingParameters().get("resourceFac");
		site = (Site)getPassingParameters().get("site");
		
		isImage = (Boolean)getPassingParameters().get("isImage");
		isFlash = (Boolean)getPassingParameters().get("isFlash");
		isVideo = (Boolean)getPassingParameters().get("isVideo");
		isAnnex = (Boolean)getPassingParameters().get("isAnnex");
		String annexType = (String)getPassingParameters().get("annexType");
		if (isAnnex){
			if (annexType == null || annexType.trim().length() == 0){
				annex_patterns = Pattern.compile(".*(\\.(*))$");
			}else{
				annex_patterns = Pattern.compile(".*(\\.(" + annexType + "))$");
			}
		}
		
		storageFolderName = (String)getPassingParameters().get("storageFolderName");
		storageFolder = new File(storageFolderName);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}
	}
	
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (isImage && IMG_PATTERNS.matcher(href).matches()) return true;
		if (isFlash && FLASH_PATTERNS.matcher(href).matches()) return true;
		if (isVideo && VIDEO_PATTERNS.matcher(href).matches()) return true;
		if (isAnnex && annex_patterns.matcher(href).matches()) return true;
		if (crawlDomains != null && crawlDomains.length > 0){
			for (String domain : crawlDomains) {
				if (href.startsWith(domain)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();

		if (!(page.getParseData() instanceof BinaryParseData)) return;
		//除了附件资源以外，不处理下载小于1k的资源
		if (!annex_patterns.matcher(url).matches() && page.getContentData().length < 10 * 1024) return;

		// 获取唯一的名称
		String extension = url.substring(url.lastIndexOf("."));
		String hashedName = Cryptography.MD5(url) + extension;
		
		String destination = storageFolder.getAbsolutePath() + "/" + hashedName;
		// 保存文件
		IO.writeBytesToFile(page.getContentData(), destination);
		System.out.println("Stored: " + url);
		
		File file = new File(destination);
		
		Resource.Type type = Resource.Type.ANNEX;
		if (IMG_PATTERNS.matcher(url).matches()) type = Resource.Type.IMAGE;
		else if (FLASH_PATTERNS.matcher(url).matches()) type = Resource.Type.FLASH;
		else if (VIDEO_PATTERNS.matcher(url).matches())	type = Resource.Type.VIDEO;
		
		try {
			resourceFac.uploadResource(site, file, url, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onBeforeExit() {
		crawlDomains = null;
		storageFolderName = null;
		isImage = null;
		isFlash = null;
		isVideo = null;
		isAnnex = null;
		annex_patterns = null;
		site = null;
		resourceFac = null;
	}
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		if (statusCode != HttpStatus.SC_OK) {
			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				System.out.println("Broken link: " + webUrl.getURL() + ", this link was found in page with docid: " + webUrl.getParentDocid());
			} else {
				System.out.println("Non success status for link: " + webUrl.getURL() + ", status code: " + statusCode + ", description: " + statusDescription);
			}
		}
	}
}
