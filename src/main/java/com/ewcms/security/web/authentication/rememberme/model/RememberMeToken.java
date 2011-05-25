/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.security.web.authentication.rememberme.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wangwei
 */
@Entity
@Table(name="auth_persistent_logins")
public class RememberMeToken implements Serializable{
    
    @Id
    private String id;
    @Column(nullable=false,length=20)
    private String username;
    @Column(nullable=false,length=64)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date lastUsed;
    @Column(nullable=false,length=30)
    private String ipAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastUsed() {
        return lastUsed ;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RememberMeToken other = (RememberMeToken) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("RememberMeToken{");
        builder.append("id:").append(id).append(";");
        builder.append("username:").append(username).append(";");
        builder.append("token:").append(token).append(";");
        builder.append("lastused:").append(lastUsed).append(";");
        builder.append("ip:").append(ipAddress).append(";");
        builder.append("}");

        return builder.toString();
    }
}
