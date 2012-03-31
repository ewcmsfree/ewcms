/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.quartz;

import java.util.Date;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Constants;
import org.springframework.scheduling.quartz.JobDetailAwareTrigger;

import com.ewcms.scheduling.BaseRuntimeException;

/**
 * 
 * @author wuzhijun
 *
 */
public class EwcmsSimpleTriggerBean extends SimpleTriggerImpl implements JobDetailAwareTrigger, BeanNameAware, InitializingBean {

	private static final long serialVersionUID = 7693470365658559757L;
	
    private static final Constants constants = new Constants(org.quartz.impl.triggers.SimpleTriggerImpl.class);
    private JobDetail jobDetail;
    private String beanName;
    private long startDelay;

	public EwcmsSimpleTriggerBean() {
		startDelay = 0L;
		setRepeatCount(REPEAT_INDEFINITELY);
	}
	
	public void setJobDataAsMap(Map<String, Object> jobDataAsMap){
        getJobDataMap().putAll(jobDataAsMap);
    }

    public void setMisfireInstructionName(String constantName) {
        setMisfireInstruction(constants.asNumber(constantName).intValue());
    }
    
    public void setTriggerListenerNames(String names[]){
    	for(int i = 0; i < names.length;)
    		throw new BaseRuntimeException("Error !  unhandled setTriggerListenerNames from old Quartz 1.5.  Need to port to Quartz 2.1.2");
    }
    
    public void setStartDelay(long startDelay){
    	this.startDelay = startDelay;
    }
    
	@Override
	public void afterPropertiesSet() throws Exception {
		if(getName() == null)
			setName(beanName);
	    if(getGroup() == null)
	        setGroup("DEFAULT");
	    if(getStartTime() == null)
	        setStartTime(new Date(System.currentTimeMillis() + startDelay));    
	    if(jobDetail != null){
	        setJobKey(jobDetail.getKey());
	        //setJobName(jobDetail.getName());
	        //setJobGroup(jobDetail.getGroup());
	    }
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;		
	}
	
	public void setJobDetail(JobDetail jobDetail){
		this.jobDetail = jobDetail;
	}

	@Override
	public JobDetail getJobDetail() {
		return jobDetail;
	}

}
