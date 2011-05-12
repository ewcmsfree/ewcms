/*
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.comm.jpa.query;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author wangwei
 */
@Entity
@Table(name = "certificate")
@NamedQueries({
  @NamedQuery(name="Certificate.findById",
                  query="Select o From Certificate o Where o.id = :id"),
  @NamedQuery(name="Certificate.findByIdPosition",
                  query="Select o From Certificate o Where o.id = ?1")
})
public class Certificate implements Serializable{
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private Date brithdate;
    @Column
    private Calendar brithCalendar;
    @Column(name = "moneylimit")
    private Integer limit;

    public Calendar getBrithCalendar() {
        return brithCalendar;
    }

    public void setBrithCalendar(Calendar brithCalendar) {
        this.brithCalendar = brithCalendar;
    }

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
