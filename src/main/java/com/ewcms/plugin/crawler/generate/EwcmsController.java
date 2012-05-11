/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.generate;

import static com.ewcms.common.lang.EmptyUtil.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.service.ArticleMainServiceable;
import com.ewcms.content.resource.ResourceFacable;
import com.ewcms.core.site.model.Site;
import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.crawler.generate.crawler.CrawlConfig;
import com.ewcms.plugin.crawler.generate.crawler.CrawlController;
import com.ewcms.plugin.crawler.generate.fetcher.PageFetcher;
import com.ewcms.plugin.crawler.generate.robotstxt.RobotstxtConfig;
import com.ewcms.plugin.crawler.generate.robotstxt.RobotstxtServer;
import com.ewcms.plugin.crawler.generate.util.IO;
import com.ewcms.plugin.crawler.manager.service.GatherServiceable;
import com.ewcms.plugin.crawler.model.Domain;
import com.ewcms.plugin.crawler.model.FilterBlock;
import com.ewcms.plugin.crawler.model.Gather;
import com.ewcms.plugin.crawler.model.MatchBlock;
import com.ewcms.plugin.crawler.util.CrawlerUtil;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class EwcmsController implements EwcmsControllerable {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsController.class);
	
	@Autowired
	private GatherServiceable gatherService;
	@Autowired
	private ArticleMainServiceable articleMainService;
	@Autowired
	private ResourceFacable resourceFac;
	
	private CrawlController controller = null;
	
	@Override
	public void startCrawl(Long gatherId) throws BaseException{
		Gather gather = gatherService.findGather(gatherId);
		
		if (isNull(gather)){
			logger.warn("采集的记录不存在！");
			throw new BaseException("采集的记录不存在！","采集的记录不存在！");
		}
		
		if (!gather.getStatus()){
			logger.warn("采集的为停用状态，不能执行！");
			throw new BaseException("采集的为停用状态，不能执行！","采集的为停用状态，不能执行！");
		}
		
		if (isNull(gather.getBaseURI()) || gather.getBaseURI().trim().length() == 0){
			logger.warn("采集的网站地址未设定！");
			throw new BaseException("采集的网站地址未设定！","采集的网站地址未设定！");
		}
		
		if (gather.getDomains() == null || gather.getDomains().size() == 0){
			logger.warn("采集的地址区配未设定！");
			throw new BaseException("采集的地址区配未设定！","采集的地址区配未设定！");
		}
		
		if (!gather.getIsLocal() && gather.getType() == Gather.Type.CONTENT && isNull(gather.getChannelId())){
			logger.warn("收集的频道未设定！");
			throw new BaseException("收集的频道未设定！","收集的频道未设定！");
		}
		
		String gatherFolderPath = CrawlerUtil.ROOT_FOLDER + gatherId + "-"+ Calendar.getInstance().getTimeInMillis();
		try{
			File gatherFolder = new File(gatherFolderPath);
			if (gatherFolder.exists()){ 
				boolean delete = IO.deleteFolderContents(gatherFolder);
				if (!delete){
					logger.info("采集器正在运行中，无须再运行！");
					throw new BaseException("采集器正在运行中，无须再运行！","采集器正在运行中，无须再运行！");
				}
			}
		}catch(Exception e){
			logger.error("目录删除失败！");
			throw new BaseException("采集器正在运行中，无须再运行！","采集器正在运行中，无须再运行！");
		}
			
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(gatherFolderPath);
		
		HashMap<String,Object> passingParameters = new HashMap<String,Object>();
		if (gather.getType() == Gather.Type.RESOURCE){
			Site site = EwcmsContextUtil.getCurrentSite();
			
			config.setIncludeBinaryContentInCrawling(true);
			
			passingParameters.put("resourceFac", resourceFac);
			passingParameters.put("storageFolderName", gatherFolderPath + "/resource");
			passingParameters.put("site", site);
			passingParameters.put("isImage", gather.getIsImage());
			passingParameters.put("isFlash", gather.getIsFlash());
			passingParameters.put("isVideo", gather.getIsVideo());
			passingParameters.put("isAnnex", gather.getIsAnnex());
			passingParameters.put("annexType", gather.getAnnexType());
		}else{
			config.setIncludeBinaryContentInCrawling(gather.getDownloadFile());
			
			passingParameters.put("gatherService", gatherService);
			passingParameters.put("articleMainService", articleMainService);
			passingParameters.put("gather", gather);
			passingParameters.put("matchRegex", initMatchBlock(gatherId));
			passingParameters.put("filterRegex",initFilterBlock(gatherId));
		}
		
		config.setPolitenessDelay(2000);
		//设置抓取的页面的最大数量，默认值是-1无限深度
		config.setMaxPagesToFetch(gather.getMaxPage().intValue());
		if (gather.getProxy()){
			config.setProxyHost(gather.getProxyHost());
			config.setProxyPort(gather.getProxyPort());
			config.setProxyUsername(gather.getProxyUserName());
			config.setProxyPassword(gather.getProxyPassWord());
		}
		config.setMaxDepthOfCrawling(gather.getDepth().intValue());
		config.setResumableCrawling(true);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		
		try{
			controller = new CrawlController(config, pageFetcher, robotstxtServer, passingParameters);
			String[] crawlerDomains = conversionDomain(gather.getDomains());
			if (crawlerDomains != null) controller.setCustomData(crawlerDomains);
			controller.addSeed(gather.getBaseURI());
			
			//并发线程数
			int numberOfCrawlers = gather.getThreadCount();
			if (gather.getType() == Gather.Type.RESOURCE){
				controller.startNonBlocking(EwcmsResourceCrawler.class, numberOfCrawlers);
			}else{
				controller.startNonBlocking(EwcmsContentCrawler.class, numberOfCrawlers);
			}
			controller.waitUntilFinish();
		}catch(Exception e){
			logger.error("网络采集器运行失败！- {}", e.getLocalizedMessage());
		}finally{
			passingParameters.clear();
			passingParameters = null;
		}
	}
	
	@Override
	public void interruptCrawl() throws BaseException{
		if (controller != null){
			controller.Shutdown();
			controller.waitUntilFinish();
		}
	}
	
	private String initMatchBlock(Long gatherId){
		List<String> matchRegexs = new ArrayList<String>();
		
		List<MatchBlock> parents = gatherService.findParentMatchBlockByGatherId(gatherId);
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
		List<MatchBlock> childrens = gatherService.findChildMatchBlockByParentId(gatherId, parent.getId());
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
		
		List<FilterBlock> parents = gatherService.findParentFilterBlockByGatherId(gatherId);
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
		List<FilterBlock> childrens = gatherService.findChildFilterBlockByParentId(gatherId, parent.getId());
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

	private String[] conversionDomain(Set<Domain> domains){
		if (domains != null && !domains.isEmpty()){
			String[] crawlerDomain = new String[domains.size()];
			int i = 0;
			for (Domain domain : domains){
				crawlerDomain[i] = domain.getUrl();
				i++;
			}
			return crawlerDomain;
		}
		return null;
	}
}
