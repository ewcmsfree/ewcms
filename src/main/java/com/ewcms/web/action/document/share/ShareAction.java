/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.share;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.document.DataserveFac;
import com.ewcms.core.document.model.ShareArticle;
import com.ewcms.core.site.model.Organ;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 * 
 * @author 周冬初
 */
public class ShareAction extends CrudBaseAction<ShareArticle, Integer> {
	@Autowired
	private DataserveFac dsFac;

	private List<Integer> siteIdList;
	private List<Integer> channelIds;

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

	public ShareArticle getShareArticleVo() {
		return super.getVo();
	}

	public void setShareArticleVo(ShareArticle shareArticleVo) {
		super.setVo(shareArticleVo);
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	@Override
	protected Integer getPK(ShareArticle vo) {
		return null;
	}

	@Override
	protected ShareArticle getOperator(Integer pk) {
		return null;
	}

	@Override
	protected void deleteOperator(Integer pk) {
		dsFac.delShareArticle(pk);
	}

	@Override
	protected Integer saveOperator(ShareArticle vo, boolean isUpdate) {
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
			dsFac.shareArticle(siteIdList, getSelections());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	public void share() {
		try {
			dsFac.shareArticle(siteIdList, getSelections());
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
