/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.message.MessageFacable;
import com.ewcms.content.message.model.MsgReceive;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class MsgReceiveAction extends CrudBaseAction<MsgReceive, Long> {

	private static final long serialVersionUID = -5899497745830507386L;

	@Autowired
	private MessageFacable messageFac;

	public MsgReceive getMsgReceiveVo() {
		return super.getVo();
	}

	public void setMsgReceiveVo(MsgReceive msgReceiveVo) {
		super.setVo(msgReceiveVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(MsgReceive vo) {
		return vo.getId();
	}

	@Override
	protected MsgReceive getOperator(Long pk) {
		return messageFac.findMsgReceive(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		messageFac.delMsgReceive(pk);
	}

	@Override
	protected Long saveOperator(MsgReceive vo, boolean isUpdate) {
		return null;
	}

	@Override
	protected MsgReceive createEmptyVo() {
		return null;
	}

	private Boolean read;

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public void markRead() {
		try {
			if (getRead() != null && getSelections() != null) {
				messageFac.markReadMsgReceive(getSelections().get(0), getRead());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			} else {
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void unRead(){
		List<MsgReceive> msgReceives = messageFac.findMsgReceiveByUnRead();
		if (!msgReceives.isEmpty()){
			Struts2Util.renderJson(JSONUtil.toJSON(msgReceives));
		}else{
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
