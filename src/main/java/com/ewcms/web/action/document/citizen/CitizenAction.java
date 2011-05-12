/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.document.citizen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.document.DocumentFacable;
import com.ewcms.core.document.model.Citizen;
//import com.ewcms.plugin.onlineoffice.OnlineOfficeFacable;
import com.ewcms.plugin.onlineoffice.OnlineOfficeFacable;
import com.ewcms.util.DataGrid;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("document.citizen.index")
public class CitizenAction extends CrudBaseAction<Citizen, Integer> {

	private static final long serialVersionUID = -1590036095468199050L;
	
	@Autowired
	private DocumentFacable documentFac;
	@Autowired
	private OnlineOfficeFacable onlineOfficeFac;

	public Citizen getCitizenVo(){
		return super.getVo();
	}
	
	public void setCitizenVo(Citizen citizenVo){
		super.setVo(citizenVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	@Override
	protected Citizen createEmptyVo() {
		return new Citizen();
	}

	@Override
	protected void deleteOperator(Integer pk) {
		documentFac.delCitizen(pk);
	}

	@Override
	protected Citizen getOperator(Integer pk) {
		return documentFac.getCitizen(pk);
	}

	@Override
	protected Integer getPK(Citizen vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(Citizen vo, boolean isUpdate) {
		if (isUpdate) {
			return documentFac.updCitizen(vo);
		}else{
			return documentFac.addCitizen(vo);
		}
	}

	public void getCitizen(){
		try{
			List<Citizen> citizens = onlineOfficeFac.getAllCitizen();
			DataGrid data = new DataGrid(citizens.size(), citizens);
			Struts2Util.renderJson(JSONUtil.toJSON(data));
		}catch(Exception e){
			
		}
	}
}
