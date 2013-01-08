/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Channel;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author 吴智俊
 */
public class AppChannelAction extends CrudBaseAction<Channel, Integer> {

	private static final long serialVersionUID = 5163065258225199472L;

	@Autowired
	private SiteFacable siteFac;
	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
	public Channel getChannelVo() {
		return super.getVo();
	}

	public void setChannelVo(Channel channelVo) {
		super.setVo(channelVo);
	}

	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	@Override
	protected Integer getPK(Channel vo) {
		return vo.getId();
	}

	@Override
	protected Channel getOperator(Integer pk) {
		return null;
	}

	@Override
	protected void deleteOperator(Integer pk) {
		siteFac.delAppChannel(getChannelId(), pk);
	}

	@Override
	protected Integer saveOperator(Channel vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected Channel createEmptyVo() {
		return new Channel();
	}
}
