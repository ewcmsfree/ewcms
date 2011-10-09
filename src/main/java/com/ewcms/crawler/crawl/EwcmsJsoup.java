/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ewcms.common.lang.EmptyUtil.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.model.Article;
import com.ewcms.content.document.model.Content;
import com.ewcms.content.document.service.ArticleServiceable;
import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.crawler.model.MatchBlock;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class EwcmsJsoup implements EwcmsJsoupable{

	@Autowired
	private CrawlerFacable crawlerFac;
	@Autowired
	private ArticleServiceable articleService;
	
	@Override
	public void parse(Long gatherId, Integer channelId, String url, int timeOut) {
		try {
			Document doc = Jsoup.connect(url).timeout(timeOut).get();
			String title = doc.title();
			
			List<MatchBlock> parents = crawlerFac.findParentMatchBlockByGatherId(gatherId);
			if (isCollectionNotEmpty(parents)){
				StringBuffer sbHtml = new StringBuffer();
				childrenMatchBlock(gatherId, doc, parents, sbHtml);
				doc = Jsoup.parse(sbHtml.toString());
			}
			
			List<FilterBlock> filterParents = crawlerFac.findParentFilterBlockByGatherId(gatherId);
			if (isCollectionNotEmpty(filterParents)){
				StringBuffer sbHtml = new StringBuffer();
				childrenFilterBlock(gatherId, doc, filterParents, sbHtml);
				doc = Jsoup.parse(sbHtml.toString());
			}
			
			String contentText = doc.text();
				
			Content content = new Content();
			content.setDetail(contentText);
			content.setPage(1);
			List<Content> contents = new ArrayList<Content>();
			contents.add(content);
			
			Article article = new Article();
			article.setTitle(title);
			article.setContents(contents);
				
			articleService.addArticleByCrawler(article, "gather", channelId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void childrenMatchBlock(Long gatherId, Document doc, List<MatchBlock> matchBlocks, StringBuffer sbHtml){
		for (MatchBlock matchBlock : matchBlocks){
			String regex = matchBlock.getRegex();
			Elements elements = doc.select(regex);
			String subHtml = elements.html();
			
			List<MatchBlock> childrens = crawlerFac.findChildMatchBlockByParentId(gatherId, matchBlock.getId());
			if (!childrens.isEmpty()){
				Document subDoc = Jsoup.parse(subHtml);
				childrenMatchBlock(gatherId, subDoc, childrens, sbHtml);
			}
			
			sbHtml.append(subHtml);
		}
	}
	
	private void childrenFilterBlock(Long gatherId, Document doc, List<FilterBlock> filterBlocks, StringBuffer sbHtml){
		for (FilterBlock filterBlock : filterBlocks){
			String regex = filterBlock.getRegex();
			Elements elements  = doc.select("*").not(regex);
			String subHtml = elements.html();
			
			List<FilterBlock> childrens = crawlerFac.findChildFilterBlockByParentId(gatherId, filterBlock.getId());
			if (!childrens.isEmpty()){
				Document subDoc = Jsoup.parse(subHtml);
				childrenFilterBlock(gatherId, subDoc, childrens, sbHtml);
			}
			
			sbHtml.append(subHtml);
		}
	}
}
