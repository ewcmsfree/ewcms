/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.model.UrlLevel;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class UrlLevelAction extends CrudBaseAction<UrlLevel, Long> {
	
	private static final long serialVersionUID = -7991291404500643405L;

	@Autowired
	private CrawlerFacable crawlerFac;
	
	private Long gatherId;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}

	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	public UrlLevel getUrlLevelVo(){
		return super.getVo();
	}
	
	public void setUrlLevelVo(UrlLevel urlLevelVo){
		super.setVo(urlLevelVo);
	}
	
	@Override
	protected Long getPK(UrlLevel vo) {
		return vo.getId();
	}

	@Override
	protected UrlLevel getOperator(Long pk) {
		return crawlerFac.findUrlLevel(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delUrlLevel(getGatherId(), pk);
	}

	@Override
	protected Long saveOperator(UrlLevel vo, boolean isUpdate) {
		return crawlerFac.addAndUpdUrlLevel(getGatherId(), vo);
	}

	@Override
	protected UrlLevel createEmptyVo() {
		return new UrlLevel();
	}
}
