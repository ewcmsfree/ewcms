/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.citizen.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.citizen.CitizenFacable;
import com.ewcms.plugin.citizen.model.Citizen;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

/**
 *
 * @author 吴智俊
 */
public class CitizenAction extends CrudBaseAction<Citizen, Integer> {

	private static final long serialVersionUID = -1590036095468199050L;
	
	@Autowired
	private CitizenFacable citizenFac;

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
		citizenFac.delCitizen(pk);
	}

	@Override
	protected Citizen getOperator(Integer pk) {
		return citizenFac.getCitizen(pk);
	}

	@Override
	protected Integer getPK(Citizen vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(Citizen vo, boolean isUpdate) {
		if (isUpdate) {
			return citizenFac.updCitizen(vo);
		}else{
			return citizenFac.addCitizen(vo);
		}
	}

	public void getCitizenAll(){
		try{
			List<Citizen> citizens = citizenFac.getAllCitizen();
			DataGrid data = new DataGrid(citizens.size(), citizens);
			Struts2Util.renderJson(JSONUtil.toJSON(data));
		}catch(Exception e){
		}
	}
}
