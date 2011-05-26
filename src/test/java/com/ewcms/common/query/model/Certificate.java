/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author wangwei
 */
@Entity
@Table(name = "test_certificate")
@NamedQueries({
  @NamedQuery(name="Certificate.findById",query="Select o From Certificate o Where o.id = :id"),
  @NamedQuery(name="Certificate.findByIdPosition",query="Select o From Certificate o Where o.id = ?1")
})
public class Certificate implements Serializable{
    @Id
    private String id;
    
    @Column
    private String name;
    
    @Column
    private Date brithdate;
    
    @Column(name = "moneylimit")
    private Integer limit;
    
    @ManyToOne(targetEntity=Sex.class)
    private Sex sex;
    
    @OneToMany(targetEntity=LimitLog.class,mappedBy="certificate",fetch=FetchType.LAZY)
    private List<LimitLog> limitLogs;

    public Date getBrithdate() {
        return brithdate;
    }

    public void setBrithdate(Date brithdate) {
        this.brithdate = brithdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @JsonIgnore
    public List<LimitLog> getLimitLogs() {
        return limitLogs;
    }

    public void setLimitLogs(List<LimitLog> limitLogs) {
        this.limitLogs = limitLogs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Certificate other = (Certificate) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
