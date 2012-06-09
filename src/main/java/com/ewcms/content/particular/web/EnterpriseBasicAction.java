/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.EnterpriseBasic;
import com.ewcms.core.site.model.Organ;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.EwcmsContextUtil;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class EnterpriseBasicAction extends CrudBaseAction<EnterpriseBasic, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;

	private Integer channelId;
	
	private String organShow = "disable";
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getOrganShow() {
		return organShow;
	}

	public void setOrganShow(String organShow) {
		this.organShow = organShow;
	}

	public EnterpriseBasic getEnterpriseBasicVo() {
		return super.getVo();
	}

	public void setEnterpriseBasicVo(EnterpriseBasic enterpriseBasic) {
		super.setVo(enterpriseBasic);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(EnterpriseBasic vo) {
		return vo.getId();
	}

	@Override
	protected EnterpriseBasic getOperator(Long pk) {
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}
		return particularFac.findEnterpriseBasicById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delEnterpriseBasic(pk);
	}

	@Override
	protected Long saveOperator(EnterpriseBasic vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updEnterpriseBasic(vo);
		} else {
			return particularFac.addEnterpriseBasic(vo);
		}
	}

	@Override
	protected EnterpriseBasic createEmptyVo() {
		EnterpriseBasic enterpriseBasic = new EnterpriseBasic();
		if (EwcmsContextUtil.getGroupnames().contains("GROUP_ADMIN")){
			organShow = "enable";
		}else{
			Organ organ = particularFac.findOrganByUserName();
			if (organ != null){
				enterpriseBasic.setOrgan(organ);
			}
		}
		return enterpriseBasic;
	}
	
	public void pub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.pubEnterpriseBasic(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void unPub(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				particularFac.unPubEnterpriseBasic(getChannelId(), getSelections());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}