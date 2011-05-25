/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.quartz.TriggerUtils;
import org.springframework.stereotype.Service;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.common.ValidationError;
import com.ewcms.scheduling.common.ValidationErrors;
import com.ewcms.scheduling.common.ValidationErrorsable;
import com.ewcms.scheduling.model.AlqcJob;
import com.ewcms.scheduling.model.AlqcJobCalendarTrigger;
import com.ewcms.scheduling.model.AlqcJobSimpleTrigger;
import com.ewcms.scheduling.model.AlqcJobTrigger;

/**
 * 调度任务时间表达式效验
 *
 * @author 吴智俊
 */
@Service("alqcJobValidator")
public class AlqcJobValidator implements AlqcJobValidatorable {

    private static final Pattern PATTERN_CRON_MINUTES;
    private static final Pattern PATTERN_CRON_HOURS;
    private static final Pattern PATTERN_CRON_MONTH_DAYS;
//  private static final Pattern PATTERN_TIMESTAMP_FORMAT;

    static {
        String allPattern = "(\\*)";

        String minPattern = "(\\d|[0-5]\\d)";
        String minRangePattern = "(" + minPattern + "(\\-" + minPattern + ")?)";
        String minuteIncrementPattern = "(" + minPattern + "\\/\\d+)";
        PATTERN_CRON_MINUTES = Pattern.compile("^(" + minRangePattern + "(," + minRangePattern + ")*)|" + minuteIncrementPattern + "|" + allPattern + "$");

        String hourPattern = "(\\d|[01]\\d|2[0-3])";
        String hourRangePattern = "(" + hourPattern + "(\\-" + hourPattern + ")?)";
        String hourIncrementPattern = "(" + hourPattern + "\\/\\d+)";
        PATTERN_CRON_HOURS = Pattern.compile("^(" + hourRangePattern + "(," + hourRangePattern + ")*)|" + hourIncrementPattern + "|" + allPattern + "$");

        String dayPattern = "([1-9]|[012]\\d|3[01])";
        String dayRangePattern = "(" + dayPattern + "(\\-" + dayPattern + ")?)";
        String dayIncrementPattern = "(" + dayPattern + "\\/\\d+)";
        PATTERN_CRON_MONTH_DAYS = Pattern.compile("^(" + dayRangePattern + "(," + dayRangePattern + ")*)|" + dayIncrementPattern + "|" + allPattern + "$");

//	PATTERN_TIMESTAMP_FORMAT = Pattern.compile("(\\p{L}|\\p{N}|(\\_)|(\\.)|(\\-))+");
    }

    public ValidationErrorsable validateJob(AlqcJob job) throws BaseException {
        ValidationErrorsable errors = new ValidationErrors();
        validateJobDetails(errors, job);
        validateJobTrigger(errors, job);
//        if (job instanceof AlqcJobReport){
//            validateJobOutput(errors, (AlqcJobReport)job);
//        }
        return errors;
    }

    protected void validateJobDetails(ValidationErrorsable errors, AlqcJob job) {
        checkString(errors, "label", "名称", job.getLabel(), true, 100);
        checkString(errors, "description", "描述", job.getDescription(), false, 2000);
    }

