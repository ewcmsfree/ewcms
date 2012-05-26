/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.plugin.online.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.online.OnlineOfficeFacable;
import com.ewcms.plugin.online.model.Citizen;
import com.ewcms.plugin.online.model.Matter;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author 吴智俊
 */
public class WorkingBodyAction extends ActionSupport {

	private static final long serialVersionUID = -9048840370552678688L;
	
	@Autowired
	private OnlineOfficeFacable onlineOfficeFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String index() throws Exception{
		onlineOfficeFac.addWorkingBodyRoot(getChannelId());
		return "index";
	}
	
	private String status;
	private Matter matterVo;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Matter getMatterVo() {
		return matterVo;
	}

	public void setMatterVo(Matter matterVo) {
		this.matterVo = matterVo;
	}

	public String queryChannelStatus(){
		if (status.toLowerCase().trim().equals("workingbody")){
			return "article";
		}else if (status.toLowerCase().trim().equals("matter")){
			Matter matter = onlineOfficeFac.getMatter(getWorkingBodyId());
			setMatterVo(matter);
			return "matter";
		}
		return null;
	}
	
	public void findAllMatter(){
		try{
			List<Matter> matters = onlineOfficeFac.getMatterAllOrderBySort();
			Struts2Util.renderJson(JSONUtil.toJSON(matters.toArray()));
		}catch(Exception e){
		}
	}
	
	public void findAllCitizen(){
		try{
			List<Citizen> citizens = onlineOfficeFac.getAllCitizen();
			Struts2Util.renderJson(JSONUtil.toJSON(citizens.toArray()));
		}catch(Exception e){
		}
	}
	
	private String selectIds;
	private Integer parentId;
	private Integer matterId;
	
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
	
	public Integer getMatterId() {
		return matterId;
	}

	public void setMatterId(Integer matterId) {
		this.matterId = matterId;
	}

	public void addMatterToWorkingBody(){
		try{
			if (getSelectIds() != null && getSelectIds().length() > 0){
				String[] select_matterIds = getSelectIds().split(",");
				List<Integer> matterIds = new ArrayList<Integer>();
				for (int i = 0; i < select_matterIds.length; i++){
					matterIds.add(new Integer(select_matterIds[i]));
				}
				onlineOfficeFac.addMatterToWorkingBody(getParentId(), matterIds, getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void removeMatterFromWorkingBody(){
		try{
			onlineOfficeFac.removeMatterFromWorkingBody(getParentId(), getMatterId(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public Boolean isMatter;
	
	public Boolean getIsMatter() {
		return isMatter;
	}

	public void setIsMatter(Boolean isMatter) {
		this.isMatter = isMatter;
	}

	public String tree() throws Exception{
		TreeNode matterTree = onlineOfficeFac.getWorkingBodyWindowTree(getChannelId(),getIsMatter());
		Struts2Util.renderJson(JSONUtil.toJSON(new TreeNode[] { matterTree }));
		return null;
	}
	
	private String workingBodyName;
	
	public String getWorkingBodyName() {
		return workingBodyName;
	}

	public void setWorkingBodyName(String workingBodyName) {
		this.workingBodyName = workingBodyName;
	}

	public void addWorkingBody(){
		try {
			if (getParentId() != null && getParentId().intValue() > 0){
				Integer id = onlineOfficeFac.addWorkingBody(getParentId(), getWorkingBodyName(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON(id));
			}
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	private Integer workingBodyId;
	
	public Integer getWorkingBodyId() {
		return workingBodyId;
	}

	public void setWorkingBodyId(Integer workingBodyId) {
		this.workingBodyId = workingBodyId;
	}

	public void renameWorkingBody(){
		try{
			if (getWorkingBodyId() != null && getWorkingBodyId().intValue() > 0){
				onlineOfficeFac.renameWorkingBody(getWorkingBodyId(), getWorkingBodyName(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch (Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void delWorkingBody(){
		try{
			if (getWorkingBodyId() != null && getWorkingBodyId().intValue() > 0){
				onlineOfficeFac.delWorkingBody(getWorkingBodyId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		}catch (Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void pubLeadingWindow(){
//		try {
//			leadingRelease.release(getChannelId());
//			Struts2Util.renderJson(JSONUtil.toJSON("true"));
//		} catch (ReleaseException e) {
//			e.printStackTrace();
//			Struts2Util.renderJson(JSONUtil.toJSON("false"));
//		}
		//TODO 发布
	}
	
	
	public void upWorkingBody(){
		try{
			if (getParentId() != null && getWorkingBodyId() != null && getChannelId() != null){
				onlineOfficeFac.upWorkingBody(getParentId(), getWorkingBodyId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void downWorkingBody(){
		try{
			if (getParentId() != null && getWorkingBodyId() != null && getChannelId() != null){
				onlineOfficeFac.downWorkingBody(getParentId(), getWorkingBodyId(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	private List<String> filePath;
	private List<String> legend;
	private List<Integer> matterAnnexId;

	public List<Integer> getMatterAnnexId() {
		return matterAnnexId;
	}

	public void setMatterAnnexId(List<Integer> matterAnnexId) {
		this.matterAnnexId = matterAnnexId;
	}

	public List<String> getFilePath() {
		return filePath;
	}

	public void setFilePath(List<String> filePath) {
		this.filePath = filePath;
	}

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}
	
	private Integer organId;
	
	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public void addOrganToMatter(){
		try{
			if (getOrganId() != null && getMatterId() != null){
				onlineOfficeFac.addOrganToMatter(getMatterId(), getOrganId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	private Integer[] organIds;
	
	public Integer[] getOrganIds() {
		return organIds;
	}

	public void setOrganIds(Integer[] organIds) {
		this.organIds = organIds;
	}

	public void addOrganToWorkingBody(){
		try{
			if (getOrganIds() != null && getOrganIds().length > 0 && getWorkingBodyId() != null){
				onlineOfficeFac.addOrganToWorkingBody(getWorkingBodyId(), Arrays.asList(getOrganIds()));
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void removeOrganFromWorkingBody(){
		try{
			if (getWorkingBodyId() != null){
				onlineOfficeFac.removeOrganFromWorkingBOdy(getWorkingBodyId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void removeOrganFromMatter(){
		try{
			if (getMatterId() != null){
				onlineOfficeFac.removeOrganFromMatter(getMatterId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void addCitizenToMatter(){
		try{
			if (getSelectIds() != null && getSelectIds().length() > 0){
				String[] select_citizenIds = getSelectIds().split(",");
				List<Integer> citizenIds = new ArrayList<Integer>();
				for (int i = 0; i < select_citizenIds.length; i++){
					citizenIds.add(new Integer(select_citizenIds[i]));
				}
				onlineOfficeFac.addCitizenToMatter(getMatterId(), citizenIds);
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void removeCitizenFromMatter(){
		try{
			if (getMatterId() != null){
				onlineOfficeFac.removeCitizenFromMatter(getMatterId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
