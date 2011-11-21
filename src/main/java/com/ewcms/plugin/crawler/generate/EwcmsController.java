/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.generate;

import static com.ewcms.common.lang.EmptyUtil.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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
import com.ewcms.plugin.crawler.generate.crawler4j.util.IO;
import com.ewcms.plugin.crawler.manager.CrawlerFacable;
import com.ewcms.plugin.crawler.model.Domain;
import com.ewcms.plugin.crawler.model.Gather;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
@Scope("prototype")
public class EwcmsController implements EwcmsControllerable {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsController.class);
	
	public static final String ROOT_FOLDER = "/tmp/crawler/";
	
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
		
		Long timestamp = Calendar.getInstance().getTime().getTime();
		String gatherFolderPath = ROOT_FOLDER + "/" + timestamp;
		
		PageFetcher pageFetcher;
		DocIDServer docIDServer;
		Frontier frontier;
		CrawlController controller;
		EwcmsWebCrawler ewcmsWebCrawler;
		try{
			pageFetcher = new PageFetcher();
			docIDServer = new DocIDServer();
			frontier = new Frontier();
			
			controller = new CrawlController(gatherFolderPath, pageFetcher, docIDServer, frontier);
			
			String[] crawlDomains = new String[urlLevels.size()];
			for (int index = 0; index < urlLevels.size(); index++){
				crawlDomains[index] = urlLevels.get(index).getUrl();
				controller.addSeed(crawlDomains[index]);
			}
			
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
			ewcmsWebCrawler.setCrawlDomains(crawlDomains);
			ewcmsWebCrawler.setCrawlerFac(crawlerFac);
			ewcmsWebCrawler.setGather(gather);
			
			controller.start(ewcmsWebCrawler, numberOfCrawlers);
		}catch(IOException e){
			logger.error(e.getLocalizedMessage());
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}finally{
			try{
				File gatherFolder = new File(gatherFolderPath);
				if (gatherFolder.exists()) IO.deleteFolder(gatherFolder);
			}catch(Exception e){
				logger.error(e.getLocalizedMessage());
			}
			ewcmsWebCrawler = null;
			docIDServer = null;
			frontier = null;
			pageFetcher = null;
			controller = null;
		}
	}
}