    protected void validateJobTrigger(ValidationErrorsable errors, AlqcJob job) throws BaseException {
        AlqcJobTrigger trigger = job.getTrigger();
        if (trigger == null) {
            errors.add(new ValidationError("error.alqc.job.no.trigger", null, "没有触发器指向任务.", "trigger"));
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date now = calendar.getTime();
        String tzId = trigger.getTimeZone();
        if (tzId != null && tzId.length() > 0) {
            TimeZone tz = TimeZone.getTimeZone(tzId);
            now = TriggerUtils.translateTime(now, tz, TimeZone.getDefault());
        }

        if (trigger.getStartType() == AlqcJobTrigger.START_TYPE_SCHEDULE) {
            Date startDate = trigger.getStartDate();
            if (startDate == null) {
                errors.add(new ValidationError("error.not.empty", null, "开始日期不能为空.", "trigger.startDate"));
//            } else if (startDate.before(now)) {
//                errors.add(new ValidationError("error.before.current.date", null, "开始日期不能在过去.", "trigger.startDate"));
            }
        }

        if (trigger.getEndDate() != null) {
            if (trigger.getStartType() == AlqcJobTrigger.START_TYPE_NOW) {
                if (trigger.getEndDate().before(now)) {
                    errors.add(new ValidationError("error.before.current.date", null, "结束日期不能在过去.", "trigger.endDate"));
                }
            } else if (trigger.getStartType() == AlqcJobTrigger.START_TYPE_SCHEDULE) {
                if (trigger.getEndDate().before(now)) {
                    errors.add(new ValidationError("error.before.current.date", null, "结束日期不能在过去.", "trigger.endDate"));
                }
                if (trigger.getStartDate() != null && trigger.getEndDate().before(trigger.getStartDate())) {
                    errors.add(new ValidationError("error.before.start.date", null, "结束日期不能早于开始日期.", "trigger.endDate"));
                }
            }
        }

        if (trigger instanceof AlqcJobSimpleTrigger) {
            validateJobSimpleTrigger(errors, (AlqcJobSimpleTrigger) trigger);
        } else if (trigger instanceof AlqcJobCalendarTrigger) {
            validateJobCalendarTrigger(errors, (AlqcJobCalendarTrigger) trigger);
        } else {
//            String quotedTriggerType = "\"" + trigger.getClass().getName() + "\"";
//            throw new JSException("jsexception.job.unknown.trigger.type", new Object[] {quotedTriggerType});
            throw new BaseException("不能识别任务中的触发器类型", "不能识别任务中的触发器类型");
        }
    }

    protected void validateJobSimpleTrigger(ValidationErrorsable errors, AlqcJobSimpleTrigger trigger) throws BaseException {
    	if (trigger.getOccurrenceCount() == null){
    		throw new BaseException("次数不能为空","次数不能为空");
    	}
    	int occurrenceCount = trigger.getOccurrenceCount();
	    if (occurrenceCount != AlqcJobSimpleTrigger.RECUR_INDEFINITELY && occurrenceCount < 1) {
	    	errors.add(new ValidationError("error.invalid", null, "次数不能少于0", "trigger.occurrenceCount"));
	    } else if (occurrenceCount > 1 || occurrenceCount == AlqcJobSimpleTrigger.RECUR_INDEFINITELY) {
	        if (trigger.getRecurrenceInterval() == null) {
	        	errors.add(new ValidationError("error.not.empty", null, "间隔数不能为空.", "trigger.recurrenceInterval"));
	        } else if (trigger.getRecurrenceInterval().intValue() <= 0) {
	            errors.add(new ValidationError("error.positive", null, "间隔数不能小于0", "trigger.recurrenceInterval"));
	        }
	        if (trigger.getRecurrenceIntervalUnit() == null) {
	            errors.add(new ValidationError("error.not.empty", null, "间隔单位不能为空.", "trigger.recurrenceIntervalUnit"));
	        }
	    }
    }

    protected void validateJobCalendarTrigger(ValidationErrorsable errors, AlqcJobCalendarTrigger trigger) throws BaseException {
        if (checkString(errors, "trigger.minutes", "分钟", trigger.getMinutes(), true, 200)) {
            validateCronMinutes(errors, trigger.getMinutes());
        }

        if (checkString(errors, "trigger.hours", "小时", trigger.getHours(), true, 80)) {
            validateCronHours(errors, trigger.getHours());
        }

        if (trigger.getDaysType().intValue() == AlqcJobCalendarTrigger.DAYS_TYPE_ALL.intValue()) {
        } else if (trigger.getDaysType().intValue() == AlqcJobCalendarTrigger.DAYS_TYPE_WEEK.intValue()) {
            if (trigger.getWeekDays() == null || trigger.getWeekDays().length() == 0) {
                errors.add(new ValidationError("error.not.empty", null, "一周内不能为空.", "trigger.weekDays"));
            }
        } else if (trigger.getDaysType().intValue() == AlqcJobCalendarTrigger.DAYS_TYPE_MONTH.intValue()) {
            if (checkString(errors, "trigger.monthDays", "一个月内", trigger.getMonthDays(), true, 100)) {
                validateCronMonthDays(errors, trigger.getMonthDays());
            }
        } else {
            throw new BaseException("不能识别任务中的复杂触发器天数类型", "不能识别任务中的复杂触发器天数类型");
        }

        if (trigger.getMonths() == null || trigger.getMonths().length() == 0) {
            errors.add(new ValidationError("error.not.empty", null, "月份不能为空.", "trigger.months"));
        }
    }

    protected void validateCronMinutes(ValidationErrorsable errors, String minutes) {
        if (!PATTERN_CRON_MINUTES.matcher(minutes).matches()) {
            errors.add(new ValidationError("error.pattern", null, "分钟无效.", "trigger.minutes"));
        }
    }

    protected void validateCronHours(ValidationErrorsable errors, String hours) {
        if (!PATTERN_CRON_HOURS.matcher(hours).matches()) {
            errors.add(new ValidationError("error.pattern", null, "小时无效.", "trigger.hours"));
        }
    }

    protected void validateCronMonthDays(ValidationErrorsable errors, String days) {
        if (!PATTERN_CRON_MONTH_DAYS.matcher(days).matches()) {
            errors.add(new ValidationError("error.pattern", null, "小时无效.", "trigger.monthDays"));
        }
    }

//    protected void validateJobOutput(ValidationErrorsable errors, AlqcJobReport jobReport) {
//
//        String outputFormats = jobReport.getOutputFormat();
//
//        if (outputFormats == null || outputFormats.length() == 0) {
//            if (jobReport.getTextReport() != null && jobReport.getChartReport() == null) {
//                errors.add(new ValidationError("error.report.job.no.output.formats", null, "在任务中没有选择输出格式.", "outputFormats"));
//            }
//        }
//
//        AlqcJobMail mail = jobReport.getMail();
//        if (mail != null) {
//            validateMailNotification(errors, mail);
//        }
//    }

    protected boolean checkString(ValidationErrorsable errors, String field, String name, String value, boolean mandatory, int maxLength) {
        boolean valid = true;
        boolean empty = value == null || value.length() == 0;
        if (empty) {
            if (mandatory) {
                errors.add(new ValidationError("error.not.empty", null, name + "不能为空.", field));
                valid = false;
            }
        } else {
            if (value.length() > maxLength) {
                errors.add(new ValidationError("error.length", new Object[]{Integer.valueOf(maxLength)}, "最大长度为{0 ，数量，整数}.", field));
                valid = false;
            }
        }
        return valid;
    }
}
