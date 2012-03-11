/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.manager.SchedulingFacable;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.web.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
public class JobClassAction extends CrudBaseAction<JobClass, Long> {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private SchedulingFacable schedulingFac;

	public JobClass getAlqcJobClassVo(){
		return super.getVo();
	}
	
	public void setAlqcJobClassVo(JobClass alqcJobClassVo){
		super.setVo(alqcJobClassVo);
	}
	
	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}
	
	@Override
	protected JobClass createEmptyVo() {
		return new JobClass();
	}

	@Override
	protected void deleteOperator(Long pk) {
		try {
			schedulingFac.deletedJobClass(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
		}
	}

	@Override
	protected JobClass getOperator(Long pk) {
		try {
			return schedulingFac.findByJobClass(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
			return null;
		}
	}

	@Override
	protected Long getPK(JobClass vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(JobClass vo, boolean isUpdate) {
		try{
			if (isUpdate) {
				return schedulingFac.updateJobClass(vo);
			}else{
				return schedulingFac.saveJobClass(vo);
			}
		}catch(BaseException e){
			this.addActionMessage(e.getPageMessage());
			return null;
		}
	}
}
