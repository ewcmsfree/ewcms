/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.JobClassEntity;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manage.fac.SchedulingFacable;
import com.ewcms.scheduling.manage.util.ConversionUtil;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobClass;
import com.ewcms.web.CrudBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller
public class JobInfoAction extends CrudBaseAction<PageDisplayVO, Integer> {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private SchedulingFacable schedulingFac;
	
	private Boolean subChannel;

	public PageDisplayVO getPageDisplayVo(){
		return super.getVo();
	}
	
	public void setPageDisplayVo(PageDisplayVO pageDisplayVo){
		super.setVo(pageDisplayVo);
	}
	
	public List<Integer> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Integer> selections) {
		super.setOperatorPK(selections);
	}
	
	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}

	@Override
	protected PageDisplayVO createEmptyVo() {
		return new PageDisplayVO();
	}

	@Override
	protected void deleteOperator(Integer pk) {
		try {
			schedulingFac.deletedScheduledJob(pk);
		} catch (BaseException e) {
			this.addActionMessage(e.getPageMessage());
		}
	}

	@Override
	protected PageDisplayVO getOperator(Integer pk) {
		PageDisplayVO pageDisplayVo = new PageDisplayVO();
		try {
			JobInfo alqcJob = schedulingFac.getScheduledJob(pk);
			pageDisplayVo = ConversionUtil.constructPageVo(alqcJob);
			
			if (alqcJob instanceof EwcmsJobChannel){
				setSubChannel(((EwcmsJobChannel)alqcJob).getSubChannel());
				pageDisplayVo.setIsJobChannel(true);
			}
		} catch (BaseException e) {
		}
		return pageDisplayVo;
	}

	@Override
	protected Integer getPK(PageDisplayVO vo) {
		return vo.getJobId();
	}

	@Override
	protected Integer saveOperator(PageDisplayVO vo, boolean isUpdate) {
//		AlqcJob alqcJob = new AlqcJob();
//		try{
//			if (vo.getJobId() != null && vo.getJobId().intValue() > 0){
//				alqcJob = alqcSchedulingFac.getScheduledJob(vo.getJobId());
//			}
//			if (vo.getJobClassId() != null && vo.getJobClassId().intValue() > 0){
//				AlqcJobClass alqcJobClass = alqcSchedulingFac.findByJobClass(vo.getJobClassId());
//				alqcJob.setJobClass(alqcJobClass);
//			}
//			if (isUpdate) {
//				return alqcSchedulingFac.updateScheduledJob(ConversionUtil.constructAlqcJobVo(alqcJob,vo));
//			}else{
//				return alqcSchedulingFac.saveScheduleJob(ConversionUtil.constructAlqcJobVo(alqcJob, vo));
//			}
//		}catch(BaseException e){
//			this.addActionMessage(e.getPageMessage());
//			return null;
//		}
		return null;
	}
	
	@Override
	public String save() throws Exception{
		JobInfo alqcJob = new JobInfo();
		try{
			if (getPageDisplayVo().getJobId() != null && getPageDisplayVo().getJobId().intValue() > 0){
				alqcJob = schedulingFac.getScheduledJob(getPageDisplayVo().getJobId());
			}
			if (getPageDisplayVo().getJobClassId() != null && getPageDisplayVo().getJobClassId().intValue() > 0){
				JobClass alqcJobClass = schedulingFac.findByJobClass(getPageDisplayVo().getJobClassId());
				alqcJob.setJobClass(alqcJobClass);
			}
			alqcJob = ConversionUtil.constructAlqcJobVo(alqcJob, getPageDisplayVo());
			
			if (isUpdateOperator()) {
				operatorState = OperatorState.UPDATE;
			    schedulingFac.updateScheduledJob(alqcJob);
			    operatorPK.remove(0);
			    if (!operatorPK.isEmpty()){
			    	setPageDisplayVo(getOperator(operatorPK.get(0)));
			    }
			} else {
				operatorState = OperatorState.ADD;
	            Integer id = schedulingFac.saveScheduleJob(alqcJob);
	            operatorPK.add(id);
	            setPageDisplayVo(createEmptyVo());
			}
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}finally{
			if (alqcJob instanceof EwcmsJobChannel){
				setSubChannel(((EwcmsJobChannel)alqcJob).getSubChannel());
				getPageDisplayVo().setIsJobChannel(true);
			}
		}
		return SUCCESS;
	}
	
	public List<JobClass> getAllJobClassList() {
		List<JobClass> alqcJobClassList = new ArrayList<JobClass>();
		try {
			alqcJobClassList = schedulingFac.findByAllJobClass();
			if (getPageDisplayVo().getIsJobChannel()==false){
				JobClass alqcJobClass = schedulingFac.findByJobClassByClassEntity(JobClassEntity.JOB_CHANNEL);
				alqcJobClassList.remove(alqcJobClass);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
		return alqcJobClassList;
	}
	
	private Integer jobId;
	
	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String pauseJob(){
		try {
			schedulingFac.pauseJob(getJobId());
		} catch (BaseException e) {
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
	
	public String resumedJob(){
		try{
			schedulingFac.resumedJob(getJobId());
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
}
