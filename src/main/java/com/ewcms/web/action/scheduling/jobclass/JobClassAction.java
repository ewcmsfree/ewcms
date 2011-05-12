/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.web.action.scheduling.jobclass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.manage.fac.AlqcSchedulingFacable;
import com.ewcms.scheduling.model.AlqcJobClass;
import com.ewcms.web.action.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.jobclass.index")
public class JobClassAction extends CrudBaseAction<AlqcJobClass, Integer> {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private AlqcSchedulingFacable alqcSchedulingFac;

	public AlqcJobClass getAlqcJobClassVo(){
		return super.getVo();
	}
	
	public void setAlqcJobClassVo(AlqcJobClass alqcJobClassVo){
		super.setVo(alqcJobClassVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	@Override
	protected AlqcJobClass createEmptyVo() {
		return new AlqcJobClass();
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try {
			alqcSchedulingFac.deletedJobClass(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
		}
	}

	@Override
	protected AlqcJobClass getOperator(Integer pk) {
		try {
			return alqcSchedulingFac.findByJobClass(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
			return null;
		}
	}

	@Override
	protected Integer getPK(AlqcJobClass vo) {
		return vo.getId();
	}

	@Override
	protected Integer saveOperator(AlqcJobClass vo, boolean isUpdate) {
		try{
			if (isUpdate) {
				return alqcSchedulingFac.updateJobClass(vo);
			}else{
				return alqcSchedulingFac.saveJobClass(vo);
			}
		}catch(BaseException e){
			this.addActionMessage(e.getPageMessage());
			return null;
		}
	}
}
