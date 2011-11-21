/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.BaseException;
import com.ewcms.plugin.crawler.manager.CrawlerFacable;
import com.ewcms.plugin.crawler.model.Domain;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wuzhijun
 *
 */
public class DomainAction extends CrudBaseAction<Domain, Long> {
	
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
	
	public Domain getDomainVo(){
		return super.getVo();
	}
	
	public void setDomainVo(Domain domainVo){
		super.setVo(domainVo);
	}
	
	@Override
	protected Long getPK(Domain vo) {
		return vo.getId();
	}

	@Override
	protected Domain getOperator(Long pk) {
		return crawlerFac.findDomain(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delDomain(getGatherId(), pk);
	}

	@Override
	protected Long saveOperator(Domain vo, boolean isUpdate){
		try {
			return crawlerFac.addAndUpdDomain(getGatherId(), vo);
		} catch (BaseException e) {
			addActionMessage(e.getPageMessage());
			return null;
		}
	}

	@Override
	protected Domain createEmptyVo() {
		return new Domain();
	}
	
	public void up(){
		try{
			if (getGatherId() != null && getSelections() != null && getSelections().size() == 1){
				crawlerFac.upDomain(getGatherId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void down(){
		try{
			if (getGatherId() != null && getSelections() != null && getSelections().size() == 1){
				crawlerFac.downDomain(getGatherId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
