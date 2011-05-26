package com.ewcms.common.query.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_limitlog")
public class LimitLog implements Serializable{

    @Id
    private Integer id;
    @ManyToOne(targetEntity=Certificate.class,fetch=FetchType.LAZY)
    private Certificate certificate;
    @Column
    private Integer money;
    @Column
    private Date date;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Certificate getCertificate() {
        return certificate;
    }
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LimitLog other = (LimitLog) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "LimitLog [id=" + id + ", certificate=" + certificate
                + ", money=" + money + ", date=" + date + "]";
    }
}
