/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * 定时器任务信息
 * 
 * <ul>
 * <li>id:任务编号</li>
 * <li>version:版本号</li>
 * <li>userName:用户名</li>
 * <li>label:名称</li>
 * <li>outputLoacal:输出格式本地化</li>
 * <li>description:说明</li>
 * <li>trigger:AlqcJobTrigger对象</li>
 * <li>state:状态(不持久化)</li>
 * <li>previousFireTime:上一次执行时间(不持久化)</li>
 * <li>nextFireTime:下一次执行时间(不持久化)</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_info")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "seq_job_info", sequenceName = "seq_job_info_id", allocationSize = 1)
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 5651638241918468407L;
	@Id
    @GeneratedValue(generator = "seq_job_info",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Integer id;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "username", length = 100, nullable = false)
    private String userName;
    @Column(name = "label", length = 100, nullable = false)
    private String label;
    @Column(name = "outputlocale", length = 20)
    private String outputLocale = "zh_CN";
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "trigger_id")
    private JobTrigger trigger = new JobTrigger();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "jobclass_id")
    private JobClass jobClass = new JobClass();
    
    @Transient
    private String state;
    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    private String previousFireTime;
    @Transient
    private String nextFireTime;

    public JobInfo() {
        version = -1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOutputLocale() {
        return outputLocale;
    }

    public void setOutputLocale(String outputLocale) {
        this.outputLocale = outputLocale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(JobTrigger trigger) {
        this.trigger = trigger;
    }

    public JobClass getJobClass(){
        return jobClass;
    }

    public void setJobClass(JobClass jobClass){
        this.jobClass = jobClass;
    }
    
    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(String previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobInfo other = (JobInfo) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
