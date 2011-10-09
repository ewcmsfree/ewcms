/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Channel;
import com.ewcms.crawler.BaseException;
import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.crawl.EwcmsCrawlable;
import com.ewcms.crawler.model.Gather;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wuzhijun
 *
 */
public class GatherAction extends CrudBaseAction<Gather, Long> {

	private static final long serialVersionUID = -3623131322596928713L;

	@Autowired
	private CrawlerFacable crawlerFac;
	@Autowired
	private SiteFac siteFac;
	@Autowired
	private EwcmsCrawlable ewcmsCrawl;
	
	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	public Gather getGatherVo(){
		return super.getVo();
	}
	
	public void setGatherVo(Gather gatherVo){
		super.setVo(gatherVo);
	}
	
	@Override
	protected Long getPK(Gather vo) {
		return vo.getId();
	}

	private String channelName;
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	protected Gather getOperator(Long pk) {
		Gather gather = crawlerFac.findGather(pk);
		Integer channelId = gather.getChannelId();
		if (channelId != null){
			Channel channel = siteFac.getChannel(channelId);
			if (channel != null) setChannelName(channel.getName());
		}
		return gather;
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delGather(pk);
	}

	@Override
	protected Long saveOperator(Gather vo, boolean isUpdate) {
		if (isUpdate){
			return crawlerFac.updGather(vo);
		}else{
			return crawlerFac.addGather(vo);
		}
	}

	@Override
	protected Gather createEmptyVo() {
		return new Gather();
	}

	public void crawlRun(){
		if (getSelections() != null && getSelections().size() == 1){
			try {
				ewcmsCrawl.crawl(getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			} catch (BaseException e) {
				Struts2Util.renderJson(JSONUtil.toJSON(e.getPageMessage()));
			}
		}
	}
}
