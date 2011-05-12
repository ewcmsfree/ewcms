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
 * 简单触发器
 * 
 * <ul>
 * <li>occurenceCount:次数</li>
 * <li>recurrenceInterval:间隔数</li>
 * <li>recurrenceIntervalUnit:间隔单位</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_trigger_simple")
@PrimaryKeyJoinColumn(name = "trigger_id")
public class AlqcJobSimpleTrigger extends AlqcJobTrigger {

    private static final long serialVersionUID = -8944849480433973356L;
    public static final Integer INTERVAL_MINUTE = 1;//分钟
    public static final Integer INTERVAL_HOUR = 2;//小时
    public static final Integer INTERVAL_DAY = 3;//天
    public static final Integer INTERVAL_WEEK = 4;//星期
    public static final Integer RECUR_INDEFINITELY = -1;//次数
    
    @Column(name = "occurrencecount")
    private Integer occurrenceCount;
    @Column(name = "recurrenceinterval")
    private Integer recurrenceInterval;
    @Column(name = "recurrenceintervalunit")
    private Integer recurrenceIntervalUnit;

    public AlqcJobSimpleTrigger(){
    	occurrenceCount = RECUR_INDEFINITELY;
    }
    
    public Integer getOccurrenceCount() {
        return occurrenceCount;
    }

    public void setOccurrenceCount(Integer recurrenceCount) {
        this.occurrenceCount = recurrenceCount;
    }

    public Integer getRecurrenceIntervalUnit() {
        return recurrenceIntervalUnit;
    }

    public void setRecurrenceIntervalUnit(Integer recurrenceInterval) {
        this.recurrenceIntervalUnit = recurrenceInterval;
    }

    public Integer getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(Integer recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }
}
