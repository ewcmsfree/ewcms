/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.leadingwindow.position;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.generator.release.ReleaseException;
import com.ewcms.plugin.leadingwindow.LeadingWindowFacable;
import com.ewcms.plugin.leadingwindow.model.Leader;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.util.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.leadingwindow.position.index")
public class PositionAction extends ActionSupport {

	private static final long serialVersionUID = -2955746993197094631L;

	@Autowired
	private LeadingWindowFacable leadingWindowFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String index() throws Exception{
		leadingWindowFac.addPositionRoot(getChannelId());
		return "index";
	}
	
	private String status;
	private Integer leaderId;
	private Leader leaderVo;
	private Integer leaderChannelId;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Leader getLeaderVo() {
		return leaderVo;
	}

	public void setLeaderVo(Leader leaderVo) {
		this.leaderVo = leaderVo;
	}

	public Integer getLeaderChannelId() {
		return leaderChannelId;
	}

	public void setLeaderChannelId(Integer leaderChannelId) {
		this.leaderChannelId = leaderChannelId;
	}

	public String queryChannelStatus(){
		if (status.toLowerCase().trim().equals("leader")){
			Leader leader = leadingWindowFac.getLeader(getLeaderChannelId(), getChannelId());
			setLeaderVo(leader);
			return "leader";
		}else if (status.toLowerCase().trim().equals("leaderchannel")){
			return "article";
		}
		return null;
	}
	
	public void findAllLeader(){
		try{
			List<Leader> leaders = leadingWindowFac.getLeaderAllOrderBySort(getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON(leaders.toArray()));
		}catch(Exception e){
			
		}
	}
	
	private String selectIds;
	private Integer parentId;
	
	public String getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(String selectIds) {
		this.selectIds = selectIds;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public void addLeaderToPosition(){
		try{
			if (getSelectIds() != null && getSelectIds().length() > 0){
				String[] select_leaderIds = getSelectIds().split(",");
				List<Integer> leaderIds = new ArrayList<Integer>();
				for (int i = 0; i < select_leaderIds.length; i++){
					leaderIds.add(new Integer(select_leaderIds[i]));
				}
				leadingWindowFac.addLeaderToPosition(getParentId(), leaderIds, getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void removeLeaderToPosition(){
		try{
			leadingWindowFac.removeLeaderFromPosition(getParentId(), getLeaderId(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public String tree() throws Exception{
		TreeNode leaderTree = leadingWindowFac.getLeadingWindowTree(getChannelId());
		Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { leaderTree }));
		return null;
	}
	
	private String positionName;
	
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public void addPosition(){
		try {
			if (getParentId() != null && getParentId().intValue() > 0){
				Integer id = leadingWindowFac.addPosition(getParentId(), getPositionName(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON(id));
			}
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	private Integer positionId;
	
	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public void renamePosition(){
		try{
			if (getPositionId() != null && getPositionId().intValue() > 0){
				leadingWindowFac.renamePosition(getPositionId(), getPositionName(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch (Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void delPosition(){
		try{
			if (getPositionId() != null && getPositionId().intValue() > 0){
				leadingWindowFac.delPosition(getPositionId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch (Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void pubLeadingWindow(){
		try {
			leadingWindowFac.pubPosition(getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (ReleaseException e) {
			e.printStackTrace();
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void upPosition(){
		try{
			if (getParentId() != null && getPositionId() != null && getChannelId() != null){
				leadingWindowFac.upPosition(getParentId(), getPositionId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void downPosition(){
		try{
			if (getParentId() != null && getPositionId() != null && getChannelId() != null){
				leadingWindowFac.downPosition(getParentId(), getPositionId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
