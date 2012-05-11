/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.generate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.plugin.crawler.generate.crawler.Page;
import com.ewcms.plugin.crawler.generate.crawler.WebCrawler;
import com.ewcms.plugin.crawler.generate.url.WebURL;
import com.ewcms.plugin.crawler.manager.service.GatherServiceable;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.model.Storage;
import com.ewcms.plugin.crawler.util.CrawlerUtil;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class EwcmsContentCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsContentCrawler.class);

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	private String[] crawlDomains;
	private ArticleMainServiceable articleMainService;
	private GatherServiceable gatherService;
	private Gather gather;
	private String matchRegex;
	private String filterRegex;
	private String htmlType;
	private Boolean isLocal;
	private String[] keys;

	@Override
	public void onStart() {
		super.onStart();
		crawlDomains = (String[]) myController.getCustomData();
		articleMainService = (ArticleMainServiceable)getPassingParameters().get("articleMainService");
		gatherService = (GatherServiceable)getPassingParameters().get("gatherService");
		matchRegex = (String)getPassingParameters().get("matchRegex");
		filterRegex = (String)getPassingParameters().get("filterRegex");
		gather = (Gather)getPassingParameters().get("gather");
		htmlType = gather.getHtmlType();
		isLocal = gather.getIsLocal();
		if (isLocal){
			keys = gather.getKeys().split(",");
		}
	}

	/**
	 * 根据url进行网页的解析，对返回为TRUE的网页进行抓取
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) return false;
		if (href.lastIndexOf("." + htmlType) == -1)	return false;
		if (crawlDomains != null && crawlDomains.length > 0){
			for (String crawlDomain : crawlDomains) {
				if (href.startsWith(crawlDomain)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 解析网页内容，page类包含了丰富的方法，可以利用这些方法得到网页的内容和属性。
	 */
	@Override
	public void visit(Page page) {
		try {
			String url = page.getWebURL().getURL();
			
			page.setContentType("text/html; charset=" + gather.getEncoding());
			Document doc = Jsoup.connect(url).timeout(gather.getTimeOutWait().intValue() * 1000).get();
	
			String title = doc.title();
			if (gather.getTitleExternal() && gather.getTitleRegex() != null && gather.getTitleRegex().length() > 0) {
				Elements titleEles = doc.select(gather.getTitleRegex());
				if (!titleEles.isEmpty()) {
					String tempTitle = titleEles.text();
					if (tempTitle != null && tempTitle.length() > 0) {
						title = tempTitle;
					}
				}
			}
			
			if (title != null && title.trim().length() > 0){
				Elements elements = doc.select(matchRegex);
				if (filterRegex != null && filterRegex.trim().length() > 0){
						elements = elements.not(filterRegex);
				}
				if (!elements.isEmpty()){
					String subHtml = elements.html();
					Document blockDoc = Jsoup.parse(subHtml);
					String contentText = blockDoc.html();
				
					if (gather.getRemoveHref()) {
						Document moveDoc = Jsoup.parse(contentText);
						Elements moveEles = moveDoc.select("*").not("a");
						contentText = moveEles.html();
					}
					if (gather.getRemoveHtmlTag())
						contentText = doc.text();
		
					if (isLocal){
						contentText = doc.text();
						
						Boolean isMatcher = true;
						for (int i = 0 ; i < keys.length ; i++){
							Boolean result = Pattern.compile(keys[i].trim()).matcher(contentText).find();
							if (!result){
								isMatcher = false;
								break;
							}
						}
						
						if (isMatcher){
							Storage storage = new Storage();
							storage.setGatherId(gather.getId());
							storage.setGatherName(gather.getName());
							storage.setTitle(title);
							storage.setUrl(url);
							try{
								gatherService.addStorage(storage);
							}catch(Exception e){
								logger.error("save storage error : {}", e.getLocalizedMessage());
							}finally{
								storage = null;
							}
						}
					}else{
						Content content = new Content();
						content.setDetail(contentText);
						content.setPage(1);
						List<Content> contents = new ArrayList<Content>();
						contents.add(content);
					
						Article article = new Article();
						article.setTitle(title);
						article.setContents(contents);
					
						articleMainService.addArticleMainByCrawler(article, gather.getChannelId(), CrawlerUtil.USER_NAME);
					}
				}
			}
		} catch (IOException e) {
			logger.warn(e.getLocalizedMessage());
		}
	}

	/**
	 * 当作业完成时，由控制器调用获得此crawler本地数据
	 */
	@Override
	public Object getMyLocalData() {
		return null;
	}

	/**
	 * 控制器退出之前执行
	 */
	@Override
	public void onBeforeExit() {
		gather = null;
		matchRegex = null;
		filterRegex = null;
		articleMainService = null;
	}
	
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		if (statusCode != HttpStatus.SC_OK) {
			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				logger.info("Broken link: {} , this link was found in page with docid: {}" , webUrl.getURL(), webUrl.getParentDocid());
			} else {
				logger.info("Non success status for link: {} , status code: {} , description: {}", webUrl.getURL(), statusCode);
			}
		}
	}
}
