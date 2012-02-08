/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.TriggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.BaseRuntimeException;
import com.ewcms.scheduling.BaseRuntimeExceptionWrapper;
import com.ewcms.scheduling.generate.common.ValidationError;
import com.ewcms.scheduling.generate.common.ValidationErrorsable;
import com.ewcms.scheduling.generate.vo.JobInfoRuntimeInformation;
import com.ewcms.scheduling.model.JobCalendarTrigger;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobSimpleTrigger;
import com.ewcms.scheduling.model.JobTrigger;

/**
 * @author 吴智俊
 */
public class JobsQuartzScheduler implements JobsQuartzSchedulerable, InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(JobsQuartzScheduler.class);
    
    private static final String GROUP = "jobs";
    private static final String TRIGGER_LISTENER_NAME = "schedulerTriggerListener";
    private static final String JOB_DATA_KEY_DETAILS_ID = "jobDetailsID";
    
    private static final Long COEFFICIENT_MINUTE = 60L * 1000L;
    private static final Long COEFFICIENT_HOUR = 60L * COEFFICIENT_MINUTE;
    private static final Long COEFFICIENT_DAY = 24L * COEFFICIENT_HOUR;
    private static final Long COEFFICIENT_WEEK = 7L * COEFFICIENT_DAY;
    private static final Integer COUNT_WEEKDAYS = 7;
    private static final Integer COUNT_MONTHS = 12;
    
    private Scheduler scheduler;

    private final Set<SchedulerListenerable> listeners;
    private final SchedulerListener schedulerListener;
    private final TriggerListener triggerListener;

    public JobsQuartzScheduler() {
        listeners = new HashSet<SchedulerListenerable>();

        schedulerListener = new SchedulerQuartzListener();
        triggerListener = new SchedulerTriggerListener(TRIGGER_LISTENER_NAME);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void afterPropertiesSet() throws Exception {
        try {
            //注册为非全局的SchedulerListener
            getScheduler().addSchedulerListener(schedulerListener);
            //注册为非全局的TriggerListener
            getScheduler().addTriggerListener(triggerListener);
        } catch (SchedulerException e) {
            logger.error("注册Quartz监听时出现错误", e);
            throw new BaseRuntimeExceptionWrapper(e);
        }
    }

    public void scheduleJob(JobInfo job) throws BaseException {
        JobDetail jobDetail = createJobDetail(job);
        Trigger trigger = createTrigger(job);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("JobsQuartzScheduler：为任务 " + job.getId() + " 创建任务 " + jobDetail.getFullName() + " 和触发器 " + trigger.getFullName());
        } catch (SchedulerException e) {
            logger.error("调度Quartz任务时错误", e);
            throw new BaseException(e.toString(), "调度Quartz任务时错误");
        }
    }

    /**
     * 建立调度器任务明细
     *
     * @param job 调度器任务
     * @return JobDetail
     */
    @SuppressWarnings("unchecked")
	protected JobDetail createJobDetail(JobInfo job) {
        String jobName = jobName(job.getId());
        String jobClassEntity = job.getJobClass().getClassEntity().trim();
        try {
            if (jobClassEntity != null && jobClassEntity.length() > 0) {
                Class<Job> classEntity = (Class<Job>) Class.forName(jobClassEntity);
                JobDetail jobDetail = new JobDetail(jobName, GROUP, classEntity, false, false, false);
                return jobDetail;
            }else{
            	logger.info("必须选择一个执行的调度器作业任务");
                throw new BaseRuntimeException("必须选择一个执行的调度器作业任务");
            }
        } catch (ClassNotFoundException e) {
            logger.error("调度器作业不是一个有效的作业任务",e);
            throw new BaseRuntimeException("调度器作业不是一个有效的作业任务", new Object[]{jobClassEntity});
        }
    }

    public void rescheduleJob(JobInfo job) throws BaseException {
        try {
            Trigger oldTrigger = getJobTrigger(job.getId());

            String jobName = jobName(job.getId());
            Trigger trigger = createTrigger(job);
            trigger.setJobName(jobName);
            trigger.setJobGroup(GROUP);

            if (oldTrigger == null) {
                JobDetail jobDetail = createJobDetail(job);
                scheduler.scheduleJob(jobDetail, trigger);
                logger.info("JobsQuartzScheduler：为任务 " + job.getId() + " 建立触发器 " + trigger.getFullName());
            } else {
                scheduler.rescheduleJob(oldTrigger.getName(), oldTrigger.getGroup(), trigger);
                logger.info("JobsQuartzScheduler：为任务 " + job.getId() + " 把触发器 " + oldTrigger.getFullName() + " 改为 " + trigger.getFullName());
            }
        } catch (SchedulerException e) {
            logger.error("调度Quartz任务错误", e);
            throw new BaseException(e.toString(), "调度Quartz任务错误");
        }
    }

    /**
     * 查询调度器任务的触发器
     *
     * @param jobId 调度器任务编号
     * @return Trigger
     * @throws SchedulerException
     * @throws BaseException
     */
    protected Trigger getJobTrigger(Integer jobId) throws SchedulerException, BaseException {
        Trigger trigger;
        String jobName = jobName(jobId);
        Trigger[] triggers = scheduler.getTriggersOfJob(jobName, GROUP);
        if (triggers == null || triggers.length == 0) {
            trigger = null;
            logger.info("JobsQuartzScheduler：在任务 " + jobId + " 中未找到触发器");
        } else if (triggers.length == 1) {
            trigger = triggers[0];
            logger.info("JobsQuartzScheduler：在任务 " + jobId + " 找到触发器 " + trigger.getFullName());
        } else {
        	logger.error("任务有一个以上的触发器", new Object[] {new Integer(jobId)});
            throw new BaseException("任务有一个以上的触发器", "任务有一个以上的触发器");
        }
        return trigger;
    }

    /**
     * 设置调度器任务名称
     *
     * @param jobId 调度器任务编号
     * @return String
     */
    protected String jobName(Integer jobId) {
        return "job_" + jobId;
    }

    /**
     * 设置触发器名称
     *
     * @param jobTrigger 触发器
     * @return String
     */
    protected String triggerName(JobTrigger jobTrigger) {
        return "trigger_" + jobTrigger.getId() + "_" + jobTrigger.getVersion();
    }

    /**
     * 建立触发器
     *
     * @param jobInfo 触发器
     * @return Trigger
     * @throws BaseException
     */
    protected Trigger createTrigger(JobInfo jobInfo) throws BaseException {
        Trigger trigger;
        JobTrigger jobTrigger = jobInfo.getTrigger();
        if (jobTrigger instanceof JobSimpleTrigger) {
            trigger = createTrigger((JobSimpleTrigger) jobTrigger);
        } else if (jobTrigger instanceof JobCalendarTrigger) {
            trigger = createTrigger((JobCalendarTrigger) jobTrigger);
        } else {
        	String quotedJobTrigger = "\"" + jobTrigger.getClass().getName() + "\"";
        	logger.error("任务触发类型没有命名", new Object[] {quotedJobTrigger});
            throw new BaseException("任务触发类型没有命名", "任务触发类型没有命名");
        }

        JobDataMap jobDataMap = trigger.getJobDataMap();
        jobDataMap.put(JOB_DATA_KEY_DETAILS_ID, jobInfo.getId());

        trigger.addTriggerListener(TRIGGER_LISTENER_NAME);

        return trigger;
    }

    //---------------------------------------简单型触发器---------------------------------------------------
    /**
     * 建立触发器
     *
     * @param jobTrigger 简单型触发器
     * @return Trigger
     * @throws BaseException
     */
    protected Trigger createTrigger(JobSimpleTrigger jobTrigger) throws BaseException {
        String triggerName = triggerName(jobTrigger);
        Date startDate = getStartDate(jobTrigger);
        Date endDate = getEndDate(jobTrigger);

        int repeatCount = repeatCount(jobTrigger);
        SimpleTrigger trigger;
        if (repeatCount == 0) {
            trigger = new SimpleTrigger(triggerName, GROUP, startDate);
        } else {
            int recurrenceInterval = jobTrigger.getRecurrenceInterval().intValue();
            if (jobTrigger.getRecurrenceIntervalUnit().intValue() == JobSimpleTrigger.INTERVAL_MINUTE.intValue()) {
                trigger = new SimpleTrigger(triggerName, GROUP, startDate, endDate, repeatCount, recurrenceInterval * COEFFICIENT_MINUTE);
            } else if (jobTrigger.getRecurrenceIntervalUnit().intValue() == JobSimpleTrigger.INTERVAL_HOUR.intValue()) {
                trigger = new SimpleTrigger(triggerName, GROUP, startDate, endDate, repeatCount, recurrenceInterval * COEFFICIENT_HOUR);
            } else if (jobTrigger.getRecurrenceIntervalUnit().intValue() == JobSimpleTrigger.INTERVAL_DAY.intValue()) {
                trigger = new SimpleTrigger(triggerName, GROUP, startDate, endDate, repeatCount, recurrenceInterval * COEFFICIENT_DAY);
            } else if (jobTrigger.getRecurrenceIntervalUnit().intValue() == JobSimpleTrigger.INTERVAL_WEEK.intValue()) {
                trigger = new SimpleTrigger(triggerName, GROUP, startDate, endDate, repeatCount, recurrenceInterval * COEFFICIENT_WEEK);
            } else {
            	logger.error("不能识别任务执行的间隔数", new Object[] {jobTrigger.getRecurrenceIntervalUnit()});
                throw new BaseException("不能识别任务执行的间隔数", "不能识别任务执行的间隔数");
            }
        }

        trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return trigger;
    }

    /**
     * 查询结束时间
     *
     * @param jobTrigger 触发器
     * @return Date
     * @throws BaseException
     */
    protected Date getEndDate(JobTrigger jobTrigger) throws BaseException {
        return translateFromTriggerTimeZone(jobTrigger, jobTrigger.getEndDate());
    }

    protected Date translateFromTriggerTimeZone(JobTrigger jobTrigger, Date date) throws BaseException {
        if (date != null) {
            TimeZone tz = getTriggerTimeZone(jobTrigger);
            if (tz != null) {
                date = TriggerUtils.translateTime(date, TimeZone.getDefault(), tz);
            }
        }
        return date;
    }

    /**
     * 查询开始时间
     *
     * @param jobTrigger 触发器
     * @return Date
     * @throws BaseException
     */
    protected Date getStartDate(JobTrigger jobTrigger) throws BaseException {
        Date startDate;

        if (jobTrigger.getStartType().intValue() == JobTrigger.START_TYPE_NOW.intValue()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate = calendar.getTime();
        } else if (jobTrigger.getStartType().intValue() == JobTrigger.START_TYPE_SCHEDULE.intValue()) {
            startDate = translateFromTriggerTimeZone(jobTrigger, jobTrigger.getStartDate());
        } else {
        	logger.error("任务开始类型没有赋值", new Object[] {jobTrigger.getStartType()});
            throw new BaseException("任务开始类型没有赋值", "任务开始类型没有赋值");
        }
        return startDate;
    }

    /**
     * 执行次数
     *
     * @param jobTrigger
     * @return int
     */
    protected int repeatCount(JobSimpleTrigger jobTrigger) {
        Integer recurrenceCount = jobTrigger.getOccurrenceCount();
        Integer repeatCount;
        if (recurrenceCount.intValue() == JobSimpleTrigger.RECUR_INDEFINITELY.intValue()) {
            repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
        } else {
            repeatCount = recurrenceCount - 1;
        }
        return repeatCount;
    }
    //---------------------------------------简单型触发器---------------------------------------------------

    //---------------------------------------复杂型触发器---------------------------------------------------
    /**
     * 建立触发器
     *
     * @param jobTrigger 复杂型触发器
     * @return Trigger
     * @throws BaseException
     */
    protected Trigger createTrigger(JobCalendarTrigger jobTrigger) throws BaseException {
        String triggerName = triggerName(jobTrigger);
        Date startDate = getStartDate(jobTrigger);
        Date endDate = getEndDate(jobTrigger);

        String cronExpression = getCronExpression(jobTrigger);

        try {
            CronTrigger trigger = new CronTrigger(triggerName, GROUP, cronExpression);
            trigger.setStartTime(startDate);
            trigger.setEndTime(endDate);
            trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

            TimeZone timeZone = getTriggerTimeZone(jobTrigger);
            if (timeZone != null) {
                trigger.setTimeZone(timeZone);
            }

            return trigger;
        } catch (ParseException e) {
            logger.error("建立Quartz复杂触发器错误", e);
            throw new BaseException("建立Quartz复杂触发器错误", "建立Quartz复杂触发器错误");
        }
    }

    /**
     * 查询触发器时区
     *
     * @param jobTrigger 触发器
     * @return TimeZone
     * @throws BaseException
     */
    protected TimeZone getTriggerTimeZone(JobTrigger jobTrigger) throws BaseException {
        String tzId = jobTrigger.getTimeZone();
        TimeZone tz;
        if (tzId == null || tzId.length() == 0) {
            tz = null;
        } else {
            tz = TimeZone.getTimeZone(tzId);
            if (tz == null) {
            	String quotedTzId = "\"" + tzId + "\"";
            	logger.error("时区(TimeZone)没有赋值", quotedTzId);
                throw new BaseException("时区(TimeZone)没有赋值", "时区(TimeZone)没有赋值");
            }
        }
        return tz;
    }

    /**
     * 查询触发器表达式
     *
     * @param jobTrigger 复杂型触发器
     * @return String
     * @throws BaseException
     */
    protected String getCronExpression(JobCalendarTrigger jobTrigger) throws BaseException {
        String minutes = jobTrigger.getMinutes();
        String hours = jobTrigger.getHours();
        String weekDays;
        String monthDays;
        if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_ALL.intValue()) {
            weekDays = "?";
            monthDays = "*";
        } else if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_WEEK.intValue()) {
            weekDays = enumerateCronVals(jobTrigger.getWeekDays(), COUNT_WEEKDAYS);
            monthDays = "?";
        } else if (jobTrigger.getDaysType().intValue() == JobCalendarTrigger.DAYS_TYPE_MONTH.intValue()) {
            weekDays = "?";
            monthDays = jobTrigger.getMonthDays();
        } else {
        	logger.error("未知的任务触发天类型",  new Object[] {jobTrigger.getDaysType()});
            throw new BaseException("未知的任务触发天类型", "未知的任务触发天类型");
        }
        String months = enumerateCronVals(jobTrigger.getMonths(), COUNT_MONTHS);

        StringBuffer cronExpression = new StringBuffer();
        cronExpression.append("0 ");
        cronExpression.append(minutes);
        cronExpression.append(' ');
        cronExpression.append(hours);
        cronExpression.append(' ');
        cronExpression.append(monthDays);
        cronExpression.append(' ');
        cronExpression.append(months);
        cronExpression.append(' ');
        cronExpression.append(weekDays);

        return cronExpression.toString();
    }

    protected String enumerateCronVals(String vals, int totalCount) throws BaseException {
        if (vals == null || vals.length() == 0) {
        	logger.error("未选择值");
            throw new BaseException("未选择值", "未选择值");
        }
        StringTokenizer tokenizer = new StringTokenizer(vals, ",", false);
        if (tokenizer.countTokens() == totalCount) {
            return "*";
        } else {
            return vals;
        }
    }
    //---------------------------------------复杂型触发器---------------------------------------------------

    public void removeScheduledJob(Integer jobId) throws BaseException {
        try {
            String jobName = jobName(jobId);
            if (scheduler.deleteJob(jobName, GROUP)) {
                logger.info("JobsQuartzScheduler：任务 " + jobName + " 被删除");
            } else {
                logger.info("JobsQuartzScheduler：Quartz任务 " + jobId + " 未查到不能删除");
            }
        } catch (SchedulerException e) {
            logger.error("删除Quartz任务时错误", e);
            throw new BaseException("删除Quartz任务时错误", "删除Quartz任务时错误");
        }
    }

    public List<JobInfo> getJobsRuntimeInformation(List<JobInfo> jobInfos) throws BaseException {
        if (jobInfos == null || jobInfos.size() == 0) {
            return new ArrayList<JobInfo>();
        }
        try {
            Set<String> executingJobNames = getExecutingJobNames();
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (JobInfo jobInfo : jobInfos) {
                JobInfoRuntimeInformation info = getJobRuntimeInformation(jobInfo.getId(), executingJobNames);
                jobInfo.setState(info.getState());
                if (info.getStartTime() != null){
                	jobInfo.setStartTime(bartDateFormat.format(info.getStartTime()));
                }else{
                	try{
                		jobInfo.setStartTime(bartDateFormat.format(jobInfo.getTrigger().getStartDate()));
                	}catch(Exception e){
                	}
                }
                if (info.getEndTime() != null){
                	jobInfo.setEndTime(bartDateFormat.format(info.getEndTime()));
                }else{
                	try{
                		jobInfo.setEndTime(bartDateFormat.format(jobInfo.getTrigger().getEndDate()));
                	}catch(Exception e){
                	}
                }
                if (info.getPreviousFireTime() != null) {
                    jobInfo.setPreviousFireTime(bartDateFormat.format(info.getPreviousFireTime()));
                }
                if (info.getNextFireTime() != null) {
                    jobInfo.setNextFireTime(bartDateFormat.format(info.getNextFireTime()));
                }
            }
            return jobInfos;
        } catch (SchedulerException e) {
            logger.error("获得Quartz运行时信息错误", e);
            throw new BaseException("获得Quartz运行时信息错误", "获得Quartz运行时信息错误");
        }
    }

    /**
     * 获取调度器任务运行时的状态
     *
     * @param jobId 调度器任务编号
     * @param executingJobNames 调度器任务名称
     * @return JobRuntimeInformation
     * @throws SchedulerException
     * @throws BaseException
     */
    protected JobInfoRuntimeInformation getJobRuntimeInformation(Integer jobId, Set<String> executingJobNames) throws SchedulerException, BaseException {
        JobInfoRuntimeInformation info = new JobInfoRuntimeInformation();
        Trigger trigger = getJobTrigger(jobId);
        if (trigger == null) {
            info.setState(JobInfoRuntimeInformation.STATE_UNKNOWN);
        } else {
        	info.setStartTime(trigger.getStartTime());
        	info.setEndTime(trigger.getEndTime());
            info.setPreviousFireTime(trigger.getPreviousFireTime());
            if (trigger.mayFireAgain()) {
                info.setNextFireTime(trigger.getNextFireTime());
            }

            String state = getJobState(trigger, executingJobNames);
            info.setState(state);
        }
        return info;
    }

    protected String getJobState(Trigger trigger, Set<String> executingJobNames) throws SchedulerException {
        String state;
        int quartzState = scheduler.getTriggerState(trigger.getName(), trigger.getGroup());
        switch (quartzState) {
            case Trigger.STATE_NORMAL:
            case Trigger.STATE_BLOCKED:
                state = executingJobNames.contains(trigger.getJobName()) ? JobInfoRuntimeInformation.STATE_EXECUTING : JobInfoRuntimeInformation.STATE_NORMAL;
                break;
            case Trigger.STATE_COMPLETE:
                state = JobInfoRuntimeInformation.STATE_COMPLETE;
                break;
            case Trigger.STATE_PAUSED:
                state = JobInfoRuntimeInformation.STATE_PAUSED;
                break;
            case Trigger.STATE_ERROR:
                state = JobInfoRuntimeInformation.STATE_ERROR;
                break;
            default:
                state = JobInfoRuntimeInformation.STATE_UNKNOWN;
                break;
        }
        return state;
    }

    @SuppressWarnings("unchecked")
    protected Set<String> getExecutingJobNames() throws SchedulerException {
        List<JobExecutionContext> executingJobs = (List<JobExecutionContext>) scheduler.getCurrentlyExecutingJobs();
        Set<String> executingJobNames = new HashSet<String>();
        for (Iterator<JobExecutionContext> iter = executingJobs.iterator(); iter.hasNext();) {
            JobExecutionContext executionContext = (JobExecutionContext) iter.next();
            JobDetail jobDetail = executionContext.getJobDetail();
            if (jobDetail.getGroup().equals(GROUP)) {
                executingJobNames.add(jobDetail.getName());
            }
        }
        return executingJobNames;
    }

    public void addSchedulerListener(SchedulerListenerable listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public synchronized void removeSchedulerListener(SchedulerListenerable listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void notifyListenersOfFinalizedJob(Integer jobId) throws BaseException {
        synchronized (listeners) {
            for (Iterator<SchedulerListenerable> it = listeners.iterator(); it.hasNext();) {
                SchedulerListenerable listener = (SchedulerListenerable) it.next();
                listener.jobFinalized(jobId);
            }
        }
    }

    protected void getTriggerFinalized(Trigger trigger) throws BaseException {
    	try{
    		Integer jobId = trigger.getJobDataMap().getIntegerFromString(JOB_DATA_KEY_DETAILS_ID);
    		notifyListenersOfFinalizedJob(jobId);
    	}catch(ClassCastException e){
    	}
    }

    protected class SchedulerQuartzListener implements SchedulerListener {

        public SchedulerQuartzListener() {
        }

        /*
         * Scheduler在有新的JobDetail部署时调用这个方法。
         * @see org.quartz.SchedulerListener#jobScheduled(org.quartz.Trigger)
         */
        @Override
        public void jobScheduled(Trigger trigger) {
            logger.info("SchedulerListener：" + trigger.getFullJobName() + " 触发器上的任务被安排在 " + trigger.getFullName() + " 触发器上");
        }

        /*
         * Scheduler在有新的JobDetail卸载时调用这个方法。
         * @see org.quartz.SchedulerListener#jobUnscheduled(java.lang.String, java.lang.String)
         */
        @Override
        public void jobUnscheduled(String name, String group) {
            logger.info("SchedulerListener：" + group + "." + name + " 任务被卸载");
        }

        /*
         * 当一个Trigger来到了再也不会触发的状态时调用这个方法。
         * 除非这个Job已设置成持久性，否则它就会从Scheduler中移除。
         * @see org.quartz.SchedulerListener#triggerFinalized(org.quartz.Trigger)
         */
        @Override
        public void triggerFinalized(Trigger trigger) {
            logger.info("SchedulerListener：" + trigger.getFullName() + " 触发器已完成");

            if (trigger.getGroup().equals(GROUP)) {
                try {
                    getTriggerFinalized(trigger);
                } catch (Exception e) {
                	logger.error("Quartz：" + e.toString());
                }
            }
        }

        /*
         * Scheduler调用这个方法是发生在一个Trigger或Trigger组被暂停时。
         * 假如是Trigger组的话，triggerName参数为null。
         * @see org.quartz.SchedulerListener#triggersPaused(java.lang.String, java.lang.String)
         */
        @Override
        public void triggersPaused(String triggerName, String group) {
        	logger.info("SchedulerListener：" + group + "." + triggerName + " 触发器被暂停");
        }

        /*
         * Scheduler调用这个方法是发生在一个Trigger或Trigger组从暂停中恢复时。
         * 假如是Trigger组的话，triggerName参数为null。
         * @see org.quartz.SchedulerListener#triggersResumed(java.lang.String, java.lang.String)
         */
        @Override
        public void triggersResumed(String triggerName, String group) {
        	logger.info("SchedulerListener：" + group + "." + triggerName + " 触发器从暂停中恢复");
        }

        /*
         * 当一个或一组JobDetail暂停时调用这个方法。
         * @see org.quartz.SchedulerListener#jobsPaused(java.lang.String, java.lang.String)
         */
        @Override
        public void jobsPaused(String name, String group) {
        	logger.info("SchedulerListener：" + group + "." + name + " 任务被暂停");
        }

        /*
         * 当一个或一组Job从暂停上恢复时调用这个方法。
         * 假如是一个Job组，jobName参数将为null。
         * @see org.quartz.SchedulerListener#jobsResumed(java.lang.String, java.lang.String)
         */
        @Override
        public void jobsResumed(String jobName, String group) {
        	logger.info("SchedulerListener：" + group + "." + jobName + " 任务从暂停中恢复");
        }

        /*
         * 在Scheduler的正常运行期间产生一个严重错误时调用这个方法。
         * 错误的类型会各式的，但是下面列举了一些错误例子：
         * · 初始化Job类的问题
         * · 试图去找下一个Trigger的问题
         * · JobStore中重复的问题
         * · 数据存储连接的问题
         * 可以使用SchedulerException的getErrorCode()或者getUnderlyingException()方法获取到特定错误的更详细的信息。
         * @see org.quartz.SchedulerListener#schedulerError(java.lang.String, org.quartz.SchedulerException)
         */
        @Override
        public void schedulerError(String msg, SchedulerException cause) {
        	logger.error("SchedulerListener：" + msg, cause);
        }

        /*
         * Scheduler调用这个方法用来通知SchedulerListener，Scheduler将要被关闭。
         * @see org.quartz.SchedulerListener#schedulerShutdown()
         */
        @Override
        public void schedulerShutdown() {
            logger.info("SchedulerListener：调度器已关闭");
        }

        /*
         * (non-Javadoc)
         * @see org.quartz.SchedulerListener#jobAdded(org.quartz.JobDetail)
         */
		@Override
		public void jobAdded(JobDetail jobDetail) {
			logger.info("SchedulerListener：" + jobDetail.getFullName() + " 任务被新增");
		}

		/*
		 * (non-Javadoc)
		 * @see org.quartz.SchedulerListener#jobDeleted(java.lang.String, java.lang.String)
		 */
		@Override
		public void jobDeleted(String jobName, String groupName) {
			logger.info("SchedulerListener：" + groupName + "." + jobName + " 任务被删除");
		}

		/*
		 * (non-Javadoc)
		 * @see org.quartz.SchedulerListener#schedulerInStandbyMode()
		 */
		@Override
		public void schedulerInStandbyMode() {
			logger.info("SchedulerListener：调度器移动到待机模式");
		}

		/*
		 * (non-Javadoc)
		 * @see org.quartz.SchedulerListener#schedulerShuttingdown()
		 */
		@Override
		public void schedulerShuttingdown() {
			logger.info("SchedulerListener：调度器正在关闭");
		}

		/*
		 * (non-Javadoc)
		 * @see org.quartz.SchedulerListener#schedulerStarted()
		 */
		@Override
		public void schedulerStarted() {
			logger.info("SchedulerListener：调度器已经开始");
		}
    }

    protected class SchedulerTriggerListener implements TriggerListener {

        private final String name;

        public SchedulerTriggerListener(String name) {
            this.name = name;
        }

        /*
         * 监听器名称
         * @see org.quartz.TriggerListener#getName()
         */
        @Override
        public String getName() {
            return name;
        }

        /*
         * 当与监听器相关联的Trigger被触发，Job上的execute()方法将要被执行时，Scheduler就调用这个方法。
         * 在全局TriggerListener情况下，这个方法为所有Trigger被调用。
         * @see org.quartz.TriggerListener#triggerFired(org.quartz.Trigger, org.quartz.JobExecutionContext)
         */
        @Override
        public void triggerFired(Trigger trigger, JobExecutionContext context) {
            logger.info("TriggerListener：" + trigger.getFullName() + " 触发器被触发");
        }

        /*
         * 在Trigger触发后，Job将要被执行时由Scheduler调用这个方法。
         * TriggerListener给了一个选择去否决Job的执行。
         * 假如这个方法返回true，这个Job将不会为此次Trigger触发而去执行。
         * @see org.quartz.TriggerListener#vetoJobExecution(org.quartz.Trigger, org.quartz.JobExecutionContext)
         */
        @Override
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
            return false;
        }

        /*
         * Scheduler调用这个方法是在Trigger错过触发时。
         * 如这个方法的JavaDoc所指出的，你应该关注此方法中持续时间长的逻辑：在出现许多错过触发的Trigger时，长的逻辑会导致骨牌效应。
         * 你应当保持这个方法尽量的小。
         * @see org.quartz.TriggerListener#triggerMisfired(org.quartz.Trigger)
         */
        @Override
        public void triggerMisfired(Trigger trigger) {
            logger.info("TriggerListener：" + trigger.getFullName() + " 触发器已错过触发");

            if (trigger.getGroup().equals(GROUP) && trigger.getFireTimeAfter(new Date()) == null) {
                try {
                    getTriggerFinalized(trigger);
                } catch (BaseException e) {
                    logger.error(e.toString());
                }
            }
        }

        @Override
        public void triggerComplete(Trigger trigger, JobExecutionContext context, int triggerInstructionCode) {
            logger.info("TriggerListener：" + trigger.getFullName() + " 触发器 " + triggerInstructionCode + " 指令码已完成");
        }
    }

    /*
     * 校验Job
     */
    public void validate(JobInfo job, ValidationErrorsable errors) throws BaseException {
        Trigger quartzTrigger = createTrigger(job);
        Date firstFireTime = quartzTrigger.computeFirstFireTime(null);
        if (firstFireTime == null) {
            errors.add(new ValidationError("error.report.job.trigger.no.fire", null, null, "trigger"));
        }
    }

	@Override
	public void pauseJob(Integer jobId) throws BaseException {
		try{
			String jobName = jobName(jobId);
			Trigger trigger = getJobTrigger(jobId);
			int quartzState = scheduler.getTriggerState(trigger.getName(), trigger.getGroup());
			if (quartzState == Trigger.STATE_NORMAL){
				scheduler.pauseTrigger(trigger.getName(), trigger.getGroup());
				logger.info("暂停工作任务 " + GROUP + "." + jobName);
			}else{
				logger.error("暂停工作任务 " + GROUP + "." + jobName + " 必须处于正常状态");
				throw new BaseException("任务必须处于正常状态才能暂停","任务必须处于正常状态才能暂停");
			}
		}catch (SchedulerException e) {
			logger.error("暂停Quartz任务时错误", e);
		    throw new BaseException("暂停Quartz任务时错误", "暂停Quartz任务时错误");
        }
	}

	@Override
	public void resumedJob(Integer jobId) throws BaseException {
		try{
			String jobName = jobName(jobId);
			Trigger trigger = getJobTrigger(jobId);
			int quartzState = scheduler.getTriggerState(trigger.getName(), trigger.getGroup());
			if (quartzState == Trigger.STATE_PAUSED){
				scheduler.resumeTrigger(trigger.getName(), trigger.getGroup());
				logger.info("恢复工作任务 " + GROUP + "." + jobName);
			}else{
				logger.error("恢复工作任务 " + GROUP + "." + jobName + " 必须处于暂停状态");
				throw new BaseException("任务必须处于暂停状态才能恢复" ,"任务必须处于暂停状态才能恢复");
			}
		}catch (SchedulerException e) {
			logger.error("恢复Quartz任务时错误", e);
		    throw new BaseException("恢复Quartz任务时错误", "恢复Quartz任务时错误");
        }
	}
}
