/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.message.MessageFacable;
import com.ewcms.content.message.model.MsgSend;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author wu_zhijun
 *
 */
public class MsgSendAction extends CrudBaseAction<MsgSend, Long> {

	private static final long serialVersionUID = 5056220633230582617L;

	@Autowired
	private MessageFacable messageFac;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgSend getMsgSendVo(){
		return super.getVo();
	}
	
	public void setMsgSendVo(MsgSend msgSendVo){
		super.setVo(msgSendVo);
	}
	
	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(MsgSend vo) {
		return vo.getId();
	}

	@Override
	protected MsgSend getOperator(Long pk) {
		MsgSend msgSend = messageFac.findMsgSend(pk);
		//setContent(msgSend.getMsgContent().getDetail());
		return msgSend;
	}

	@Override
	protected void deleteOperator(Long pk) {
		messageFac.delMsgSend(pk);
	}

	@Override
	protected Long saveOperator(MsgSend vo, boolean isUpdate) {
		if (isUpdate) {
			return messageFac.updMsgSend(vo);
		} else {
			return messageFac.addMsgSend(vo, getContent(), new ArrayList<String>());
		}
	}

	@Override
	protected MsgSend createEmptyVo() {
		return new MsgSend();
	}
	
	public void notice(){
		List<MsgSend> msgSends = messageFac.findMsgSendByNotice();
		if (!msgSends.isEmpty()){
			Struts2Util.renderJson(JSONUtil.toJSON(msgSends));
		}else{
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void subscription(){
		List<MsgSend> msgSends = messageFac.findMsgSendBySubscription();
		if (!msgSends.isEmpty()){
			Struts2Util.renderJson(JSONUtil.toJSON(msgSends));
		}else{
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
