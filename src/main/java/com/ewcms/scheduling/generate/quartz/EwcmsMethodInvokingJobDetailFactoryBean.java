/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.quartz;

import static org.quartz.JobBuilder.newJob;

import java.lang.reflect.InvocationTargetException;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;

/**
 * 
 * @author wuzhijun
 *
 */
public class EwcmsMethodInvokingJobDetailFactoryBean extends ArgumentConvertingMethodInvoker implements FactoryBean<JobDetail>, BeanNameAware, BeanClassLoaderAware, InitializingBean {

	static{
		try{
			jobDetailImplClass = Class.forName("org.quartz.impl.JobDetailImpl");
		}catch(ClassNotFoundException e){
			jobDetailImplClass = null;
		}
	}
	
    private Scheduler scheduler;

    private static Class<?> jobDetailImplClass;
    private String name;
    private String group;
    private boolean concurrent;
    private String targetBeanName;
    private String jobListenerNames[];
    private String beanName;
    private ClassLoader beanClassLoader;
    private BeanFactory beanFactory;
    private JobDetail jobDetail;
	
    public Scheduler getScheduler() throws JobExecutionException {
    	if (scheduler == null){
    		throw new JobExecutionException("Fatal Error:  bean '" + this.getClass().getName()+"' does not have its QuartzScheduler bean set and it is required to be.");
    	}
    	return scheduler;
    }
    
    public void setScheduler(Scheduler scheduler){
    	this.scheduler = scheduler;
    }
    
    public EwcmsMethodInvokingJobDetailFactoryBean(){
    	group = "DEFAULT";
    	concurrent = true;
    	beanClassLoader = ClassUtils.getDefaultClassLoader();
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setGroup(String group){
    	this.group = group;
    }
    
    public void setConcurrent(Boolean concurrent){
    	this.concurrent = concurrent;
    }
    
    public void setTargetBeanName(String targetBeanName){
    	this.targetBeanName = targetBeanName;
    }
    
    public void setJobListenerNames(String names[]){
        jobListenerNames = names;
    }

    public void setBeanFactory(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }
    
	@Override
	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
		prepare();
        String name = this.name == null ? beanName : this.name;
        Class<? extends MethodInvokingJob> jobClass = concurrent ? EwcmsMethodInvokingJobDetailFactoryBean.MethodInvokingJob.class : EwcmsMethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob.class;
        if(jobDetailImplClass != null){
        	jobDetail = (JobDetail)BeanUtils.instantiate(jobDetailImplClass);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(jobDetail);
            bw.setPropertyValue("name", name);
            bw.setPropertyValue("group", group);
            bw.setPropertyValue("jobClass", jobClass);
            bw.setPropertyValue("durability", Boolean.valueOf(true));
            ((JobDataMap)bw.getPropertyValue("jobDataMap")).put("methodInvoker", this);
        }else{
        	jobDetail = newJob(jobClass).withIdentity(name, group).build();

            if (!(jobDetail instanceof JobDetailImpl))
                throw new RuntimeException("Expected JobDetail to be an instance of '" + JobDetailImpl.class + "' but instead we got '"+jobDetail.getClass().getName()+"'");
            ((JobDetailImpl)jobDetail).setDurability(true);
            jobDetail.getJobDataMap().put("methodInvoker", this);
        }
        if(jobListenerNames != null){
            String as[];
            int j = (as = jobListenerNames).length;
            for(int i = 0; i < j; i++){
                String jobListenerName = as[i];
                if(jobDetailImplClass != null)
                    throw new IllegalStateException("Non-global JobListeners not supported on Quartz 2 - manually register a Matcher against the Quartz ListenerManager instead");

                JobKey jk = jobDetail.getKey();
                Matcher<JobKey> matcher = KeyMatcher.keyEquals(jk);
                try {
                	getScheduler().getListenerManager().addJobListenerMatcher(jobListenerName, matcher);
                } catch (org.quartz.SchedulerException e) {
                	throw new RuntimeException("Error adding Quartz Trigger Listener: "+e.getMessage());
                }

                //jobDetail.addJobListener(jobListenerName);
            }
        }
        postProcessJobDetail(jobDetail);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		beanClassLoader = classLoader;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	protected void postProcessJobDetail(JobDetail jobdetail){
    }
	
	@Override
	public Class<?> getTargetClass(){
		Class<?> targetClass = super.getTargetClass();
	    if(targetClass == null && targetBeanName != null){
	    	Assert.state(beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
	        targetClass = beanFactory.getType(targetBeanName);
	    }
	    return targetClass;
	}
	
	@Override
	public Object getTargetObject(){
	    Object targetObject = super.getTargetObject();
	    if(targetObject == null && targetBeanName != null){
	    	Assert.state(beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
	        targetObject = beanFactory.getBean(targetBeanName);
	    }
	    return targetObject;
	}
	 
	@Override
	public JobDetail getObject() throws Exception {
		return jobDetail;
	}

	@Override
	public Class<? extends JobDetail> getObjectType() {
		return jobDetail == null ? org.quartz.impl.JobDetailImpl.class : jobDetail.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	protected Class<?> resolveClassName(String className) throws ClassNotFoundException{
		return ClassUtils.forName(className, beanClassLoader);
	}
	
	public static class MethodInvokingJob extends EwcmsQuartzJobBean{
		
        protected static final Logger logger = LoggerFactory.getLogger(EwcmsMethodInvokingJobDetailFactoryBean.MethodInvokingJob.class);
        private MethodInvoker methodInvoker;
		
		public MethodInvokingJob(){
		}

		@Override
		protected void executeInternal(JobExecutionContext jobexecutioncontext)	throws JobExecutionException {
			try{
				jobexecutioncontext.setResult(methodInvoker.invoke());
            }
            catch(InvocationTargetException ex){
                if(ex.getTargetException() instanceof JobExecutionException)
                    throw (JobExecutionException)ex.getTargetException();
                else
                    throw new EwcmsJobMethodInvocationFailedException(methodInvoker, ex.getTargetException());
            }
            catch(Exception ex){
                throw new EwcmsJobMethodInvocationFailedException(methodInvoker, ex);
            }
		}
	}
	
	@SuppressWarnings("deprecation")
	public static class StatefulMethodInvokingJob extends MethodInvokingJob implements org.quartz.StatefulJob{
		public StatefulMethodInvokingJob(){
		}
	}
}
