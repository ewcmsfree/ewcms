/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl;

import static com.ewcms.common.lang.EmptyUtil.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.crawler.BaseException;
import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.model.Gather;
import com.ewcms.crawler.model.UrlLevel;

import net.vidageek.crawler.PageCrawler;
import net.vidageek.crawler.config.CrawlerConfiguration;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class EwcmsCrawl implements EwcmsCrawlable {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsCrawl.class);
	
	@Autowired
	private CrawlerFacable crawlerFac;
	@Autowired
	private EwcmsPageVisitor ewcmsPageVisitor;
	
	@Override
	public void crawl(Long gatherId) throws BaseException{
		Gather gather = crawlerFac.findGather(gatherId);
		
		if (isNull(gather)){
			logger.error("采集的记录不存在");
			throw new BaseException("采集的记录不存在","采集的记录不存在");
		}
		
		List<UrlLevel> urlLevels = gather.getUrlLevels();
		if (isCollectionEmpty(urlLevels)){
			logger.error("采集的URL地址未设定");
			throw new BaseException("采集的URL地址未设定","采集的URL地址未设定");
		}
		
		if (isNull(gather.getChannelId())){
			logger.error("收集的频道未设定");
			throw new BaseException("收集的频道未设定","收集的频道未设定");
		}
		
		ewcmsPageVisitor.setGather(gather);
		
		for (UrlLevel urlLevel : urlLevels){
			CrawlerConfiguration cfg = new CrawlerConfiguration(urlLevel.getUrl());
			PageCrawler crawler = new PageCrawler(cfg);
			ewcmsPageVisitor.setUrlLevel(urlLevel.getUrl());
			crawler.crawl(ewcmsPageVisitor);
		}
	}
}
