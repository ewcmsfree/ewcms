/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ReviewProcess;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class ReviewProcessAction extends CrudBaseAction<ReviewProcess, Long> {

	private static final long serialVersionUID = -8106374733257334311L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ReviewProcess getReviewProcessVo() {
		return super.getVo();
	}

	public void setReviewProcessVo(ReviewProcess reviewProcessVo) {
		super.setVo(reviewProcessVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ReviewProcess vo) {
		return vo.getId();
	}

	@Override
	protected ReviewProcess getOperator(Long pk) {
		return documentFac.findReviewProcess(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		documentFac.delReviewProcess(pk);
	}

	@Override
	protected Long saveOperator(ReviewProcess vo, boolean isUpdate) {
		if (isUpdate) {
			return documentFac.updReviewProcess(vo);
		} else {
			return documentFac.addReviewProcess(getChannelId(), vo);
		}
	}

	@Override
	protected ReviewProcess createEmptyVo() {
		return new ReviewProcess();
	}
	
	public void up(){
		try{
			if (getChannelId() != null && getSelections() != null){
				documentFac.upReivewProcess(getChannelId(), getSelections().get(0));
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
			if (getChannelId() != null && getSelections() != null){
				documentFac.downReviewProcess(getChannelId(), getSelections().get(0));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}