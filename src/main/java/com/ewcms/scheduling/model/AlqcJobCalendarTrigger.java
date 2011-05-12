/**
 * 创建日期 2009-4-12
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 日历(复杂)触发器
 * 
 * <ul>
 * <li>minutes:分种</li>
 * <li>hours:小时</li>
 * <li>daysType:天数类型</li>
 * <li>weekDays:星期</li>
 * <li>monthDays:一个月中的天数</li>
 * <li>months:月份</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_trigger_calendar")
@PrimaryKeyJoinColumn(name = "trigger_id")
public class AlqcJobCalendarTrigger extends AlqcJobTrigger {

    private static final long serialVersionUID = 7369898846500579220L;
    public static final Integer DAYS_TYPE_ALL = 1;
    public static final Integer DAYS_TYPE_WEEK = 2;
    public static final Integer DAYS_TYPE_MONTH = 3;
    @Column(name = "minutes", length = 200, nullable = false)
    private String minutes;
    @Column(name = "hours", length = 80, nullable = false)
    private String hours;
    @Column(name = "daystype", nullable = false)
    private Integer daysType;
    @Column(name = "weekdays", length = 20)
    private String weekDays;
    @Column(name = "monthdays", length = 20)
    private String monthDays;
    @Column(name = "months", length = 40, nullable = false)
    private String months;

     public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Integer getDaysType() {
        return daysType;
    }

    public void setDaysType(Integer daysType) {
        this.daysType = daysType;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(String monthDays) {
        this.monthDays = monthDays;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
}
