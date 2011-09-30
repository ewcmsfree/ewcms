/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.message.MessageFacable;
import com.ewcms.content.message.model.MsgContent;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class MsgContentAction extends CrudBaseAction<MsgContent, Long> {

	private static final long serialVersionUID = 6285954864795511996L;
	
	@Autowired
	private MessageFacable messageFac;
	
	private Long msgSendId;

	public Long getMsgSendId() {
		return msgSendId;
	}

	public void setMsgSendId(Long msgSendId) {
		this.msgSendId = msgSendId;
	}

	public MsgContent getMsgContentVo() {
		return super.getVo();
	}

	public void setMsgContentVo(MsgContent msgContentVo) {
		super.setVo(msgContentVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(MsgContent vo) {
		return vo.getId();
	}

	@Override
	protected MsgContent getOperator(Long pk) {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		messageFac.delSubscription(pk);
	}

	@Override
	protected Long saveOperator(MsgContent vo, boolean isUpdate) {
		if (isUpdate) {
			return null;
		} else {
			return messageFac.addSubscription(getMsgSendId(), vo.getTitle(), vo.getDetail());
		}
	}

	@Override
	protected MsgContent createEmptyVo() {
		return new MsgContent();
	}
}
