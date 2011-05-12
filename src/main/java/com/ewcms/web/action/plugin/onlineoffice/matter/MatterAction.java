/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.plugin.onlineoffice.matter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.plugin.onlineoffice.OnlineOfficeFacable;
import com.ewcms.plugin.onlineoffice.model.Matter;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("plugin.onlineoffice.matter.index")
public class MatterAction extends CrudBaseAction<Matter, Integer> {

	private static final long serialVersionUID = -9048840370552678688L;
	@Autowired
	private OnlineOfficeFacable onlineOfficeFac;
	private List<Integer> matterAnnexId;
	private List<String> filePath;
	private List<String> legend;
	
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

	public Matter getMatterVo(){
		return super.getVo();
	}
	
	public void setMatterVo(Matter matterVo){
		super.setVo(matterVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Matter createEmptyVo() {
		return new Matter();
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try{
			onlineOfficeFac.delMatter(pk);
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	@Override
	protected Matter getOperator(Integer pk) {
		return onlineOfficeFac.getMatter(pk);
	}

	@Override
	protected Integer getPK(Matter vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(Matter vo, boolean isUpdate) {
        if (isUpdate) {
        	return onlineOfficeFac.updMatter(vo, getMatterAnnexId(), getFilePath(), getLegend());
        } else {
            return onlineOfficeFac.addMatter(vo, getFilePath(), getLegend());
        }
	}
	
	private Integer matterId;
	
	public Integer getMatterId() {
		return matterId;
	}

	public void setMatterId(Integer matterId) {
		this.matterId = matterId;
	}

	public void downMatter(){
		try{
			if (getMatterId() != null){
				onlineOfficeFac.downMatter(getMatterId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
	
	public void upMatter(){
		try{
			if (getMatterId() != null){
				onlineOfficeFac.upMatter(getMatterId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("false"));
		}
	}
}
