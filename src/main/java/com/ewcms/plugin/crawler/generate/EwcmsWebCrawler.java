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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.plugin.crawler.generate.crawler4j.crawler.Page;
import com.ewcms.plugin.crawler.generate.crawler4j.crawler.WebCrawler;
import com.ewcms.plugin.crawler.generate.crawler4j.url.WebURL;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.util.CrawlerUtil;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class EwcmsWebCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsWebCrawler.class);

	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private Gather gather;
	private String matchRegex;
	private String filterRegex;
	private List<String> domainUrls;
	private ArticleMainServiceable articleMainService;

	public void setGather(Gather gather) {
		this.gather = gather;
	}

	public String getMatchRegex() {
		return matchRegex;
	}

	public void setMatchRegex(String matchRegex) {
		this.matchRegex = matchRegex;
	}

	public String getFilterRegex() {
		return filterRegex;
	}

	public void setFilterRegex(String filterRegex) {
		this.filterRegex = filterRegex;
	}

	public List<String> getDomainUrls() {
		return domainUrls;
	}

	public void setDomainUrls(List<String> domainUrls) {
		this.domainUrls = domainUrls;
	}

	public void setArticleMainService(ArticleMainServiceable articleMainService) {
		this.articleMainService = articleMainService;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * 根据url进行网页的解析，对返回为TRUE的网页进行抓取
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		if (url == null) return false;
		String href = url.getURL();
		if (filters.matcher(href).matches()) return false;
		String htmlType = gather.getHtmlType();
		if (href.indexOf(htmlType) > -1) return true;
		return false;
	}

	/**
	 * 解析网页内容，page类包含了丰富的方法，可以利用这些方法得到网页的内容和属性。
	 */
	@Override
	public void visit(Page page) {
		try {
			String url = page.getWebURL().getURL();
			Boolean matchUrl = false;
			for (String domainUrl : domainUrls){
				if (url.startsWith(domainUrl)){
					matchUrl = true;
					break;
				}
			}
			
			if (matchUrl){
				page.setDefaultEncoding(gather.getEncoding());
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
					Elements elements = doc.select(getMatchRegex());
					if (getFilterRegex() != null && getFilterRegex().trim().length() > 0){
						elements = elements.not(getFilterRegex());
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
}
