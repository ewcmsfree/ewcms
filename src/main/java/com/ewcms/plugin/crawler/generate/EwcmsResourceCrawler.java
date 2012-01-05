package com.ewcms.plugin.crawler.generate;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

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
	private static final Pattern filters = Pattern.compile(".*(\\.(css|js|pdf|zip|rar|gz))$");

	private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");
	private static final Pattern flashPatterns = Pattern.compile(".*(\\.(swf|flv))$");
	private static final Pattern videoPatterns = Pattern.compile(".*(\\.(mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|rm|smil|wmv|wma))$");
	
	private String[] crawlDomains;
	private String storageFolderName;
	private Boolean isImage;
	private Boolean isFlash;
	private Boolean isVideo;
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
		
		storageFolderName = (String)getPassingParameters().get("storageFolderName");
		storageFolder = new File(storageFolderName);
		if (!storageFolder.exists()) {
			storageFolder.mkdirs();
		}
	}
	
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (filters.matcher(href).matches()) return false;
		if (isImage && imgPatterns.matcher(href).matches()) return true;
		if (isFlash && flashPatterns.matcher(href).matches()) return true;
		if (isVideo && videoPatterns.matcher(href).matches()) return true;
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

		// We are only interested in processing images
		if (!(page.getParseData() instanceof BinaryParseData)) return;
		//if (!imgPatterns.matcher(url).matches()) return;
		// Not interested in very small images
		if (page.getContentData().length < 10 * 1024) return;

		// get a unique name for storing this image
		String extension = url.substring(url.lastIndexOf("."));
		String hashedName = Cryptography.MD5(url) + extension;
		
		String destination = storageFolder.getAbsolutePath() + "/" + hashedName;
		// store image
		IO.writeBytesToFile(page.getContentData(), destination);
		System.out.println("Stored: " + url);
		
		File file = new File(destination);
		
		Resource.Type type = Resource.Type.ANNEX;
		if (imgPatterns.matcher(url).matches())
			type = Resource.Type.IMAGE;
		else if (flashPatterns.matcher(url).matches())
			type = Resource.Type.FLASH;
		else if (videoPatterns.matcher(url).matches())
			type = Resource.Type.VIDEO;

		try {
			resourceFac.uploadResource(site, file, url, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
