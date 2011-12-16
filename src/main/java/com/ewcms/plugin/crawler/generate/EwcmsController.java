/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.generate;

import static com.ewcms.common.lang.EmptyUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.crawler.generate.crawler4j.crawler.CrawlController;
import com.ewcms.plugin.crawler.generate.crawler4j.crawler.PageFetcher;
import com.ewcms.plugin.crawler.generate.crawler4j.frontier.DocIDServer;
import com.ewcms.plugin.crawler.generate.crawler4j.frontier.Frontier;
import com.ewcms.plugin.crawler.manager.CrawlerFacable;
import com.ewcms.plugin.crawler.model.Domain;
import com.ewcms.plugin.crawler.model.FilterBlock;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.model.MatchBlock;
import com.ewcms.plugin.crawler.util.CrawlerUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
@Scope("prototype")
public class EwcmsController implements EwcmsControllerable {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsController.class);
	
	@Autowired
	private CrawlerFacable crawlerFac;
	@Autowired
	private ArticleMainServiceable articleMainService;
	
	@Override
	public void crawl(Long gatherId) throws BaseException{
		Gather gather = crawlerFac.findGather(gatherId);
		
		if (isNull(gather)){
			logger.error("采集的记录不存在！");
			throw new BaseException("采集的记录不存在！","采集的记录不存在！");
		}
		
		if (!gather.getStatus()){
			logger.warn("采集的为停用状态，不能执行！");
			throw new BaseException("采集的为停用状态，不能执行！","采集的为停用状态，不能执行！");
		}
		
		List<Domain> urlLevels = gather.getDomains();
		if (isCollectionEmpty(urlLevels)){
			logger.error("采集的URL地址未设定！");
			throw new BaseException("采集的URL地址未设定！","采集的URL地址未设定！");
		}
		
		if (isNull(gather.getChannelId())){
			logger.error("收集的频道未设定！");
			throw new BaseException("收集的频道未设定！","收集的频道未设定！");
		}
		
		String gatherFolderPath = CrawlerUtil.ROOT_FOLDER + gatherId;
//		try{
//			File gatherFolder = new File(CrawlerUtil.ROOT_FOLDER + gatherId + "/frontier");
//			if (gatherFolder.exists()) IO.deleteFolderContents(gatherFolder);
//		}catch(Exception e){
//		}
			
		PageFetcher pageFetcher = new PageFetcher();
		DocIDServer docIDServer = new DocIDServer();
		Frontier frontier = new Frontier();
		CrawlController controller;
		EwcmsWebCrawler ewcmsWebCrawler;
		try{
			controller = new CrawlController(gatherFolderPath, pageFetcher, docIDServer, frontier);
			
			controller.addSeed(gather.getBaseURI());
			controller.setPolitenessDelay(200);
			
			//设置抓取的页面的最大数量，默认值是-1无限深度
			controller.setMaximumPagesToFetch(gather.getMaxPage());
			//并发线程数
			int numberOfCrawlers = gather.getThreadCount();
			//代理
			if (gather.getProxy()){
				controller.setProxy(gather.getProxyHost(), gather.getProxyPort(), gather.getProxyUserName(), gather.getProxyPassWord());
			}
			
			ewcmsWebCrawler = new EwcmsWebCrawler();
			//设置最大爬行深度，默认值是-1无限深度
			ewcmsWebCrawler.setMaximumCrawlDepth(gather.getDepth().shortValue());
			//是否包含二进制文件
			ewcmsWebCrawler.setIncludeBinaryContent(gather.getDownloadFile());
			
			ewcmsWebCrawler.setArticleMainService(articleMainService);
			ewcmsWebCrawler.setGather(gather);
			ewcmsWebCrawler.setMatchRegex(initMatchBlock(gatherId));
			ewcmsWebCrawler.setFilterRegex(initFilterBlock(gatherId));
			ewcmsWebCrawler.setDomainUrls(conversionDomain(gather.getDomains()));
			
			controller.start(ewcmsWebCrawler, numberOfCrawlers);
		}catch(IOException e){
			logger.error(e.getLocalizedMessage());
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}finally{
			ewcmsWebCrawler = null;
			docIDServer = null;
			frontier = null;
			pageFetcher = null;
			controller = null;
		}
	}
	
	private String initMatchBlock(Long gatherId){
		List<String> matchRegexs = new ArrayList<String>();
		
		List<MatchBlock> parents = crawlerFac.findParentMatchBlockByGatherId(gatherId);
		for (MatchBlock parent : parents){
			String regex = "";
			String matchRegex = parent.getRegex();
			if (matchRegex != null && matchRegex.length() > 0){
				regex = matchRegex;
			}
			childrenMatchBlock(gatherId, regex, parent, matchRegexs);
		}
		
		if (matchRegexs.isEmpty()) return "*";
		
		String regex = "";
		for (String matchRegex : matchRegexs){
			if (matchRegex == null || matchRegex.trim().length() == 0) continue;
			regex += matchRegex + ", ";
		}
		if (regex.length() > 0){
			regex = regex.substring(0, regex.length() - 2);
		}
		
		return regex;
	}
		
	private void childrenMatchBlock(Long gatherId, String regex, MatchBlock parent, List<String> matchRegexs){
		List<MatchBlock> childrens = crawlerFac.findChildMatchBlockByParentId(gatherId, parent.getId());
		if (childrens.isEmpty()){
			matchRegexs.add(regex);
		}else{
			for (MatchBlock children : childrens){
				String childrenRegex = regex;
				String matchRegex = children.getRegex();
				if (matchRegex != null && matchRegex.length() > 0){
					childrenRegex += " > " + matchRegex;
				}
				childrenMatchBlock(gatherId, childrenRegex, children, matchRegexs);
				
			}
		}
	}
	
	private String initFilterBlock(Long gatherId){
		List<String> filterRegexs = new ArrayList<String>();
		
		List<FilterBlock> parents = crawlerFac.findParentFilterBlockByGatherId(gatherId);
		for (FilterBlock parent : parents){
			String regex = "";
			String filterRegex = parent.getRegex();
			if (filterRegex != null && filterRegex.length() > 0){
				regex = filterRegex;
			}
			childrenFilterBlock(gatherId, regex, parent, filterRegexs);
		}
		
		if (filterRegexs.isEmpty()) return "";
		
		String regex = "";
		for (String filterRegex : filterRegexs){
			if (filterRegex == null || filterRegex.trim().length() == 0) continue;
			regex += filterRegex + ", ";
		}
		if (regex.length() > 0){
			regex = regex.substring(0, regex.length() - 2);
		}
		
		return regex;
	}
		
	private void childrenFilterBlock(Long gatherId, String regex, FilterBlock parent, List<String> filterRegexs){
		List<FilterBlock> childrens = crawlerFac.findChildFilterBlockByParentId(gatherId, parent.getId());
		if (childrens.isEmpty()){
			filterRegexs.add(regex);
		}else{
			for (FilterBlock children : childrens){
				String childrenRegex = regex;
				String filterRegex = children.getRegex();
				if (filterRegex != null && filterRegex.length() > 0){
					childrenRegex += " > " + filterRegex;
				}
				childrenFilterBlock(gatherId, childrenRegex, children, filterRegexs);
			}
		}
	}

	private List<String> conversionDomain(List<Domain> domains){
		List<String> domainURLs = new ArrayList<String>();
		if (domains != null && !domains.isEmpty()){
			for (Domain domain : domains){
				domainURLs.add(domain.getUrl());
			}
		}
		return domainURLs;
	}
}
