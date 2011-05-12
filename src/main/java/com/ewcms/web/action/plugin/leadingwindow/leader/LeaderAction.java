/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.leader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.plugin.leadingwindow.LeadingWindowFacable;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.leader.index")
public class LeaderAction extends CrudBaseAction<Leader, Integer> {

	private static final long serialVersionUID = -9048840370552678688L;
	@Autowired
	private LeadingWindowFacable leadingWindowFac;
	
	private Integer channelId;
	
	public Leader getLeaderVo(){
		return super.getVo();
	}
	
	public void setLeaderVo(Leader leaderVo){
		super.setVo(leaderVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected Leader createEmptyVo() {
		return new Leader();
	}
	
	@Override
	public String index() throws Exception{
		leadingWindowFac.addPositionRoot(getChannelId());
		return INDEX;
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try{
			leadingWindowFac.delLeader(pk, getChannelId());
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	@Override
	protected Leader getOperator(Integer pk) {
		return leadingWindowFac.getLeader(pk, getChannelId());
	}

	@Override
	protected Integer getPK(Leader vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(Leader vo, boolean isUpdate) {
        if (isUpdate) {
        	return leadingWindowFac.updLeader(vo, getChannelId());
        } else {
            return leadingWindowFac.addLeader(vo, getChannelId());
        }
	}
	
	private Integer leaderId;
	
	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public void downLeader(){
		try{
			if (getLeaderId() != null){
				leadingWindowFac.downLeader(getLeaderId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void upLeader(){
		try{
			if (getLeaderId() != null){
				leadingWindowFac.upLeader(getLeaderId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
}
