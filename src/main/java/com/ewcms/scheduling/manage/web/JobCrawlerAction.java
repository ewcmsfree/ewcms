/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.scheduling.BaseException;
import com.ewcms.function.crawler.manager.CrawlerFacable;
import com.ewcms.function.crawler.model.Gather;
import com.ewcms.scheduling.generate.job.crawler.EwcmsJobCrawlerFacable;
import com.ewcms.scheduling.generate.job.crawler.model.EwcmsJobCrawler;
import com.ewcms.scheduling.manage.util.ConversionUtil;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author 吴智俊
 */
public class JobCrawlerAction extends ActionSupport {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private EwcmsJobCrawlerFacable ewcmsJobCrawlerFac;
	@Autowired
	private CrawlerFacable crawlerFac;
	
	private Long gatherId;
	private PageDisplayVO pageDisplayVo;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}


	public PageDisplayVO getPageDisplayVo() {
		return pageDisplayVo;
	}

	public void setPageDisplayVo(PageDisplayVO pageDisplayVo) {
		this.pageDisplayVo = pageDisplayVo;
	}

	public String getJobCrawler(){
		EwcmsJobCrawler jobCrawler = ewcmsJobCrawlerFac.findJobCrawlerByGatherId(getGatherId());
		PageDisplayVO vo = new PageDisplayVO();
		if (jobCrawler != null){
			vo = ConversionUtil.constructPageVo(jobCrawler);
		}else{
			Gather gather = crawlerFac.findGather(getGatherId());
			if (gather != null){
				vo.setLabel(gather.getName());
			}
		}
		setPageDisplayVo(vo);
		return INPUT;
	}

	public String saveJobCrawler() {
		try{
			Integer jobId = ewcmsJobCrawlerFac.saveOrUpdateJobCrawler(getGatherId(), getPageDisplayVo());
			if (jobId == null){
				addActionMessage("操作失败");
			}
			EwcmsJobCrawler jobCrawler = ewcmsJobCrawlerFac.findJobCrawlerByGatherId(getGatherId());
			if (jobCrawler != null){
				PageDisplayVO vo = ConversionUtil.constructPageVo(jobCrawler);
				setPageDisplayVo(vo);
				addActionMessage("数据保存成功!");
			}
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
}
