/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.leaderchannel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.plugin.leadingwindow.LeadingWindowFacable;
import com.ewcms.plugin.leadingwindow.model.LeaderChannel;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.leaderchannel.index")
public class LeaderChannelAction extends CrudBaseAction<LeaderChannel, Integer> {

	private static final long serialVersionUID = -2100000694109964578L;
	
	@Autowired
	private LeadingWindowFacable leadingWindowFac;
	
	private Integer channelId;
	private Integer leaderId;

	public LeaderChannel getLeaderChannelVo(){
		return super.getVo();
	}
	
	public void setLeaderChannelVo(LeaderChannel leaderChannelVo){
		super.setVo(leaderChannelVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected LeaderChannel createEmptyVo() {
		return new LeaderChannel();
	}

	@Override
	protected LeaderChannel getOperator(Integer pk) {
		return leadingWindowFac.getLeaderChannel(pk, getChannelId());
	}

	@Override
	protected Integer getPK(LeaderChannel vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(LeaderChannel vo, boolean isUpdate) {
        if (isUpdate) {
        	return leadingWindowFac.updLeaderChannel(getLeaderId(), vo, getChannelId());
        } else {
            return leadingWindowFac.addLeaderChannel(getLeaderId(), vo, getChannelId());
        }
	}
	
	@Override
	protected void deleteOperator(Integer pk) {
	}
	
	private Integer leaderChannelId;
	
	public Integer getLeaderChannelId() {
		return leaderChannelId;
	}

	public void setLeaderChannelId(Integer leaderChannelId) {
		this.leaderChannelId = leaderChannelId;
	}

	public void up(){
		try{
			if (getLeaderId() != null && getLeaderChannelId() != null && getChannelId() != null){
				leadingWindowFac.upLeaderChannel(getLeaderId(), getLeaderChannelId(), getChannelId());
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
			if (getLeaderId() != null && getLeaderChannelId() != null && getChannelId() != null){
				leadingWindowFac.downLeaderChannel(getLeaderId(), getLeaderChannelId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else {
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}

	public void deleteLC(){
		try{
			if (getLeaderChannelId() != null){
				leadingWindowFac.delLeaderChannel(getLeaderId(), getLeaderChannelId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
}
