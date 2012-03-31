/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.model;

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
@Table(name="auth_userinfo")
public class UserInfo implements Serializable{

	private static final long serialVersionUID = 311835227847275415L;
	
	@Id
    private String username;
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthday;
    @Column(name="credential_id",length=50)
    private String identification;
    @Column(length=50)
    private String name;
    @Column(length=50)
    private String email;
    @Column(length=20)
    private String phone;
    @Column(length=20)
    private String mphone;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserInfo other = (UserInfo) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInfo{");
        builder.append("username:").append(username).append(";");
        builder.append("name:").append(name).append(";");
        builder.append("identification:").append(identification).append(";");
        builder.append("email:").append(email).append(";");
        builder.append("phone:").append(phone).append(";");
        builder.append("mphone:").append(mphone).append(";");
        builder.append("birtyday:").append(birthday).append(";");
        builder.append("}");

        return builder.toString();
    }


}
