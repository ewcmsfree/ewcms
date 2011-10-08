/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.crawler.CrawlerFacable;
import com.ewcms.crawler.model.MatchBlock;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wuzhijun
 *
 */
public class MatchBlockAction extends CrudBaseAction<MatchBlock, Long> {

	private static final long serialVersionUID = -8951167029867664588L;
	
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
	
	public MatchBlock getMatchBlockVo(){
		return super.getVo();
	}
	
	public void setMatchBlockVo(MatchBlock matchBlockVo){
		super.setVo(matchBlockVo);
	}
	
	@Override
	protected Long getPK(MatchBlock vo) {
		return vo.getId();
	}

	@Override
	protected MatchBlock getOperator(Long pk) {
		return crawlerFac.findMatchBlock(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delMatchBlock(getGatherId(), pk);
	}

	@Override
	protected Long saveOperator(MatchBlock vo, boolean isUpdate) {
		return crawlerFac.addAndUpdMatchBlock(getGatherId(), getParentId(), vo);
	}

	@Override
	protected MatchBlock createEmptyVo() {
		return new MatchBlock();
	}
	
	public void up(){
		try{
			if (getGatherId() != null && getSelections() != null && getSelections().size() == 1){
				crawlerFac.upMatchBlock(getGatherId(), getSelections().get(0));
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
				crawlerFac.downMatchBlock(getGatherId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
