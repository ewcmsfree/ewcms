/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.model.FilterBlock;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class FilterBlockAction extends CrudBaseAction<FilterBlock, Long> {
	
	private static final long serialVersionUID = -7991291404500643405L;

	@Autowired
	private CrawlerFacable crawlerFac;
	
	private Long gatherId;
	private Long parentId;
	
	public Long getGatherId() {
		return gatherId;
	}

	public void setGatherId(Long gatherId) {
		this.gatherId = gatherId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	public FilterBlock getFilterBlockVo(){
		return super.getVo();
	}
	
	public void setFilterBlockVo(FilterBlock filterBlockVo){
		super.setVo(filterBlockVo);
	}
	
	@Override
	protected Long getPK(FilterBlock vo) {
		return vo.getId();
	}

	@Override
	protected FilterBlock getOperator(Long pk) {
		return crawlerFac.findFilterBlock(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delFilterBlock(getGatherId(), pk);
	}

	@Override
	protected Long saveOperator(FilterBlock vo, boolean isUpdate) {
		return crawlerFac.addAndUpdFilterBlock(getGatherId(), getParentId(), vo);
	}

	@Override
	protected FilterBlock createEmptyVo() {
		return new FilterBlock();
	}
}
