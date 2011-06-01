/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.document.DataserveFac;
import com.ewcms.content.document.model.ShareArticle;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author 周冬初
 */
public class ShareAction extends CrudBaseAction<ShareArticle, Long> {

	private static final long serialVersionUID = -4525206274953910541L;

	@Autowired
	private DataserveFac dsFac;

	private List<Integer> siteIdList;
	private List<Integer> channelIds;
	private Integer channelId;

	public List<Integer> getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(List<Integer> channelIds) {
		this.channelIds = channelIds;
	}

	public List<Integer> getSiteIdList() {
		return siteIdList;
	}

	public void setSiteIdList(List<Integer> siteIdList) {
		this.siteIdList = siteIdList;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ShareArticle getShareArticleVo() {
		return super.getVo();
	}

	public void setShareArticleVo(ShareArticle shareArticleVo) {
		super.setVo(shareArticleVo);
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	@Override
	protected Long getPK(ShareArticle vo) {
		return null;
	}

	@Override
	protected ShareArticle getOperator(Long pk) {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		dsFac.delShareArticle(pk);
	}

	@Override
	protected Long saveOperator(ShareArticle vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected ShareArticle createEmptyVo() {
		return null;
	}

	public void shareTop() {
//		try {
//			Organ vo = bsFac.getOrgan(bsFac.searchOrganRoot().getId());
//			if (vo.getChildren() != null && !vo.getChildren().isEmpty()) {
//				Integer homeSiteId = vo.getChildren().get(0).getHomeSiteId();
//				siteIdList = new ArrayList<Integer>();
//				siteIdList.add(vo.getChildren().get(0).getHomeSiteId());
//				dsFac.shareArticle(siteIdList, getSelections());
//				Struts2Util.renderJson(JSONUtil.toJSON("true"));
//			} else {
//				Struts2Util.renderJson(JSONUtil.toJSON("false"));
//			}
//
//		} catch (Exception e) {
//			Struts2Util.renderJson(JSONUtil.toJSON("false"));
//		}
	}

	public void shareSelf() {
		try {
			siteIdList = new ArrayList<Integer>();
			siteIdList.add(getCurrentSite().getId());
			dsFac.shareArticle(siteIdList, getSelections(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	public void share() {
		try {
			dsFac.shareArticle(siteIdList, getSelections(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	public void toChannel() {
		try {
			dsFac.refArticle(getSelections(), channelIds);
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
