/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl;

import static com.ewcms.common.lang.EmptyUtil.isCollectionNotEmpty;

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
import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.crawl.crawler4j.crawler.Page;
import com.ewcms.crawler.crawl.crawler4j.crawler.WebCrawler;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;
import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.crawler.util.CrawlerUserName;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class EwcmsWebCrawler extends WebCrawler {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsWebCrawler.class);

	Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	private Gather gather;
	private String[] crawlDomains;
	private CrawlerFacable crawlerFac;
	private ArticleMainServiceable articleMainService;

	public void setGather(Gather gather) {
		this.gather = gather;
	}

	public void setCrawlDomains(String[] crawlDomains) {
		this.crawlDomains = crawlDomains;
	}

	public void setCrawlerFac(CrawlerFacable crawlerFac) {
		this.crawlerFac = crawlerFac;
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
		if (href.indexOf(htmlType) > -1){
			for (String domain : crawlDomains) {
				if (href.startsWith(domain)) {
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
			page.setDefaultEncoding(gather.getEncoding());

			Document doc = Jsoup.connect(page.getWebURL().getURL()).timeout(gather.getTimeOutWait().intValue() * 1000).get();

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

			List<MatchBlock> parents = crawlerFac
					.findParentMatchBlockByGatherId(gather.getId());
			if (isCollectionNotEmpty(parents)) {
				StringBuffer sbHtml = new StringBuffer();
				childrenMatchBlock(gather.getId(), doc, parents, sbHtml);
				doc = Jsoup.parse(sbHtml.toString());
			}

			List<FilterBlock> filterParents = crawlerFac
					.findParentFilterBlockByGatherId(gather.getId());
			if (isCollectionNotEmpty(filterParents)) {
				StringBuffer sbHtml = new StringBuffer();
				childrenFilterBlock(gather.getId(), doc, filterParents, sbHtml);
				doc = Jsoup.parse(sbHtml.toString());
			}

			String contentText = doc.html();

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

			articleMainService.addArticleMainByCrawler(article, CrawlerUserName.USER_NAME, gather.getChannelId());
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
		crawlDomains = null;
		crawlerFac = null;
		articleMainService = null;
	}

	private void childrenMatchBlock(Long gatherId, Document doc,
			List<MatchBlock> matchBlocks, StringBuffer sbHtml) {
		for (MatchBlock matchBlock : matchBlocks) {
			String regex = matchBlock.getRegex();
			Elements elements = doc.select(regex);
			String subHtml = elements.html();

			List<MatchBlock> childrens = crawlerFac.findChildMatchBlockByParentId(gatherId, matchBlock.getId());
			if (!childrens.isEmpty()) {
				Document subDoc = Jsoup.parse(subHtml);
				childrenMatchBlock(gatherId, subDoc, childrens, sbHtml);
			}

			sbHtml.append(subHtml);
		}
	}

	private void childrenFilterBlock(Long gatherId, Document doc,
			List<FilterBlock> filterBlocks, StringBuffer sbHtml) {
		for (FilterBlock filterBlock : filterBlocks) {
			String regex = filterBlock.getRegex();
			Elements elements = doc.select("*").not(regex);
			String subHtml = elements.html();

			List<FilterBlock> childrens = crawlerFac
					.findChildFilterBlockByParentId(gatherId,
							filterBlock.getId());
			if (!childrens.isEmpty()) {
				Document subDoc = Jsoup.parse(subHtml);
				childrenFilterBlock(gatherId, subDoc, childrens, sbHtml);
			}

			sbHtml.append(subHtml);
		}
	}
}
