/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.member.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author wangwei
 */
@Entity
@Table(name = "plugin_site_member")
public class Member implements Serializable {

    @Id
    @Column(length = 20, nullable = false)
    private String username;
    @Column
    private String name;
    @Column(length = 40, nullable = false)
    private String password = "xy123456";
    @Column
    private Boolean enabled = true;
    @Column
    private Boolean cppcc = false;
    @Column
    private Boolean nccpc = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return (name == null ? username : name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getCppcc() {
        return cppcc;
    }

    public void setCppcc(Boolean cppcc) {
        this.cppcc = cppcc;
    }

    public Boolean getNccpc() {
        return nccpc;
    }

    public void setNccpc(Boolean nccpc) {
        this.nccpc = nccpc;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Member other = (Member) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("User{");
        builder.append("username:").append(username).append(";");
        builder.append("name:").append(name).append(";");
        builder.append("enabled:").append(enabled).append(";");
        builder.append("}");

        return builder.toString();
    }
}
